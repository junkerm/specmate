package com.specmate.nlp.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Pattern;

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

	private static final Pattern CONJ_PATTERN = Pattern.compile("(?<!,)(\\s+(und|oder))");

	/** {@inheritDoc} */
	@Override
	protected Optional<Dependency> findSubjectDependency(JCas jCas, Annotation anno, boolean isGovernor) {
		Optional<Dependency> subj = NLPUtil.findDependency(jCas, anno, DEPENDENCY_TYPE_SUBJECT, isGovernor);
		if (!subj.isPresent()) {
			Optional<Dependency> aux = NLPUtil.findDependency(jCas, anno, "AUX", false);
			if (aux.isPresent()) {
				return findSubjectDependency(jCas, aux.get().getGovernor(), isGovernor);
			}
			return Optional.empty();
		}
		return subj;
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

			// Get verb token and check if its an auxillary verb. If yes, get the main verg
			Token verbToken = optSubjDependency.get().getGovernor();
			Optional<Dependency> aux = NLPUtil.findDependency(jCas, verbToken, "AUX", true);
			if (aux.isPresent()) {
				verbToken = aux.get().getDependent();
			}

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
		Token origObjToken = null;
		if (role == ENounRole.OBJ) {
			Optional<Dependency> optObjDep = findObjectDependency(jCas, verb, true);
			if (optObjDep.isPresent()) {
				origObjToken = optObjDep.get().getDependent();
			}
		} else if (role == ENounRole.SUBJ) {
			Optional<Dependency> optObjDep = findSubjectDependency(jCas, verb, true);
			if (optObjDep.isPresent()) {
				origObjToken = optObjDep.get().getDependent();
			}
		}
		if (origObjToken != null && origObjToken.getEnd() < np.getBegin() && np.getEnd() < verb.getBegin()) {
			base = NLPUtil.selectIfCovering(Chunk.class, origObjToken);
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
	@Override
	protected Optional<Dependency> findObjectDependency(JCas jCas, Annotation anno, boolean isGovernor) {
		Optional<Dependency> optObjDep = NLPUtil.findDependency(jCas, anno, DEPENDENCY_TYPE_ACCUSATIVE_OBJECT,
				isGovernor);
		if (!optObjDep.isPresent()) {
			optObjDep = NLPUtil.findDependency(jCas, anno, DEPENDENCY_TYPE_DATIVE_OBJECT, isGovernor);
		}
		return optObjDep;
	}

	/** Follows the conjunction dependency until a subject is found */
	private Optional<Dependency> followVerbConjunctionsToSubject(JCas jCas, Chunk vp) {
		Optional<Dependency> optSubjDependency;
		Annotation currentVerb = vp;
		Set<Annotation> seen = new HashSet<>();
		do {
			seen.add(currentVerb);
			Optional<Pair<Token, Token>> optConjToken = followConjunctionFromAnnotation(jCas, currentVerb);
			if (!optConjToken.isPresent()) {
				return Optional.empty();
			}
			currentVerb = optConjToken.get().getLeft();
			optSubjDependency = findSubjectDependency(jCas, currentVerb, true);
			if (seen.contains(currentVerb)) {
				break;
			}
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

	@Override
	protected List<Dependency> getConjunctiveAdjectiveModifyers(JCas jCas, Annotation np) {
		return new Vector<Dependency>();
	}

	@Override
	protected List<Pair<Integer, String>> completeConjunctiveAdjectiveNounPhrase(JCas jCas, Annotation np,
			List<Dependency> modifiers) {
		return new Vector<Pair<Integer, String>>();
	}

	@Override
	protected String insertCommasBeforeConjunctions(String text) {
		return CONJ_PATTERN.matcher(text).replaceAll(r -> "," + r.group(1));
	}

	@Override
	protected Optional<Annotation> getAssociatedSubjectConditional(JCas jCas, Annotation subject) {
		Optional<Dependency> optSubjectDep = findSubjectDependency(jCas, subject, false);
		if (optSubjectDep.isEmpty()) {
			return Optional.empty();
		}
		Token verb = optSubjectDep.get().getGovernor();
		Optional<Dependency> optConditionalDep = NLPUtil.findDependency(jCas, verb, "KONJ", true);
		if (optConditionalDep.isPresent()) {
			return Optional.of(optConditionalDep.get().getDependent());
		}

		return Optional.empty();
	}

}
