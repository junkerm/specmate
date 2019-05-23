package com.specmate.nlp.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Vector;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import com.specmate.nlp.util.NLPUtil.ConstituentType;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.chunk.Chunk;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.dependency.Dependency;

public class EnglishSentenceUnfolder extends SentenceUnfolderBase {

	/** The dependency types for a cunjunction depenendenc (and, or) */
	private static final String DEPENDENCY_TYPE_CONJUNCTION = "conj";
	private static final String DEPENDENCY_TYPE_CC = "cc";

	/** The dependency type for a subject dependency */
	private static final String DEPENDENCY_TYPE_SUBJECT = "nsubj";
	/** The dependency type for a passive subject dependency */
	private static final String DEPENDENCY_TYPE_SUBJECT_PASS = "nsubjpass";

	/** The dependency type for accusative (direct) objets */
	private static final String DEPENDENCY_TYPE_ACCUSATIVE_OBJECT = "dobj";
	private static final String DEPENDENCY_TYPE_ADJECTIVE_MODIFIER = "amod";

	@Override
	protected Optional<Dependency> findSubjectDependency(JCas jCas, Annotation vp, boolean isGovernor) {
		Optional<Dependency> result = NLPUtil.findDependency(jCas, vp, DEPENDENCY_TYPE_SUBJECT, isGovernor); 
		if(result.isPresent()) {
			return result; 
		}
		return NLPUtil.findDependency(jCas, vp, DEPENDENCY_TYPE_SUBJECT_PASS, isGovernor);
	}

	@Override
	protected Optional<Pair<Annotation, EWordOrder>> findImplicitVerbSubjectByConjunction(JCas jCas, Chunk vp) {
		Optional<Pair<Token, Token>> optRelatedVerb = followConjunctionFromAnnotation(jCas, vp);
		if (optRelatedVerb.isPresent()) {
			Token relatedVerb = optRelatedVerb.get().getLeft();
			Optional<Dependency> subj = NLPUtil.findDependency(jCas, relatedVerb, DEPENDENCY_TYPE_SUBJECT, true);
			if (subj.isPresent()) {
				Token subjToken = subj.get().getDependent();
				List<Chunk> chunk = JCasUtil.selectCovering(jCas, Chunk.class, subjToken);
				Chunk np = chunk.get(0);
				if (np.getChunkValue().equals(ConstituentType.NP.getName())) {
					return Optional.of(Pair.of(np, EWordOrder.SOV));
				}
			}
		}
		return Optional.empty();
	}

	@Override
	protected List<Pair<Annotation, Annotation>> completeSubjectsByConjunction(JCas jCas, Annotation subj) {
		Collection<Dependency> dependencies = JCasUtil.select(jCas, Dependency.class);
		List<Pair<Annotation, Annotation>> result = new ArrayList<>();
		result.add(Pair.of(null, subj));
		Optional<Dependency> conjDep;
		Optional<Dependency> ccDep;
		do {
			conjDep = NLPUtil.findDependency(dependencies, subj, DEPENDENCY_TYPE_CONJUNCTION, true);
			ccDep = NLPUtil.findDependency(dependencies, subj, DEPENDENCY_TYPE_CC, true);
			if (conjDep.isPresent() && ccDep.isPresent()) {
				Token govConjToken = conjDep.get().getDependent();
				Token govCcToken = ccDep.get().getDependent();
				Annotation conSubj = NLPUtil.selectIfCovering(Chunk.class, govConjToken);
				Annotation cc = NLPUtil.selectIfCovering(Chunk.class, govCcToken);
				result.add(Pair.of(cc, conSubj));
				subj = conSubj;
			}
		} while (conjDep.isPresent());
		return result;
	}

	@Override
	protected int determineSubjectInsertionPoint(JCas jcas, Chunk vp, EWordOrder order) {
		return vp.getBegin();
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
			List<Chunk> verbChunk = JCasUtil.selectCovering(jCas, Chunk.class, verbToken);
			Annotation conjuctVpOrToken;
			if (verbChunk.size() > 0 && verbChunk.get(0).getChunkValue().equals(NLPUtil.ConstituentType.VP.getName())) {
				conjuctVpOrToken = verbChunk.get(0);
			} else {
				conjuctVpOrToken = verbToken;
			}
			if (conjuctVpOrToken != null) {
				return Optional.of(Triple.of(conjuctVpOrToken, EWordOrder.SVO, role));
			}
		}

		return Optional.empty();
	}

	@Override
	protected int determineVerbInsertionPoint(JCas jcas, Annotation np, Annotation verb, EWordOrder order,
			ENounRole role) {
		
		// In case of a noun conjunction we place the verb before the conjunction  (Noun <X> and Noun)
		// Otherwise we get a conjunction between verb and noun (Noun and <X> Noun)
		Collection<Dependency> dependencies = JCasUtil.select(jcas, Dependency.class);
		Optional<Dependency> conjDep = NLPUtil.findDependency(dependencies, np, DEPENDENCY_TYPE_CONJUNCTION, false);
		if(conjDep.isPresent()) {
			Token firstNoun = conjDep.get().getGovernor();
			Optional<Dependency> ccDep = NLPUtil.findDependency(dependencies, firstNoun, DEPENDENCY_TYPE_CC, true);
			if(ccDep.isPresent()) {
				return ccDep.get().getBegin();
			}
		}
		
		return np.getBegin();
	}

	/** Determines either an accusative or dative object dependency */
	private Optional<Dependency> findObjectDependency(JCas jCas, Annotation anno, boolean isGovernor) {
		Optional<Dependency> obj = NLPUtil.findDependency(jCas, anno, DEPENDENCY_TYPE_ACCUSATIVE_OBJECT, isGovernor);
		return obj;
	}

	/** Follows a conjunction to the related Token */
	private Optional<Pair<Token, Token>> followConjunctionFromAnnotation(JCas jCas, Annotation chunk) {
		Token relatedToken = null;
		Optional<Dependency> optConjDep = NLPUtil.findDependency(jCas, chunk, DEPENDENCY_TYPE_CONJUNCTION, false);
		if (optConjDep.isPresent()) {
			relatedToken = optConjDep.get().getGovernor();
		} else {
			optConjDep = NLPUtil.findDependency(jCas, chunk, DEPENDENCY_TYPE_CONJUNCTION, true);
			if (optConjDep.isPresent()) {
				relatedToken = optConjDep.get().getDependent();
			}
		}
		if (relatedToken != null) {
			Optional<Dependency> optCcDep = NLPUtil.findDependency(jCas, chunk, DEPENDENCY_TYPE_CC, true);
			Token conjunctionToken = null;
			if (optCcDep.isPresent()) {
				conjunctionToken = optCcDep.get().getGovernor();
			}
			return Optional.of(Pair.of(relatedToken, conjunctionToken));
		}
		return Optional.empty();
	}

	
	
	@Override
	protected List<Dependency> getConjunctiveAdjectiveModifyers(JCas jCas, Annotation np) {
		Collection<Dependency> dependencies = JCasUtil.select(jCas, Dependency.class);
		List<Dependency> modifyers = NLPUtil.findDependencies(dependencies, np, DEPENDENCY_TYPE_ADJECTIVE_MODIFIER, true);
		
		Vector<Dependency> result = new Vector<Dependency>();
		
		for(Dependency modifyer: modifyers) {
			Token firstModifyer = modifyer.getDependent();
			Optional<Dependency> ccDep = NLPUtil.findDependency(dependencies, firstModifyer, DEPENDENCY_TYPE_CC, true);
			Optional<Dependency> secondModifyerDep = NLPUtil.findDependency(dependencies, firstModifyer, DEPENDENCY_TYPE_CONJUNCTION, true);
			
			if(ccDep.isPresent() && secondModifyerDep.isPresent()) {
				result.add(modifyer);
			}
		}
		return result;
	}

	@Override
	protected List<Pair<Integer, String>> completeConjunctiveAdjectiveNounPhrase(JCas jCas, Annotation np, List<Dependency> modifyers) {
		Collection<Dependency> dependencies = JCasUtil.select(jCas, Dependency.class);
		Vector<Pair<Integer,String>> result = new Vector<Pair<Integer,String>>();
		
		for(Dependency modifyer: modifyers) {
			Token noun = modifyer.getGovernor();
			Token firstModifyer = modifyer.getDependent();
			Token conjunction   = NLPUtil.findDependency(dependencies, firstModifyer, DEPENDENCY_TYPE_CC, true).get().getDependent();
			result.add(Pair.of(conjunction.getBegin(), noun.getCoveredText()));
		}
		return result;
	}

}
