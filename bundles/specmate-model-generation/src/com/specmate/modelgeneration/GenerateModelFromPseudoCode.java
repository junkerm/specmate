package com.specmate.modelgeneration;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import com.specmate.common.exception.SpecmateException;
import com.specmate.common.exception.SpecmateInternalException;
import com.specmate.model.administration.ErrorCode;
import com.specmate.model.requirements.CEGModel;
import com.specmate.model.requirements.CEGNode;
import com.specmate.model.requirements.NodeType;
import com.specmate.objectif.resolve.BusinessRuleUtil;
import com.specmate.objectif.resolve.rule.AndNode;
import com.specmate.objectif.resolve.rule.BusinessRuleNode;
import com.specmate.objectif.resolve.rule.LiteralNode;
import com.specmate.objectif.resolve.rule.ObjectifNode;
import com.specmate.objectif.resolve.rule.OrNode;
import com.specmate.xtext.XTextException;

// This class transforms pseudo code into a CEG.
// The pseudo code must conform with the implemented XText grammar.
public class GenerateModelFromPseudoCode implements ICEGFromRequirementGenerator {

	// Our factory to create the elements of the CEG (nodes and connections).
	CEGCreation creation = new CEGCreation();

	// Counter for numbering of intermediate nodes as well as business rule nodes.
	int intermediatenum = 1;
	int brnum = 1;

	@Override
	public CEGModel createModel(CEGModel model, String text) throws SpecmateException {
		return translateObjectIf(model);
	}

	//
	public CEGModel translateObjectIf(CEGModel model) throws SpecmateException {
		List<BusinessRuleNode> rules;
		// First, we need to load all pseudo code lines from a document / input.
		try {
			rules = loadRules("/resources/test_rules.objectif");
		} catch (URISyntaxException | XTextException e) {
			throw new SpecmateInternalException(ErrorCode.INTERNAL_PROBLEM, "...");
		}

		BusinessRuleNode rule = rules.get(0);
		return createCEGModelForEachSection(rule, model, null, false);
	}

	// This method creates a CEG model for each "WENN" --> "DANN" --> "ENDE-WENN"
	// clause.
	private CEGModel createCEGModelForEachSection(BusinessRuleNode rule, CEGModel model, CEGNode brNode,
			boolean nesting) {

		// The type of a BR node should be always "OR". Exception: The model consists
		// only of conjunctive causes.
		CEGNode cegNodeBR;

		// If the brNode is null, we start with a new model.
		if (brNode == null) {
			cegNodeBR = creation.createNode(model, "BR" + (brnum++), "is present", 0, 0, NodeType.OR);
		} else {
			// Otherwise, we are within a recursive loop (i.e. the clause is nested) and the
			// brNode is our superior
			// business rule.
			cegNodeBR = brNode;
		}

		// Check for nesting:
		// The rootEffect contains all effects of a rule. If the rootEffect is a
		// "BusinessRuleNode", the rule is nested and consists of multiple rules.
		// Here we check if we need to do a recursion again or if we reached the
		// business rule at the lowest depth (i.e. the business rule which does not
		// contains another one).
		ObjectifNode rootEffect = rule.getEffect();
		if (rootEffect instanceof BusinessRuleNode) {
			// Case: nested
			// Basic idea: Split the whole tree in subtrees (i.e. split the tree into
			// individual business rules) and connect first the causes to these business
			// rules and then simply connect superior rules to inferior rules.
			CEGNode cegNodeBRNested = creation.createNode(model, "BR" + (brnum++), "is present", 0, 0, NodeType.AND);
			List<List<ObjectifNode>> andCausesGroups = new ArrayList<List<ObjectifNode>>();
			List<ObjectifNode> singleOrCauses = new ArrayList<ObjectifNode>();

			ObjectifNode rootCause = rule.getCause();
			identifyCauses(rootCause, singleOrCauses, andCausesGroups);

			if (nesting == false) {
				connectCausesToBR(cegNodeBR, singleOrCauses, andCausesGroups, model);
			} else {
				connectCausesToNestedBR(cegNodeBR, singleOrCauses, andCausesGroups, model);
			}

			// We do not need to identify the effects of the business rule since we know
			// that the rule contains another one and is therefore a condition for the
			// nested rule. We simply connect the rule with the inferior rule.
			creation.createConnection(model, cegNodeBR, cegNodeBRNested, false);

			// Start with the recursion. We update the root and do the whole procedure for
			// the inferior rule again. Until we reach a rule which is not nested anymore.
			createCEGModelForEachSection((BusinessRuleNode) rootEffect, model, cegNodeBRNested, true);

		} else {
			// Case: not nested
			// RootCause contains all causes of a rule.
			ObjectifNode rootCause = rule.getCause();
			List<ObjectifNode> effects = new ArrayList<ObjectifNode>();

			// 1. Case = The rule contains only one cause. This is the case if the rootCause
			// is a "LiteralNode".
			if (rootCause instanceof LiteralNode) {
				// Create a node with the content of the rootCause and connect the node with the
				// rule node by means of identity connection.
				CEGNode cegNode = creation.createNode(model, ((LiteralNode) rootCause).getContent(), "is present", 0, 0,
						null);

				creation.createConnection(model, cegNode, cegNodeBR, false);
				// Search for effects of the rule.
				identifyEffects(rootEffect, effects);

				// Connect the rule node with all identified effects.
				for (ObjectifNode effect : effects) {
					CEGNode cegNodeEffect = creation.createNode(model, ((LiteralNode) effect).getContent(),
							"is present", 0, 0, null);
					creation.createConnection(model, cegNodeBR, cegNodeEffect, false);
				}
			} else {

				// 2. Case = The rule contains multiple causes which are interconnected
				// (conjunctions or disjunctions).
				List<List<ObjectifNode>> andCausesGroups = new ArrayList<List<ObjectifNode>>();
				List<ObjectifNode> singleOrCauses = new ArrayList<ObjectifNode>();

				identifyCauses(rootCause, singleOrCauses, andCausesGroups);
				identifyEffects(rootEffect, effects);

				if (nesting == false) {
					connectCausesToBR(cegNodeBR, singleOrCauses, andCausesGroups, model);
				} else {
					connectCausesToNestedBR(cegNodeBR, singleOrCauses, andCausesGroups, model);
				}
				connectBRToEffects(cegNodeBR, effects, model);
			}
		}
		return model;
	}

	private void connectBRToEffects(CEGNode cegNodeBR, List<ObjectifNode> effects, CEGModel model) {
		// Iterate over all effects and connect the business rule node with these
		// effects.
		for (ObjectifNode effect : effects) {
			CEGNode cegNode = creation.createNode(model, ((LiteralNode) effect).getContent(), "is present", 0, 0, null);
			creation.createConnection(model, cegNodeBR, cegNode, false);
		}
	}

	// Implementation for non nested BRs
	private void connectCausesToBR(CEGNode cegNodeBR, List<ObjectifNode> singleOrCauses,
			List<List<ObjectifNode>> andCausesGroups, CEGModel model) {

		for (List<ObjectifNode> andCauses : andCausesGroups) {

			CEGNode intermediateNode = null;

			if (andCausesGroups.size() > 0 && singleOrCauses.size() != 0) {
				intermediateNode = creation.createNode(model, "Intermediate Node" + (intermediatenum++), "is present",
						0, 0, NodeType.AND);

				creation.createConnection(model, intermediateNode, cegNodeBR, false);
			}

			for (ObjectifNode cause : andCauses) {
				CEGNode cegNode = creation.createNode(model, ((LiteralNode) cause).getContent(), "is present", 0, 0,
						null);

				// There are two cases:
				// a) there is only one set of conjunctive causes and no single / disjunctive
				// causes --> just connect the causes with the rule without intermediate node.
				if (andCausesGroups.size() == 1 && singleOrCauses.size() == 0) {
					creation.createConnection(model, cegNode, cegNodeBR, false);
					cegNodeBR.setType(NodeType.AND);
				} else {
					// b) otherwise we need an intermediate node and connect conjunctive causes to
					// these nodes. All related causes share the same intermediate node!
					creation.createConnection(model, cegNode, intermediateNode, false);
				}
			}
		}
		// We iterate over all single causes and connect them with the BR by means of a
		// disjunction.
		for (ObjectifNode cause : singleOrCauses) {
			CEGNode cegNode = creation.createNode(model, ((LiteralNode) cause).getContent(), "is present", 0, 0, null);
			creation.createConnection(model, cegNode, cegNodeBR, false);
		}
	}

	// Implementation for nested BRs:
	// It is important to differentiate between non nested and nested BR since
	// nested BRs have always the NodeType "AND" (because superior BRs are always a
	// condition for the nested ones). As a result, here we need intermediate nodes
	// for the disjunctive causes and no intermediate nodes for the conjunction
	// since they can be directly connected to the BR.
	private void connectCausesToNestedBR(CEGNode cegNodeBR, List<ObjectifNode> singleOrCauses,
			List<List<ObjectifNode>> andCausesGroups, CEGModel model) {

		CEGNode intermediateNodeNested = null;

		if (singleOrCauses.size() > 1) {
			System.out.println("hallo" + brnum);
			intermediateNodeNested = creation.createNode(model, "Intermediate Node" + (intermediatenum++), "is present",
					0, 0, NodeType.OR);
			creation.createConnection(model, intermediateNodeNested, cegNodeBR, false);
		}

		for (List<ObjectifNode> andCauses : andCausesGroups) {

			CEGNode intermediateNode = null;

			if (andCausesGroups.size() > 0 && singleOrCauses.size() != 0) {
				intermediateNode = creation.createNode(model, "Intermediate Node" + (intermediatenum++), "is present",
						0, 0, NodeType.AND);
				creation.createConnection(model, intermediateNode, intermediateNodeNested, false);
			}

			for (ObjectifNode cause : andCauses) {
				CEGNode cegNode = creation.createNode(model, ((LiteralNode) cause).getContent(), "is present", 0, 0,
						null);

				if (singleOrCauses.size() == 1 || andCausesGroups.size() == 1 && singleOrCauses.size() == 0) {
					creation.createConnection(model, cegNode, cegNodeBR, false);
				} else {
					creation.createConnection(model, cegNode, intermediateNode, false);
				}
			}
		}

		for (ObjectifNode cause : singleOrCauses) {
			CEGNode cegNode = creation.createNode(model, ((LiteralNode) cause).getContent(), "is present", 0, 0, null);
			if (singleOrCauses.size() == 1) {
				creation.createConnection(model, cegNode, cegNodeBR, false);
			} else {
				creation.createConnection(model, cegNode, intermediateNodeNested, false);
			}
		}
	}

	private void identifyEffects(ObjectifNode rootEffect, List<ObjectifNode> effects) {
		// 1. Case: There is only a single effect
		if (rootEffect instanceof LiteralNode) {
			effects.add(rootEffect);
		}

		// 2. Case: The effects are interconnected.
		if (rootEffect instanceof AndNode) {

			if (rootEffect.getLeft() instanceof LiteralNode) {
				effects.add(rootEffect.getLeft());
			}

			if (rootEffect.getRight() instanceof LiteralNode) {
				effects.add(rootEffect.getRight());
			}

			if (rootEffect.getLeft() instanceof AndNode) {
				// wiederholt aufrufen aber mit geupdateter root cause
				connectAndCauses(rootEffect.getLeft(), effects);
			}

			if (rootEffect.getRight() instanceof AndNode) {
				// wiederholt aufrufen aber mit geupdateter root cause
				connectAndCauses(rootEffect.getRight(), effects);
			}
		}
	}

	// Here we connect causes which are interconnected using conjunctions.
	// Related causes are saved within individual lists.
	private void connectAndCauses(ObjectifNode rootCause, List<ObjectifNode> andCauses) {
		if (rootCause instanceof AndNode) {

			if (rootCause.getLeft() instanceof LiteralNode) {
				// add cause to the list.
				andCauses.add(rootCause.getLeft());
			}

			if (rootCause.getRight() instanceof LiteralNode) {
				// add cause to the list.
				andCauses.add(rootCause.getRight());
			}

			if (rootCause.getLeft() instanceof AndNode) {
				// recursive call of the method but with updated rootcause.
				// Here we do not (!) create a new list because this is connection of multiple
				// conjunctions. So we build a large list of multiple interconnected causes.
				connectAndCauses(rootCause.getLeft(), andCauses);
			}

			if (rootCause.getRight() instanceof AndNode) {
				// recursive call of the method but with updated rootcause.
				// See explanation above.
				connectAndCauses(rootCause.getRight(), andCauses);
			}
		}
	}

	// Here we separate between isolated causes (disjunctive causes) and related
	// causes (conjunctive causes).
	private void identifyCauses(ObjectifNode rootCause, List<ObjectifNode> orCauses,
			List<List<ObjectifNode>> causesGroups) {
		if (rootCause instanceof OrNode) {
			// 1. Case: A single "or" connection
			if (rootCause.getLeft() instanceof LiteralNode) {
				orCauses.add(rootCause.getLeft());
			}

			// belongs to case 1
			if (rootCause.getRight() instanceof LiteralNode) {
				orCauses.add(rootCause.getRight());
			}

			if (rootCause.getLeft() instanceof AndNode) {
				// Here we identify a conjunction. We create a list of Objectif nodes for each
				// identified conjunction.
				List<ObjectifNode> andCauses = new ArrayList<ObjectifNode>();
				connectAndCauses(rootCause.getLeft(), andCauses);
				causesGroups.add(andCauses);
			}

			if (rootCause.getRight() instanceof AndNode) {
				// Here we identify also a conjunction. Same procedure as described above.
				List<ObjectifNode> andCauses = new ArrayList<ObjectifNode>();
				connectAndCauses(rootCause.getRight(), andCauses);
				causesGroups.add(andCauses);
			}

			if (rootCause.getRight() instanceof OrNode) {
				// recursive call of the method with updated rootCause
				identifyCauses(rootCause.getRight(), orCauses, causesGroups);
			}

			if (rootCause.getLeft() instanceof OrNode) {
				// same here
				identifyCauses(rootCause.getLeft(), orCauses, causesGroups);
			}

		} else if (rootCause instanceof AndNode) {
			// Exceptional case if rootCause is a "AndNode".
			// Here we also need to create a new list of conjunctive nodes.
			List<ObjectifNode> andCauses = new ArrayList<ObjectifNode>();
			connectAndCauses(rootCause, andCauses);
			causesGroups.add(andCauses);

		} else if (rootCause instanceof LiteralNode) {
			// This is the case if the rule consists of only one cause.
			orCauses.add(rootCause);
		}
	}

	// Method that loads all rules from a local file.
	private List<BusinessRuleNode> loadRules(String mainFile) throws URISyntaxException, XTextException {
		URI main = getLocalFile(mainFile);
		return new BusinessRuleUtil().loadXTextResources(main);
	}

	private URI getLocalFile(String fileName) throws URISyntaxException {
		Bundle bundle = FrameworkUtil.getBundle(GenerateModelFromRequirementService.class);
		return URI.createURI(bundle.getResource(fileName).toURI().toString());
	}
}
