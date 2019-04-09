import { CEGConnection } from '../../model/CEGConnection';
import { CEGModel } from '../../model/CEGModel';
import { CEGNode } from '../../model/CEGNode';
import { IContainer } from '../../model/IContainer';
import { Arrays } from '../../util/arrays';
import { Type } from '../../util/type';
import { ElementValidatorBase } from '../element-validator-base';
import { ValidationMessage } from '../validation-message';
import { ValidationResult } from '../validation-result';
import { Validator } from '../validator-decorator';

type Circle = CEGConnection[];

@Validator(CEGModel)
export class NodeCycleValidator extends ElementValidatorBase<CEGModel> {
    // List of all CEGNodes of the model
    private nodes: CEGNode[];
    // Maps the URL of a node to the node object.
    private nodeMap: {[nodeURL: string]: CEGNode};
    // Maps the URL of an Node to all its outgoing connections.
    private outgoingConnections: CEGConnection[][];
    // Set of already explored Nodes
    private closedSet: Set<CEGNode>;

    public validate(element: CEGModel, contents: IContainer[]): ValidationResult {
        /**
         * Uses DFS to find all back-edges (=Circles)
         */
        this.initValidation(contents);
        let circles: Circle[] = [];
        for (const startNode of this.nodes) {
            if (this.closedSet.has(startNode)) {
                continue; // Node has already been seen
            }
            let newCircles = this.depthFirstSearch(startNode);
            circles = circles.concat(newCircles);
        }
        if (circles.length > 0) {
            return new ValidationResult(ValidationMessage.ERROR_CIRCULAR_CAUSES, false, Arrays.flatten(circles));
        }
        return ValidationResult.VALID;
    }

    private initValidation(contents: IContainer[]): void {
        /**
         * Setup the search structure for DFS with a list of nodes
         * A Map of outgoing edges for each node and a closed set.
         */
        this.nodes = [];
        this.nodeMap = {};
        this.outgoingConnections = [];
        for (const elem of contents) {
            if (Type.is(elem, CEGConnection)) {
                let edge = elem as CEGConnection;
                let fromURL = edge.source.url;
                if (this.outgoingConnections[fromURL] === undefined) {
                    this.outgoingConnections[fromURL] = [];
                }
                this.outgoingConnections[fromURL].push(edge);
            }

            if (Type.is(elem, CEGNode)) {
                let node = elem as CEGNode;
                this.nodeMap[node.url] = node;
                this.nodes.push(node);
            }
        }
        // We Sort by Node In-Degree so we start the search with ("root") nodes without any incomming edges.
        this.nodes = this.nodes.sort(this.nodeComparitor);
        this.closedSet = new Set();
    }

    private nodeComparitor(nodeA: CEGNode, nodeB: CEGNode) {
        //
        if (nodeA.incomingConnections === undefined) {
            return -1;
        }
        if (nodeB.incomingConnections === undefined) {
            return 1;
        }
        return nodeA.incomingConnections.length - nodeB.incomingConnections.length;
    }

    private depthFirstSearch(startNode: CEGNode): Circle[] {
        /**
         * Runs a DFS starting at the given node.
         * Returns a set of all newly found edge cycles.
         */
        let traceStack: TraceNode[] = [];
        traceStack.push(new TraceNode(startNode, this.outgoingConnections[startNode.url]));
        let circles = [];
        while (traceStack.length > 0) {
            let currentNode = traceStack[traceStack.length - 1];
            // Test if we have any unexplored edges.
            if (currentNode.hasNext()) {
                let edge = currentNode.getNextEdge();
                let toNode = this.nodeMap[edge.target.url];
                if (this.closedSet.has(toNode)) {
                    continue; // We don't enter a part of the graph we have already seen
                }
                // Check if we have already seen this node
                let toNodeIndex = traceStack.findIndex( val => val.node == toNode);
                if (toNodeIndex >= 0) {
                    // Backedge: We are at a node we have seen in our trace -> We have a cycle
                    let circleTrace = traceStack.slice(toNodeIndex);
                    let circle = circleTrace.map( node => node.getLastExploredEdge());
                    circles.push(circle);
                } else {
                    // This is an unkown node -> Explore it.
                    traceStack.push(new TraceNode(toNode, this.outgoingConnections[toNode.url]));
                }
            } else {
                // We have explored all children of this node.
                let finishedNode = traceStack.pop().node;
                this.closedSet.add(finishedNode);
            }
        }
        return circles;
    }
}

class TraceNode {
    public edgeCounter: number;
    public node: CEGNode;
    public edges: CEGConnection[];

    constructor(node: CEGNode, outgoingEdges: CEGConnection[]) {
        this.edgeCounter = 0;
        this.node = node;
        this.edges = outgoingEdges;
        if (outgoingEdges === undefined) {
            this.edges = [];
        }
    }

    public hasNext() {
        return this.edgeCounter < this.edges.length;
    }

    public getNextEdge() {
        return this.edges[this.edgeCounter++];
    }

    public getLastExploredEdge() {
        return this.edges[this.edgeCounter - 1];
    }
}
