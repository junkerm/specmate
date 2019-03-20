import { Validator } from '../validator-decorator';
import { CEGModel } from '../../model/CEGModel';
import { ElementValidatorBase } from '../element-validator-base';
import { ValidationResult } from '../validation-result';
import { IContainer } from '../../model/IContainer';
import { CEGConnection } from '../../model/CEGConnection';
import { CEGNode } from '../../model/CEGNode';
import { Type } from '../../util/type';
import { Arrays } from '../../util/arrays';
import { ValidationMessage } from '../validation-message';

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
    private openList: TopologicalNode[];
    private topoNodes: {[url: string]: TopologicalNode};

    public validate(element: CEGModel, contents: IContainer[]): ValidationResult {
        this.initValidation(contents);

        let contradictions: Contradiction[] = [];
        while (this.openList.length > 0) {
            const currentNode = this.openList.pop();
            const searchResult = this.exploreTopologicalNode(currentNode);
            contradictions = contradictions.concat(searchResult);
        }

        if (contradictions.length > 0) {
            // We have found contradictions
            // Map them to the list of involved elements (Cause, Effect, Edges from Cause to Effect)
            const elements = Arrays.flatten(contradictions.map(contradiction => contradiction.toArray()));
            return new ValidationResult(ValidationMessage.ERROR_CONTRADICTORY_CAUSES, false, elements);
        }

        return ValidationResult.VALID;
    }

    private initValidation(contents: IContainer[]): void {
        // Init all datastructures for the validation
        // Node & Edge List
        this.nodes = contents.filter( elem => Type.is(elem, CEGNode)).map( n => n as CEGNode);
        this.edges = contents.filter( elem => Type.is(elem, CEGConnection)).map( e => e as CEGConnection);
        // List of Nodes that have to be explored
        this.openList = [];
        // Map CEGNode -> TopologicalNode
        this.topoNodes = {};
        for (const node of this.nodes) {
            const tNode = new TopologicalNode(node);
            this.topoNodes[node.url] = tNode;
            if (node.incomingConnections === undefined  || node.incomingConnections.length == 0) {
                this.openList.push(tNode);
            }
        }

        for (const edge of this.edges) {
            const from = edge.source.url;
            const to = edge.target.url;
            this.topoNodes[from].edges.push(edge);
            this.topoNodes[to].openParents++;
        }
    }

    private exploreTopologicalNode(tNode: TopologicalNode): Contradiction[] {
        // Explores a Node and returns a list of all found contradictions
        let contradictions: Contradiction[] = [];
        for (const edge of tNode.edges) {
            const to = edge.target.url;
            const toNode = this.topoNodes[to];
            toNode.addParent(tNode, edge);
            if (toNode.allPerentsExplored()) {
                // We have explored all parents of toNode. It can now be explored.
                // Test for contraditcions and add mark it for the openList.
                contradictions = contradictions.concat(toNode.getContradictions(this.topoNodes));
                this.openList.push(toNode);
            }
        }
        return contradictions;
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
    public openParents: number;
    public posEdgeSets: {[parentURL: string]: CEGConnection[]};
    public negEdgeSets: {[parentURL: string]: CEGConnection[]};
    private isInitialized: boolean;

    constructor(node: CEGNode) {
        this.node = node;
        this.edges = [];
        this.openParents = 0;
        this.posEdgeSets = {};
        this.negEdgeSets = {};
        this.isInitialized = false;
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

    private isAndNode() {
        return this.node.type === 'AND';
    }

    public addParent(parent: TopologicalNode, edge: CEGConnection) {
        /**
         * Adds a parent to this node. Combines the edge sets of the parent with the sets of this node.
         */
        // Takes care of negated edges.
        const parentPositiveEdgeSet = edge.negate ? parent.negEdgeSets : parent.posEdgeSets;
        const parentNegativeEdgeSet = edge.negate ? parent.posEdgeSets : parent.negEdgeSets;

        if (!this.isInitialized) {
            // Initialize Sets
            this.posEdgeSets = TopologicalNode.cloneEdgeSet(parentPositiveEdgeSet);
            this.negEdgeSets = TopologicalNode.cloneEdgeSet(parentNegativeEdgeSet);
        }

        if (this.isAndNode() || !this.isInitialized) {
            // Add the direct parent to the edge set
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
        }
        this.openParents--;

        if (!this.isInitialized) {
            this.isInitialized = true;
            return;
        }

        if (this.isAndNode()) {
            this.edgeUnion(parentPositiveEdgeSet, parentNegativeEdgeSet);
        } else {
            this.edgeIntersection(parentPositiveEdgeSet, parentNegativeEdgeSet, edge);
        }
    }

    private edgeUnion(  parentPositiveEdgeSet: {[parentURL: string]: CEGConnection[]},
                        parentNegativeEdgeSet: {[parentURL: string]: CEGConnection[]}) {
        // This node is an AND Node so all causes to any parents are also necessaryly our own causes
        // Add all Elements of the positiveEdgeSet of its parent to its own positiveEdgeSet
        for (const key in parentPositiveEdgeSet) {
            if (parentPositiveEdgeSet.hasOwnProperty(key)) {
                if ( (!this.posEdgeSets.hasOwnProperty(key)) || this.posEdgeSets[key].length > parentPositiveEdgeSet[key].length) {
                    this.posEdgeSets[key] = parentPositiveEdgeSet[key].slice();
                }
            }
        }
        // Add all Elements of the negativeEdgeSet of its parent to its own negativeEdgeSet
        for (const key in parentNegativeEdgeSet) {
            if (parentNegativeEdgeSet.hasOwnProperty(key)) {
                if ( (!this.negEdgeSets.hasOwnProperty(key)) || this.negEdgeSets[key].length > parentNegativeEdgeSet[key].length) {
                    this.negEdgeSets[key] = parentNegativeEdgeSet[key].slice();
                }
            }
        }
    }

    private edgeIntersection(   parentPositiveEdgeSet: {[parentURL: string]: CEGConnection[]},
                                parentNegativeEdgeSet: {[parentURL: string]: CEGConnection[]}, edge: CEGConnection) {
        // The node is an OR Node so all causes in the intersection of parent causes are also necessessarily our own causes
        // We only keep elements in our edgeSets if the parent has them as well
        // If the edge from the parent to us is negated we swap their positive and negative EdgeSets

        // Compute intersection of positive/negative key sets
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

    public allPerentsExplored() {
        return this.openParents == 0;
    }

    public getContradictions(topoNodes: {[key: string]: TopologicalNode}) {
        let out = [];
        for (const key in this.posEdgeSets) {
            if (this.posEdgeSets.hasOwnProperty(key) && this.negEdgeSets.hasOwnProperty(key)) {
                // We have a contradiction
                // Cause -PosPath-> Effect
                // Cause -NegPath-> !Effect
                const cause = topoNodes[key].node;
                const effect = this.node;
                const posPath = this.posEdgeSets[key];
                const negPath = this.negEdgeSets[key];
                const contradiction = new Contradiction(cause, effect, posPath, negPath);
                out.push(contradiction);
            }
        }
        return out;
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
