package com.specmate.objectif.resolve;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.ISetup;

import com.specmate.objectif.internal.DSLStandaloneSetup;
import com.specmate.objectif.internal.dSL.AND_Node;
import com.specmate.objectif.internal.dSL.BusinessRule;
import com.specmate.objectif.internal.dSL.Literal;
import com.specmate.objectif.internal.dSL.Model;
import com.specmate.objectif.internal.dSL.OR_Node;
import com.specmate.objectif.resolve.rule.AndNode;
import com.specmate.objectif.resolve.rule.BusinessRuleNode;
import com.specmate.objectif.resolve.rule.LiteralNode;
import com.specmate.objectif.resolve.rule.ObjectifNode;
import com.specmate.objectif.resolve.rule.OrNode;
import com.specmate.xtext.XTextResourceProcessor;

public class BusinessRuleUtil extends XTextResourceProcessor<BusinessRuleNode>{

	@Override
	public ISetup getGrammar() {
		return new DSLStandaloneSetup();
	}
	
	@Override
	public List<BusinessRuleNode> process(EList<EObject> eList) {
		return eList.stream()
		.filter(e -> e instanceof Model)
		.flatMap(m -> ((Model)m).getRules().stream())
		.map(BusinessRuleUtil::resolveRule)
		.collect(Collectors.toList());
	}

	private static BusinessRuleNode resolveRule(BusinessRule businessRule) {
		ObjectifNode cause =  resolveOrNode(businessRule.getCause());
		ObjectifNode effect = resolveEffect(businessRule.getEffect());
		if(businessRule.getAlternative() != null) {
			ObjectifNode alternative = resolveEffect(businessRule.getAlternative());
			return new BusinessRuleNode(cause, effect, alternative);
		}
		return new BusinessRuleNode(cause, effect);
	}

	private static ObjectifNode resolveEffect(EObject eObject) {
		if(eObject instanceof BusinessRule) {
			return resolveRule((BusinessRule)eObject);
		}
		return resolveOrNode((OR_Node) eObject); 
	}

	private static ObjectifNode resolveOrNode(OR_Node orNode) {
		ObjectifNode node = resolveAndNode(orNode.getNode());
		if(orNode.isOr()) {
			ObjectifNode sub = resolveOrNode(orNode.getSubNode());
			return new OrNode(node, sub);
		}
		return node;
		
	}
	
	private static ObjectifNode resolveAndNode(AND_Node andNode) {
		ObjectifNode node = resolveLiteralNode(andNode.getNode());
		if(andNode.isAnd()) {
			ObjectifNode sub = resolveAndNode(andNode.getSubNode());
			return new AndNode(node, sub);
		}
		return node;
	}

	private static ObjectifNode resolveLiteralNode(Literal literal) {
		EList<String> contentList = literal.getContent();
		String[] content = contentList.toArray(new String[contentList.size()]);
		return new LiteralNode(content);
	}
}
