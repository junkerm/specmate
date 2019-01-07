import { Validator } from '../validator-decorator';
import { CEGModel } from '../../model/CEGModel';
import { ElementValidatorBase } from '../element-validator-base';
import { IContainer } from '../../model/IContainer';
import { ValidationResult } from '../validation-result';
import { CEGNode } from '../../model/CEGNode';
import { Type } from '../../util/type';
import { CEGConnection } from '../../model/CEGConnection';
import { Config } from '../../config/config';
import { Arrays } from '../../util/arrays';
import { Sets } from '../../util/sets';

@Validator(CEGModel)
export class NodeCycleValidator extends ElementValidatorBase<CEGModel> {
    // List of all CEGNodes of the model
    private nodes: CEGNode[];
    // Maps the URL of a node to the node object.
    private nodeMap: {[nodeURL: string]: CEGNode};
    // Maps the URL of an Node to all its outgoing connections.
    private outgoingConnections: {[nodeURL: string]: CEGConnection[]};
    // Set of already explored Nodes
    private closedSet: Set<CEGNode>;

    public validate(element: CEGModel, contents: IContainer[]): ValidationResult {
        /**
         * Uses DFS to find all back-edges (=Cycles)
         */
        this.initValidation(contents);
        let cycles: CEGConnection[][] = [];
        for (const startNode of this.nodes) {
            if (this.closedSet.has(startNode)) {
                // Node has already been seen
                continue;
            }
            let searchResult = this.depthFirstSearch(startNode);
            this.closedSet = Sets.union(this.closedSet, searchResult[0]);
            cycles = cycles.concat(searchResult[1]);
        }
        if (cycles.length > 0) {
            return new ValidationResult(Config.ERROR_CIRCULAR_CAUSES, false, Arrays.flatten(cycles));
        }
        return ValidationResult.VALID;
    }

    private initValidation(contents: IContainer[]): void {
        this.nodes = [];
        this.nodeMap = {};
        this.outgoingConnections = {};
        for (const elem of contents) {
            if (Type.is(elem, CEGConnection)) {
                let edge = elem as CEGConnection;
                let fromURL = edge.source.url;
                if (!this.outgoingConnections.hasOwnProperty(fromURL)) {
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
        /**
         * We want to iterate of the nodes starting with the lowest in-degree (ususally nodes without any parents).
         */
        this.nodes = this.nodes.sort( (a, b) => {
            if (a.incomingConnections === undefined) {
                return -1;
            }
            if (b.incomingConnections === undefined) {
                return 1;
            }
            return a.incomingConnections.length - b.incomingConnections.length;
        });

        this.closedSet = new Set();
    }

    private depthFirstSearch(startNode: CEGNode): [Set<CEGNode>, CEGConnection[][]] {
        /**
         * Runs a DFS starting at the given node.
         * Returns a set of all newly explored nodes and a list of newly found edge cycles.
         */

        /**
         * Current Search trace.
         * Stack containing the current search path and the index of the taken edges of each node.
         */
        let traceStack: [CEGNode, number][] = [[startNode, 0]];
        // Newly explored Nodes
        let addToClosedSet = new Set<CEGNode>();
        let cycles = [];
        while (traceStack.length > 0) {
            let currentNode = traceStack[traceStack.length - 1][0];
            let edgeIndex = traceStack[traceStack.length - 1][1];
            // Test if we have any unexplored edges.
            if (this.outgoingConnections.hasOwnProperty(currentNode.url) &&
                    edgeIndex < this.outgoingConnections[currentNode.url].length) {
                // Follow Edge
                let edge = this.outgoingConnections[currentNode.url][edgeIndex];
                let toNode = this.nodeMap[edge.target.url];
                if (this.closedSet.has(toNode)) {
                    // We don't enter a part of the graph we have already seen
                    traceStack[traceStack.length - 1][1] += 1;
                    continue;
                }

                // Check if we have already seen this node
                let toNodeIndex = traceStack.findIndex( val => val[0] == toNode);
                if (toNodeIndex >= 0) {
                    // Back Edge: We are at a node we have seen in our trace
                    // -> We have a cycle
                    let cycleTrace = traceStack.slice(toNodeIndex);
                    let cycle = cycleTrace.map( val => this.outgoingConnections[val[0].url][val[1]]);
                    cycles.push(cycle);
                    // Don't enter the cycle
                    traceStack[traceStack.length - 1][1] += 1;
                } else {
                    // This is an unkown node -> Explore it.
                    traceStack.push([toNode, 0]);
                }
            } else {
                // We have explored all children of this node.
                // Add it to the closed set
                let finishedNode = traceStack.pop()[0];
                addToClosedSet.add(finishedNode);
                if (traceStack.length > 0) {
                    // Goto next edge
                    traceStack[traceStack.length - 1][1] += 1;
                }
            }
        }
        return [addToClosedSet, cycles];
    }
}
