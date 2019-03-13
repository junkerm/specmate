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
    /**
     * Detects CEG Constructions that contain a necessary contradiction.
     * Such a contradiction arisses, when a cause implys an effect and its negation.
     * To find such contradictions we follow all edges starting from the root causes in topological order.
     * We maintain for each node a list of positive and negative causes.
     * If a node containes a parent both in the list of positive and negative causes we have a contradiction.
     * Parent --> Node && !Node
     */
    private nodes: CEGNode[];
    private edges: CEGConnection[];
    private isAndNode: {[url: string]: boolean};
    private openList: TopologicalNode[];
    private topoNodes: {[url: string]: TopologicalNode};

    public validate(element: CEGModel, contents: IContainer[]): ValidationResult {
        this.initValidation(contents);

        let contradictions: Contradiction[] = [];
        while (this.openList.length > 0) {
            const currentNode = this.openList.pop();
            const searchResult = this.exploreTopologicalNode(currentNode);
            this.openList = this.openList.concat(searchResult[0]);
            contradictions = contradictions.concat(searchResult[1]);
        }

        if (contradictions.length > 0) {
            // We have found contradictions
            // Map them to the list of involved elements (Cause, Effect, Edges from Cause to Effect)
            const elements = Arrays.flatten(contradictions.map(contradiction => contradiction.toArray()));
            return new ValidationResult(Config.ERROR_CONTRADICTORY_CAUSES, false, elements);
        }

        return ValidationResult.VALID;
    }

    private initValidation(contents: IContainer[]): void {
        // Init all datastructures for the validation
        // Node & Edge List
        this.nodes = contents.filter( elem => Type.is(elem, CEGNode)).map( n => n as CEGNode);
        this.edges = contents.filter( elem => Type.is(elem, CEGConnection)).map( e => e as CEGConnection);
        // NodeURL -> boolean (Type of CEGNode AND/OR)
        this.isAndNode = {};

        // List of Nodes that have to be explored
        this.openList = [];
        // Map CEGNode -> TopologicalNode
        this.topoNodes = {};
        for (const node of this.nodes) {
            const tNode = new TopologicalNode(node);
            this.topoNodes[node.url] = tNode;
            this.isAndNode[node.url] = (node.type === 'AND');
            if (node.incomingConnections === undefined  || node.incomingConnections.length == 0) {
                this.openList.push(tNode);
            }
        }

        for (const edge of this.edges) {
            const from = edge.source.url;
            const to = edge.target.url;
            this.topoNodes[from].edges.push(edge);
            this.topoNodes[to].parentCount++;
        }
    }

    private exploreTopologicalNode(tNode: TopologicalNode): [TopologicalNode[], Contradiction[]] {
        // Explores a Node and returns a list of unexplored Nodes and all found contradictions
        const newUnexploredNodes: TopologicalNode[] = [];
        const contradictions: Contradiction[] = [];
        for (const edge of tNode.edges) {
            const to = edge.target.url;
            const toNode = this.topoNodes[to];

            if (this.isAndNode[to]) {
                /**
                 * We have an AND Node:
                 * All parent causes are also causes of this node.
                 * --> Create the union of all parent causes.
                 */
                toNode.edgeUnion(tNode, edge);
            } else {
                /**
                 * We have an OR Node:
                 * Only causes, that are necessary causes of ALL parents are also causes of this node.
                 * --> Create the intersection of all parent causes.
                 */
                toNode.edgeIntersection(tNode, edge);
            }
            // We have dealt with this node.
            // Reduce the parent count (Topological Sort)
            toNode.parentCount--;
            if (toNode.parentCount == 0) {
                // We have explored all parents of toNode. It can now be explored.
                // Test for contraditcions and add mark it for the openList.
                for (const key in toNode.posEdgeSets) {
                    if (toNode.posEdgeSets.hasOwnProperty(key) && toNode.negEdgeSets.hasOwnProperty(key)) {
                        // We have a contradiction
                        // Cause -PosPath-> Effect
                        // Cause -NegPath-> !Effect
                        const cause = this.topoNodes[key].node;
                        const effect = toNode.node;
                        const posPath = toNode.posEdgeSets[key];
                        const negPath = toNode.negEdgeSets[key];
                        const contradiction = new Contradiction(cause, effect, posPath, negPath);
                        contradictions.push(contradiction);
                    }
                }
                newUnexploredNodes.push(toNode);
            }
        }
        return [newUnexploredNodes, contradictions];
    }
}

class TopologicalNode {
    /**
     * Helper Datastructure:
     * A CEG Node with all its outgoing edges.
     * A counter for the number of unexplored parents (for the topological sorting)
     * and the nodes' positive and and negative cause sets.
     */
    public node: CEGNode;
    public edges: CEGConnection[];
    public parentCount: number;
    public posEdgeSets: {[parentURL: string]: CEGConnection[]};
    public negEdgeSets: {[parentURL: string]: CEGConnection[]};
    private setWasFilled: boolean;

    constructor(node: CEGNode) {
        this.node = node;
        this.edges = [];
        this.parentCount = 0;
        this.posEdgeSets = {};
        this.negEdgeSets = {};
        this.setWasFilled = false;
    }

    private static cloneEdgeSet(edgeSet: {[parentURL: string]: CEGConnection[]}): {[parentURL: string]: CEGConnection[]} {
        /**
         * Helper function. Creates copy of an edge set. Also copys the arrays within the set.
         */
        const out = {};
        for (const key in edgeSet) {
            if ( edgeSet.hasOwnProperty(key)) {
                out[key] = edgeSet[key].slice();
            }
        }
        return out;
    }

    public edgeUnion(parent: TopologicalNode, edge: CEGConnection) {
        // This node is an AND Node so all causes to any parents are also necessaryly our own causes
        // There is an edge from the parent to this node:
        // If the edge is not negated
            // Add all Elements of the positiveEdgeSet of its parent to its own positiveEdgeSet
            // Add all Elements of the negativeEdgeSet of its parent to its own negativeEdgeSet
            // and add the parent to the positiveEdgeSet
        // Otherwise we swap the positive and negative sets of the parent before adding them to our own
        // and add the parent to the negative EdgeSet

        // Set Swap sets on negation
        const parentPositiveEdgeSet = edge.negate ? parent.negEdgeSets : parent.posEdgeSets;
        const parentNegativeEdgeSet = edge.negate ? parent.posEdgeSets : parent.negEdgeSets;

        // Add Parent
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

        // Add Sets
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

    public edgeIntersection(parent: TopologicalNode, edge: CEGConnection) {
        // The node is an OR Node so all causes in the intersection of parent causes are also necessessarily our own causes
        // We only keep elements in our edgeSets if the parent has them as well
        // If the edge from the parent to us is negated we swap their positive and negative EdgeSets
        const parentPositiveEdgeSet = edge.negate ? parent.negEdgeSets : parent.posEdgeSets;
        const parentNegativeEdgeSet = edge.negate ? parent.posEdgeSets : parent.negEdgeSets;

        if (!this.setWasFilled) {
            // Our Set was not yet initialized so we initialize it with the values of the first parent
            this.posEdgeSets = TopologicalNode.cloneEdgeSet(parentPositiveEdgeSet);
            this.negEdgeSets = TopologicalNode.cloneEdgeSet(parentNegativeEdgeSet);

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
            this.setWasFilled = true;
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

class Contradiction {
    /**
     * Helper Datastructure.
     * Models the contradiction
     * Cause -positivePath -> Effect
     * Cause -negativePath -> !Effect
     */
    constructor(public cause: CEGNode, public effect: CEGNode,
                public positivePath: CEGConnection[], public negativePath: CEGConnection[]) {}

    public toArray(): IContainer[] {
        const out: IContainer[] = [this.cause, this.effect];
        out.concat(this.positivePath);
        out.concat(this.negativePath);
        return out;
    }
}
