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
import com.specmate.model.requirements.RequirementsFactory;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.objectif.resolve.BusinessRuleUtil;
import com.specmate.objectif.resolve.rule.AndNode;
import com.specmate.objectif.resolve.rule.BusinessRuleNode;
import com.specmate.objectif.resolve.rule.LiteralNode;
import com.specmate.objectif.resolve.rule.ObjectifNode;
import com.specmate.objectif.resolve.rule.OrNode;
import com.specmate.xtext.XTextException;

public class GenerateModelFromPseudoCode implements ICEGFromRequirementGenerator {

	CEGCreation creation = new CEGCreation();

	@Override
	public CEGModel createModel(CEGModel model, String text) throws SpecmateException {
		return translateObjectIf(model);
	}

	public CEGModel translateObjectIf(CEGModel model) throws SpecmateException {
		List<BusinessRuleNode> rules;
		try {
			rules = loadRules("/resources/test_rules.objectif");
		} catch (URISyntaxException | XTextException e) {
			throw new SpecmateInternalException(ErrorCode.INTERNAL_PROBLEM, "...");
		}

		// Iterate through each rule and create corresponding CEG Graph
		int brnum = 1;
		for (BusinessRuleNode rule : rules) {
			// create node for each rule and nested rules
			String id = "BR" + (brnum++);

			// Eigneltich ist ein BR node immer ein Oder node. es gibt nur eine ausnahme. es
			// gibt nur und causes und kein oder cause
			CEGNode cegNodeBR = creation.createNode(model, id, "is present", 0, 0, NodeType.OR);

			// check for nesting
			// WurzelEffect (enthält alle effekte einer rule oder die entsprechenden
			// verschachtelten rules)
			ObjectifNode rootEffect = rule.getEffect();
			if (rootEffect instanceof BusinessRuleNode) {
				// nested

			} else {
				// not nested

				// Ursachenseite
				// WurzelCause (enthält alle Causes einer rule)
				ObjectifNode rootCause = rule.getCause();

				// 1. Variante = Business Rule enthält nur einen Cause --> dann literal node
				if (rootCause instanceof LiteralNode) {
					// erstelle einen node mit dem inhalt von root Cause und verbinde den node
					// mittels identitiy mit dem node der BR
					// Dann verlasse die Ursachen seite.
//					CEGNode cegNode = RequirementsFactory.eINSTANCE.createCEGNode();
//					cegNode.setId(SpecmateEcoreUtil.getIdForChild(model, cegNode.eClass()));
//					cegNode.setName(rootCause.toString());
//					cegNode.setVariable(((LiteralNode) rootCause).getContent());
//					cegNode.setCondition("is present");
//					model.getContents().add(cegNode);

					CEGNode cegNode = creation.createNode(model, ((LiteralNode) rootCause).getContent(), "is present",
							0, 0, null);

					creation.createConnection(model, cegNode, cegNodeBR, false);
					break;
				}

				// 2. Variante = Besteht nur aus "und" verknüpfern
				// dazu müssen wir durch den baum iterieren
				List<List<ObjectifNode>> andCausesGroups = new ArrayList<List<ObjectifNode>>();
				List<ObjectifNode> effects = new ArrayList<ObjectifNode>();
				List<ObjectifNode> singleOrCauses = new ArrayList<ObjectifNode>();

				causes(rootCause, singleOrCauses, andCausesGroups);

				effects(rootEffect, effects);

				for (List<ObjectifNode> andCauses : andCausesGroups) {

					CEGNode intermediateNode = null;

					if (andCausesGroups.size() >= 1) {
//						intermediateNode = RequirementsFactory.eINSTANCE.createCEGNode();
//						intermediateNode.setId(SpecmateEcoreUtil.getIdForChild(model, intermediateNode.eClass()));
//						intermediateNode.setName("Intermediate Node");
//						intermediateNode.setVariable("Intermediate Node");
//						intermediateNode.setCondition("is present");
//						model.getContents().add(intermediateNode);

						intermediateNode = creation.createNode(model, "Intermediate Node", "is present", 0, 0, null);

						creation.createConnection(model, intermediateNode, cegNodeBR, false);
					}

					for (ObjectifNode cause : andCauses) {
						// jetzt werden alle UndCauses mit der jweiligen Business Rule verbunden mittels
						// Konjunktion
//						CEGNode cegNode = RequirementsFactory.eINSTANCE.createCEGNode();
//						cegNode.setId(SpecmateEcoreUtil.getIdForChild(model, cegNode.eClass()));
//						cegNode.setName(cause.toString());
//						cegNode.setVariable(((LiteralNode) cause).getContent());
//						cegNode.setCondition("is present");
//						model.getContents().add(cegNode);

						CEGNode cegNode = creation.createNode(model, ((LiteralNode) cause).getContent(), "is present",
								0, 0, null);

						// zwei Fälle:
						// a) es gibt nur ein Päckchen an Unds und die liste an veroderten Causes ist
						// leer --> kein zwischenknoten pro päckchen; alle knoten direkt mit der BR
						// verbinden

						if (andCausesGroups.size() == 1 && singleOrCauses.size() == 0) {
							creation.createConnection(model, cegNode, cegNodeBR, false);
							cegNodeBR.setType(NodeType.AND);
						} else if (andCausesGroups.size() >= 1) {
							creation.createConnection(model, cegNode, intermediateNode, false);
						}
					}
				}
				// Jetzt laufen wir noch durch alle single causes durch und verbinden dieser per
				// Oder mit der BR node
				for (ObjectifNode cause : singleOrCauses) {
					// CEGNode cegNode = RequirementsFactory.eINSTANCE.createCEGNode();
					// cegNode.setId(SpecmateEcoreUtil.getIdForChild(model, cegNode.eClass()));
					// cegNode.setName(cause.toString());
					// cegNode.setVariable(((LiteralNode) cause).getContent());
					// cegNode.setCondition("is present");
					// model.getContents().add(cegNode);

					CEGNode cegNode = creation.createNode(model, ((LiteralNode) cause).getContent(), "is present", 0, 0,
							null);
					creation.createConnection(model, cegNode, cegNodeBR, false);
				}

				// Durch alle effekte laufen und die BR damit verbunden.
				for (ObjectifNode effect : effects) {
					/*
					 * CEGNode cegNode = RequirementsFactory.eINSTANCE.createCEGNode();
					 * cegNode.setId(SpecmateEcoreUtil.getIdForChild(model, cegNode.eClass()));
					 * cegNode.setName(effect.toString()); cegNode.setVariable(((LiteralNode)
					 * effect).getContent()); cegNode.setCondition("is present");
					 * model.getContents().add(cegNode);
					 */
					
					CEGNode cegNode = creation.createNode(model, ((LiteralNode) effect).getContent(), "is present", 0, 0,
							null);
					
					
					creation.createConnection(model, cegNodeBR, cegNode, false);
				}
			}
		}
		return model;
	}

	private void effects(ObjectifNode rootEffect, List<ObjectifNode> effects) {
		// 1. Fall: es gibt nur einen effect
		if (rootEffect instanceof LiteralNode) {
			effects.add(rootEffect);
		}

		// 2. Fall: die effekte sind miteinander verschachtelt
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

	private void connectAndCauses(ObjectifNode rootCause, List<ObjectifNode> andCauses) {
		if (rootCause instanceof AndNode) {

			if (rootCause.getLeft() instanceof LiteralNode) {
				andCauses.add(rootCause.getLeft());
			}

			if (rootCause.getRight() instanceof LiteralNode) {
				andCauses.add(rootCause.getRight());
			}

			if (rootCause.getLeft() instanceof AndNode) {
				// wiederholt aufrufen aber mit geupdateter root cause
				connectAndCauses(rootCause.getLeft(), andCauses);
			}

			if (rootCause.getRight() instanceof AndNode) {
				// wiederholt aufrufen aber mit geupdateter root cause
				connectAndCauses(rootCause.getRight(), andCauses);
			}
		}
	}

	private void causes(ObjectifNode rootCause, List<ObjectifNode> orCauses, List<List<ObjectifNode>> causesGroups) {
		if (rootCause instanceof OrNode) {
			// jetzt brauchen wir für die Und Konnektoren jeweils Zwischenknoten
			// für jedes Päckchen erstellen wir eine neue Liste an Causes

			// Die beiden einfachsten Fälle: ein einzelnes Oder mit nur zwei Literalen
			if (rootCause.getLeft() instanceof LiteralNode) {
				orCauses.add(rootCause.getLeft());
			}

			if (rootCause.getRight() instanceof LiteralNode) {
				orCauses.add(rootCause.getRight());
			}

			if (rootCause.getLeft() instanceof AndNode) {
				// offensichtlich entsteht hier ein neues Und Päckchen
				// Wir sollten eine entsprechende Liste erstellen und das Päckchen befüllen
				List<ObjectifNode> andCauses = new ArrayList<ObjectifNode>();
				connectAndCauses(rootCause.getLeft(), andCauses);
				causesGroups.add(andCauses);
			}

			if (rootCause.getRight() instanceof AndNode) {
				// offensichtlich entsteht hier ein neues Und Päckchen
				// Wir sollten eine entsprechende Liste erstellen und das Päckchen befüllen
				List<ObjectifNode> andCauses = new ArrayList<ObjectifNode>();
				connectAndCauses(rootCause.getRight(), andCauses);
				causesGroups.add(andCauses);
			}

			if (rootCause.getRight() instanceof OrNode) {
				causes(rootCause.getRight(), orCauses, causesGroups);
			}

			if (rootCause.getLeft() instanceof OrNode) {
				causes(rootCause.getLeft(), orCauses, causesGroups);
			}

		} else if (rootCause instanceof AndNode) {
			List<ObjectifNode> andCauses = new ArrayList<ObjectifNode>();
			connectAndCauses(rootCause, andCauses);
			causesGroups.add(andCauses);

		}
	}

	private List<BusinessRuleNode> loadRules(String mainFile) throws URISyntaxException, XTextException {
		URI main = getLocalFile(mainFile);
		return new BusinessRuleUtil().loadXTextResources(main);
	}

	private URI getLocalFile(String fileName) throws URISyntaxException {
		Bundle bundle = FrameworkUtil.getBundle(GenerateModelFromRequirementService.class);
		return URI.createURI(bundle.getResource(fileName).toURI().toString());
	}

}
