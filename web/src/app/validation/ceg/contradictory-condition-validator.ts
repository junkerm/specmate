import { Validator } from '../validator-decorator';
import { CEGModel } from '../../model/CEGModel';
import { ElementValidatorBase } from '../element-validator-base';
import { ValidationResult } from '../validation-result';
import { IContainer } from '../../model/IContainer';
import { CEGConnection } from '../../model/CEGConnection';
import { CEGNode } from '../../model/CEGNode';
import { Type } from '../../util/type';
import { Arrays } from '../../util/arrays';
import { Config } from '../../config/config';

@Validator(CEGModel)
export class ContradictoryCondidionValidator extends ElementValidatorBase<CEGModel> {
    public validate(element: CEGModel, contents: IContainer[]): ValidationResult {
        let nodes = contents.filter( elem => Type.is(elem, CEGNode)).map( n => n as CEGNode);
        let edges = contents.filter( elem => Type.is(elem, CEGConnection)).map( e => e as CEGConnection);
        let isAndNode: {[url: string]: boolean} = {};

        let openList: ToplogogicalNode[]   = [];
        let tNodes: {[url: string]: ToplogogicalNode} = {};
        for (const node of nodes) {
            let tNode = new ToplogogicalNode(node);
            tNodes[node.url] = tNode;
            isAndNode[node.url] = node.type === 'AND';
            if (node.incomingConnections === undefined  || node.incomingConnections.length == 0) {
                openList.push(tNode);
            }
        }

        for (const edge of edges) {
            let from = edge.source.url;
            let to = edge.target.url;
            tNodes[from].edges.push(edge);
            tNodes[to].parentCount++;
        }

        let contradictions: IContainer[][] = [];
        while (openList.length > 0) {
            let currentTNode = openList.pop();
            for (const edge of currentTNode.edges) {
                let to = edge.target.url;
                let toNode = tNodes[to];

                if (isAndNode[to]) {
                    // Merge Edgesets
                    toNode.edgeUnion(currentTNode, edge);
                } else {
                    // Intersect Edge Sets
                    toNode.edgeIntersection(currentTNode, edge);
                }

                toNode.parentCount--;
                if (toNode.parentCount == 0) {
                    for (const key in toNode.posEdgeSets) {
                        if (toNode.posEdgeSets.hasOwnProperty(key) && toNode.negEdgeSets.hasOwnProperty(key)) {
                            // We have a contradiction
                            let contradiction: IContainer[] = [toNode.node, tNodes[key].node];
                            contradiction.concat(toNode.posEdgeSets[key]);
                            contradiction.concat(toNode.negEdgeSets[key]);
                            contradictions.push(contradiction);
                        }
                    }
                    openList.push(toNode);
                }
            }
        }

        if (contradictions.length > 0) {
            let elements = Arrays.flatten(contradictions);
            return new ValidationResult(Config.ERROR_CONTRADICTORY_CAUSES, false, elements);
        }

        return ValidationResult.VALID;
    }
}

class ToplogogicalNode {
    public node: CEGNode;
    public edges: CEGConnection[];
    public parentCount: number;
    public posEdgeSets: {[parentURL: string]: CEGConnection[]};
    public negEdgeSets: {[parentURL: string]: CEGConnection[]};
    private _setWasFilled: boolean;

    constructor(node: CEGNode) {
        this.node = node;
        this.edges = [];
        this.parentCount = 0;
        this.posEdgeSets = {};
        this.negEdgeSets = {};
        this._setWasFilled = false;
    }

    private static cloneEdgeSet(edgeSet: {[parentURL: string]: CEGConnection[]}): {[parentURL: string]: CEGConnection[]} {
        let out = {};
        for (const key in edgeSet) {
            if ( edgeSet.hasOwnProperty(key)) {
                out[key] = edgeSet[key].slice();
            }
        }
        return out;
    }

    public edgeUnion(parent: ToplogogicalNode, edge: CEGConnection) {
        let parentPositiveEdgeSet = edge.negate ? parent.negEdgeSets : parent.posEdgeSets;
        let parentNegativeEdgeSet = edge.negate ? parent.posEdgeSets : parent.negEdgeSets;

        if (edge.negate) {
            if (!this.negEdgeSets.hasOwnProperty(parent.node.url)) {
                this.negEdgeSets[parent.node.url] = [];
            }
            this.negEdgeSets[parent.node.url].push(edge);
        } else {
            if (!this.posEdgeSets.hasOwnProperty(parent.node.url)) {
                this.posEdgeSets[parent.node.url] = [];
            }
            this.posEdgeSets[parent.node.url].push(edge);
        }

        for (const key in parentPositiveEdgeSet) {
            if (parentPositiveEdgeSet.hasOwnProperty(key)) {
                if ( (!this.posEdgeSets.hasOwnProperty(key)) || this.posEdgeSets[key].length > parentPositiveEdgeSet[key].length) {
                    this.posEdgeSets[key] = parentPositiveEdgeSet[key].slice();
                }
            }
        }

        for (const key in parentNegativeEdgeSet) {
            if (parentNegativeEdgeSet.hasOwnProperty(key)) {
                if ( (!this.negEdgeSets.hasOwnProperty(key)) || this.negEdgeSets[key].length > parentNegativeEdgeSet[key].length) {
                    this.negEdgeSets[key] = parentNegativeEdgeSet[key].slice();
                }
            }
        }
    }

    public edgeIntersection(parent: ToplogogicalNode, edge: CEGConnection) {
        let parentPositiveEdgeSet = edge.negate ? parent.negEdgeSets : parent.posEdgeSets;
        let parentNegativeEdgeSet = edge.negate ? parent.posEdgeSets : parent.negEdgeSets;

        if (!this._setWasFilled) {
            this.posEdgeSets = ToplogogicalNode.cloneEdgeSet(parentPositiveEdgeSet);
            this.negEdgeSets = ToplogogicalNode.cloneEdgeSet(parentNegativeEdgeSet);

            if (edge.negate) {
                if (!this.negEdgeSets.hasOwnProperty(parent.node.url)) {
                    this.negEdgeSets[parent.node.url] = [];
                }
                this.negEdgeSets[parent.node.url].push(edge);
            } else {
                if (!this.posEdgeSets.hasOwnProperty(parent.node.url)) {
                    this.posEdgeSets[parent.node.url] = [];
                }
                this.posEdgeSets[parent.node.url].push(edge);
            }
            this._setWasFilled = true;
        } else {

            // Compute intersection of positive/negative keys
            for (const key in this.posEdgeSets) {
                if (this.posEdgeSets.hasOwnProperty(key)) {
                    if (!parentPositiveEdgeSet.hasOwnProperty(key)) {
                        delete this.posEdgeSets[key];
                    } else {
                        for (const parentEdge of parentPositiveEdgeSet[key]) {
                            if (this.posEdgeSets[key].indexOf(parentEdge) < 0) {
                                this.posEdgeSets[key].push(parentEdge);
                            }
                        }
                        this.posEdgeSets[key].push(edge);
                    }
                }
            }

            for (const key in this.negEdgeSets) {
                if (this.negEdgeSets.hasOwnProperty(key)) {
                    if (!parentNegativeEdgeSet.hasOwnProperty(key)) {
                        delete this.negEdgeSets[key];
                    } else {
                        for (const parentEdge of parentNegativeEdgeSet[key]) {
                            if (this.negEdgeSets[key].indexOf(parentEdge) < 0) {
                                this.negEdgeSets[key].push(parentEdge);
                            }
                        }
                        this.negEdgeSets[key].push(edge);
                    }
                }
            }
        }
    }

}
