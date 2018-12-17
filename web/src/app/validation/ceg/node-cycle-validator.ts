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
    public validate(element: CEGModel, contents: IContainer[]): ValidationResult {
        // Use DFS to find all back-edges (= cycles)

        let nodes: CEGNode[] = [];
        let nodeMap: {[nodeURL: string]: CEGNode} = {};
        let edgeMap: {[nodeURL: string]: CEGConnection[]} = {};
        for (const elem of contents) {
            if (Type.is(elem, CEGConnection)) {
                let edge = elem as CEGConnection;
                let fromURL = edge.source.url;
                if (!edgeMap.hasOwnProperty(fromURL)) {
                    edgeMap[fromURL] = [];
                }
                edgeMap[fromURL].push(edge);
            }

            if (Type.is(elem, CEGNode)) {
                let node = elem as CEGNode;
                nodeMap[node.url] = node;
                nodes.push(node);
            }
        }

        let closedSet = new Set<CEGNode>();
        // Iterate over all nodes starting with the lowest in degree (usually nodes without any parents)
        let openList: CEGNode[] = nodes.sort( (a, b) => {
            if (a.incomingConnections === undefined) {
                return -1;
            }
            if (b.incomingConnections === undefined) {
                return 1;
            }
            return a.incomingConnections.length - b.incomingConnections.length;
        });

        let cycles: CEGConnection[][] = [];
        for (const startNode of openList) {
            if (closedSet.has(startNode)) {
                // Node has already been seen
                continue;
            }
            let traceStack: [CEGNode, number][] = [[startNode, 0]];
            let addToClosedSet = new Set<CEGNode>();
            while (traceStack.length > 0) {
                let currentNode = traceStack[traceStack.length - 1][0];
                let edgeIndex = traceStack[traceStack.length - 1][1];

                if (edgeMap.hasOwnProperty(currentNode.url) && edgeIndex < edgeMap[currentNode.url].length) {
                    // Follow Edge
                    let edge = edgeMap[currentNode.url][edgeIndex];
                    let toNode = nodeMap[edge.target.url];
                    if (closedSet.has(toNode)) {
                        // We don't enter a part of the graph we have already seen
                        traceStack[traceStack.length - 1][1] += 1;
                        continue;
                    }

                    let toNodeIndex = traceStack.findIndex( val => val[0] == toNode);
                    if (toNodeIndex >= 0) {
                        // Back Edge: We are at a node we have seen in our trace
                        // -> We have a cycle
                        let cycleTrace = traceStack.slice(toNodeIndex);
                        let cycle = cycleTrace.map( val => edgeMap[val[0].url][val[1]]);
                        cycles.push(cycle);
                        // Don't enter the cycle
                        traceStack[traceStack.length - 1][1] += 1;
                    } else {
                        // This is an unkown node -> Explore it.
                        traceStack.push([toNode, 0]);
                    }
                } else {
                    let finishedNode = traceStack.pop()[0];
                    addToClosedSet.add(finishedNode);
                    if (traceStack.length > 0) {
                        // Goto next edge
                        traceStack[traceStack.length - 1][1] += 1;
                    }
                }
            }
            closedSet = Sets.union(closedSet, addToClosedSet);
        }
        if (cycles.length > 0) {
            return new ValidationResult(Config.ERROR_CIRCULAR_CAUSES, false, Arrays.flatten(cycles));
        }
        return ValidationResult.VALID;
    }
}
