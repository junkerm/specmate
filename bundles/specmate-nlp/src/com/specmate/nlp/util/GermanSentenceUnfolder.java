package com.specmate.nlp.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.chunk.Chunk;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.dependency.Dependency;

/**
 * Provides German specific functions for unfolding sentences. That means to
 * fill in implicit subjects and verbs.
 */
public class GermanSentenceUnfolder extends SentenceUnfolderBase {

	/** Dependency annotation for dative object relation */
	private static final String DEPENDENCY_TYPE_DATIVE_OBJECT = "OBJD";

	/** Dependency annotation for accusative object relation */
	private static final String DEPENDENCY_TYPE_ACCUSATIVE_OBJECT = "OBJA";

	/** Dependency annotation for subject relation */
	private static final String DEPENDENCY_TYPE_SUBJECT = "SUBJ";

	/**
	 * The following two dependency type forn a conjunction e.g. "Mann --CJ--> und
	 * --KON--> Maus
	 */
	private static final String DEPENDENCY_TYPE_KON = "KON";
	private static final String DEPENDENCY_TYPE_CJ = "CJ";

	/** {@inheritDoc} */
	@Override
	protected Optional<Dependency> findSubjectDependency(JCas jCas, Annotation anno, boolean isGovernor) {
		return NLPUtil.findDependency(jCas, anno, DEPENDENCY_TYPE_SUBJECT, isGovernor);
	}

	/** {@inheritDoc} */
	@Override
	protected Optional<Dependency> findVerbForNounPhrase(JCas jCas, Annotation np) {
		Optional<Dependency> subjDep = findSubjectDependency(jCas, np, false);
		if (subjDep.isPresent()) {
			return subjDep;
		}
		return findObjectDependency(jCas, np, false);
	}

	/** {@inheritDoc} */
	@Override
	protected Optional<Triple<Annotation, EWordOrder, ENounRole>> findImplicitVerbByConjunction(JCas jCas,
			Annotation np) {
		Optional<Pair<Token, Token>> optConjTokens = followConjunctionFromAnnotation(jCas, np);
		if (!optConjTokens.isPresent()) {
			return Optional.empty();
		}
		Token conjNounToken = optConjTokens.get().getLeft();

		ENounRole role = ENounRole.OBJ;
		Optional<Dependency> verbDependency = findObjectDependency(jCas, conjNounToken, false);
		if (!verbDependency.isPresent()) {
			role = ENounRole.SUBJ;
			verbDependency = findSubjectDependency(jCas, conjNounToken, false);
		}

		if (verbDependency.isPresent()) {
			Token verbToken = verbDependency.get().getGovernor();
			EWordOrder order = verbToken.getBegin() < conjNounToken.getBegin() ? EWordOrder.VS : EWordOrder.SOV;
			List<Chunk> verbChunk = JCasUtil.selectCovering(jCas, Chunk.class, verbToken);
			Annotation conjuctVpOrToken;
			if (verbChunk.size() > 0 && verbChunk.get(0).getChunkValue().equals("VP")) {
				conjuctVpOrToken = verbChunk.get(0);
			} else {
				conjuctVpOrToken = verbToken;
			}
			if (conjuctVpOrToken != null) {
				return Optional.of(Triple.of(conjuctVpOrToken, order, role));
			}
		}

		return Optional.empty();
	}

	/** {@inheritDoc} */
	@Override
	protected Optional<Pair<Annotation, EWordOrder>> findImplicitVerbSubjectByConjunction(JCas jCas, Chunk vp) {
		Optional<Dependency> optSubjDependency;
		optSubjDependency = followVerbConjunctionsToSubject(jCas, vp);

		if (optSubjDependency.isPresent()) {
			// Get subject chunk or token
			Token subjToken = optSubjDependency.get().getDependent();
			Annotation subjChunkOrToken = NLPUtil.getCoveringNounPhraseOrToken(jCas, subjToken);

			// Get verb token
			Token verbToken = optSubjDependency.get().getGovernor();

			// Get object token
			Optional<Dependency> optObjDependency = findObjectDependency(jCas, verbToken, true);
			Token objToken = optObjDependency.isPresent() ? optObjDependency.get().getDependent() : null;

			// Determine the word order
			EWordOrder order = determineWordOrder(subjToken, verbToken, objToken);

			return Optional.of(Pair.of(subjChunkOrToken, order));
		}
		return Optional.empty();
	}

	/** {@inheritDoc} */
	@Override
	protected List<Pair<Annotation, Annotation>> completeSubjectsByConjunction(JCas jCas, Annotation subj) {
		List<Pair<Annotation, Annotation>> result = new ArrayList<>();
		result.add(Pair.of(null, subj));

		// Collect further chunks that are connected to the found subject
		Optional<Pair<Token, Token>> optConjTokens;
		do {
			optConjTokens = followConjunctionFromAnnotation(jCas, subj);
			if (optConjTokens.isPresent()) {
				Pair<Token, Token> conjTokens = optConjTokens.get();
				Annotation conSubj = NLPUtil.selectIfCovering(Chunk.class, conjTokens.getLeft());
				Annotation conj = NLPUtil.selectIfCovering(Chunk.class, conjTokens.getRight());
				result.add(Pair.of(conj, conSubj));
				subj = conSubj;
			}
		} while (optConjTokens.isPresent());
		return result;
	}

	/** {@inheritDoc} */
	@Override
	protected int determineSubjectInsertionPoint(JCas jCas, Chunk vp, EWordOrder wo) {
		Optional<Dependency> optObjDep;
		switch (wo) {
		case SOV:
			optObjDep = findObjectDependency(jCas, vp, true);
			if (optObjDep.isPresent()) {
				Token objToken = optObjDep.get().getDependent();
				Annotation chunkOrToken = NLPUtil.selectIfCovering(Chunk.class, objToken);
				return chunkOrToken.getBegin();
			} else {
				return vp.getBegin();
			}

		case SVO:
			return vp.getBegin();

		case VS:
			return vp.getEnd() + 1;

		case SV:
			optObjDep = findObjectDependency(jCas, vp, true);
			if (optObjDep.isPresent()) {
				Token objToken = optObjDep.get().getDependent();
				if (objToken.getBegin() < vp.getBegin()) {
					Annotation chunk = NLPUtil.selectIfCovering(Chunk.class, objToken);
					return chunk.getBegin();
				} else {
					return vp.getBegin();
				}
			}
		}

		return vp.getBegin();
	}

	/** {@inheritDoc} */
	@Override
	protected int determineVerbInsertionPoint(JCas jCas, Annotation np, Annotation verb, EWordOrder wo,
			ENounRole role) {
		Annotation base = np;
		if (role == ENounRole.OBJ) {
			Optional<Dependency> optObjDep = findObjectDependency(jCas, verb, true);
			if (optObjDep.isPresent()) {
				Token origObjToken = optObjDep.get().getDependent();
				if (origObjToken.getEnd() < np.getBegin() && np.getEnd() < verb.getBegin()) {
					base = NLPUtil.selectIfCovering(Chunk.class, origObjToken);
				}
			}
		}
		switch (wo) {
		case SOV:
			return base.getEnd() + 1;
		case VS:
			return base.getBegin();
		default:
			return base.getBegin();
		}

	}

	/** Determines either an accusative or dative object dependency */
	private Optional<Dependency> findObjectDependency(JCas jCas, Annotation anno, boolean isGovernor) {
		Optional<Dependency> obj = NLPUtil.findDependency(jCas, anno, DEPENDENCY_TYPE_ACCUSATIVE_OBJECT, isGovernor);
		if (!obj.isPresent()) {
			obj = NLPUtil.findDependency(jCas, anno, DEPENDENCY_TYPE_DATIVE_OBJECT, isGovernor);
		}
		return obj;
	}

	/** Follows the conjunction dependency until a subject is found */
	private Optional<Dependency> followVerbConjunctionsToSubject(JCas jCas, Chunk vp) {
		Optional<Dependency> optSubjDependency;
		Annotation currentVerb = vp;
		do {
			Optional<Pair<Token, Token>> optConjToken = followConjunctionFromAnnotation(jCas, currentVerb);
			if (!optConjToken.isPresent()) {
				return Optional.empty();
			}
			currentVerb = optConjToken.get().getLeft();
			optSubjDependency = NLPUtil.findDependency(jCas, currentVerb, DEPENDENCY_TYPE_SUBJECT, true);
		} while (!optSubjDependency.isPresent());
		return optSubjDependency;
	}

	/** Determines the order between subject, verb, object */
	private EWordOrder determineWordOrder(Token subjToken, Token verbToken, Token objToken) {
		EWordOrder order;
		order = verbToken.getBegin() < subjToken.getBegin() ? EWordOrder.VS : EWordOrder.SV;
		if (order == EWordOrder.SV && objToken != null) {
			;
			if (verbToken.getBegin() > objToken.getBegin()) {
				order = EWordOrder.SOV;
			} else {
				order = EWordOrder.SVO;
			}
		}
		return order;
	}

	/** Follows a conjunction to the related Token */
	private Optional<Pair<Token, Token>> followConjunctionFromAnnotation(JCas jCas, Annotation chunk) {
		Optional<Dependency> optConjDep = NLPUtil.findDependency(jCas, chunk, DEPENDENCY_TYPE_CJ, false);
		if (optConjDep.isPresent()) {
			Token conjunctionToken = optConjDep.get().getGovernor();
			Optional<Dependency> kon = NLPUtil.findDependency(jCas, conjunctionToken, DEPENDENCY_TYPE_KON, false);
			if (kon.isPresent()) {
				Token relatedToken = kon.get().getGovernor();
				return Optional.of(Pair.of(relatedToken, conjunctionToken));
			}
		} else {
			Optional<Dependency> optKonDep = NLPUtil.findDependency(jCas, chunk, DEPENDENCY_TYPE_KON, true);
			if (optKonDep.isPresent()) {
				Token conjunctionToken = optKonDep.get().getDependent();
				optConjDep = NLPUtil.findDependency(jCas, conjunctionToken, DEPENDENCY_TYPE_CJ, true);
				if (optConjDep.isPresent()) {
					Token relatedToken = optConjDep.get().getDependent();
					return Optional.of(Pair.of(relatedToken, conjunctionToken));
				}
			}
		}
		return Optional.empty();
	}

}
