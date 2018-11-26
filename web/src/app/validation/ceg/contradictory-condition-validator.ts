import { Validator } from '../validator-decorator';
import { CEGModel } from '../../model/CEGModel';
import { ElementValidatorBase } from '../element-validator-base';
import { VALID } from '@angular/forms/src/model';
import { ValidationResult } from '../validation-result';
import { IContainer } from '../../model/IContainer';
import { CEGConnection } from '../../model/CEGConnection';
import { CEGNode } from '../../model/CEGNode';
import { Type } from '../../util/type';
import { Sets } from '../../util/sets';
import { CheckboxControlValueAccessor } from '@angular/forms';


@Validator(CEGModel)
export class ContradictoryCondidionValidator extends ElementValidatorBase<CEGModel> {
    public validate(element: CEGModel, contents: IContainer[]): ValidationResult {
        let nodes = contents.filter( elem => Type.is(elem, CEGNode)).map( n => n as CEGNode);
        let edges = contents.filter( elem => Type.is(elem, CEGConnection)).map( e => e as CEGConnection);

        let openList = nodes.filter(node => node.incomingConnections.length == 0);
        let dependencies = {};
        let closedSet = new Set<CEGNode>();
        let parentMap: { [url: string]: [CEGNode, CEGConnection][]} = {};
        openList.forEach( open => parentMap[open.url] = []);

        while (openList.length > 0) {
            let current = openList.pop();
            closedSet.add(current);
            let parents = parentMap[current.url];
            if (parent.length == 0) {
                dependencies[current.url] = new Set([new Literal(current)]);
            } else {
                let parentSets: Set<Literal>[] = parents.map( parent => {
                    let node = parent[0];
                    let edge = parent[1];
                    let pSet = dependencies[node.url];
                    if (edge.negate) {
                        pSet = pSet.map((literal: Literal) => {
                            let lNew = new Literal(literal.node);
                            lNew.negated = !literal.negated;
                            return lNew;
                        });
                    }
                    return pSet;
                });
                if (current.type == 'AND') {
                    dependencies[current.url] = Sets.union(...parentSets);
                } else {
                    dependencies[current.url] = Sets.intersection(...parentSets);
                }
                let childs = ContradictoryCondidionValidator.getChilds(current, nodes, edges);
                childs.forEach( child => {
                    let childParents = parentMap[child.url];
                    if (!childParents) {
                        let childParents = ContradictoryCondidionValidator.getParents(child, nodes, edges);
                        parentMap[child.url] = childParents;
                    }
                    let isOpen = childParents.every( parent => {
                        return dependencies[parent[0].url] !== undefined;
                    });

                    if (isOpen) {
                        openList.push(child);
                    }
                });
            }
        }
        /*
            OpenList = RootNodes (in-degree = 0)
            dependencies = {}
            ClosedSet = {}
            While OpenList != empty
                current = openList.pop()
                parents, parentEdges = getParents(current)
                if parents = {}:
                    dependencies[current] = {literal(rootNode)}
                else:
                    if parents contained in dependencies:
                        dependencies[current] = Union of Parents (AND) / Intersection of Parents (OR) (if edges negated negate dependencies)
                        ClosedSet.add(current)
                        foreach child not in ClosedSet:
                            if childs parents contained in dependencies:
                                OpenList.add(child)

        */
        let nodeSet = new Set(nodes);
        let unfinishedNodes = Sets.difference(nodeSet, closedSet);
        if (unfinishedNodes.keys.length > 0) {
            // Cycle
        }
        return ValidationResult.VALID;
    }

    private static getParents(current: CEGNode, nodes: CEGNode[], edges: CEGConnection[]): [CEGNode, CEGConnection][] {
        // Retuns a list of Node, Edge pairs s.t.
        // each node is a parent of current and each edge connects the parent to current
        let out: [CEGNode, CEGConnection][] = [];
        let parentEdges = edges.filter( edge => edge.target.url === current.url);
        nodes.forEach( node => {
            let conEdge = parentEdges.find( e => e.target.url === node.url);
            if (conEdge !== undefined) {
                out.push([node, conEdge]);
            }
        });
        return out;
    }

    private static getChilds(current: CEGNode, nodes: CEGNode[], edges: CEGConnection[]): CEGNode[] {
        return edges.map( edge => {
            if (edge.target.url == current.url) {
                return nodes.find( node => node.url == edge.source.url);
            }
        });
    }
}

class Literal {
    public negated: boolean;
    public node: CEGNode;
    constructor(node: CEGNode) {
        this.negated = false;
        this.node = node;
    }

    public equals(obj: Literal): boolean {
        return obj.negated == this.negated && obj.node == this.node;
    }
}
