import { Validator } from '../validator-decorator';
import { CEGModel } from '../../model/CEGModel';
import { ElementValidatorBase } from '../element-validator-base';
import { ValidationResult } from '../validation-result';
import { IContainer } from '../../model/IContainer';
import { CEGConnection } from '../../model/CEGConnection';
import { CEGNode } from '../../model/CEGNode';
import { Type } from '../../util/type';
import { Sets } from '../../util/sets';


@Validator(CEGModel)
export class ContradictoryCondidionValidator extends ElementValidatorBase<CEGModel> {
    public validate(element: CEGModel, contents: IContainer[]): ValidationResult {
        let nodes = contents.filter( elem => Type.is(elem, CEGNode)).map( n => n as CEGNode);
        let edges = contents.filter( elem => Type.is(elem, CEGConnection)).map( e => e as CEGConnection);

        let isAndNode: {[url: string]: boolean} = {};
        let resolvedMap: {[url: string]: Literal[]} = {};
        let unresolvedMap: {[url: string]: NonLiteral[]} = {};
        let isResolved: {[url: string]: boolean} = {};
        for (const node of nodes) {
            isAndNode[node.url] = node.type === 'AND';
            if (node.incomingConnections.length == 0) {
                resolvedMap[node.url] = [new Literal(node)];
                isResolved[node.url] = true;
            } else {
                unresolvedMap[node.url] = [];
                isResolved[node.url] = false;
            }
        }

        for (const edge of edges) {
            let from = edge.source.url;
            unresolvedMap[from].push(new NonLiteral(edge));
        }

        for (const nodeURL in unresolvedMap) {
            if (unresolvedMap.hasOwnProperty(nodeURL)) {
                let workstack = [nodeURL];

                while (workstack.length > 0) {
                    let currentNodeURL = workstack[workstack.length - 1];
                    let currentNonLiteralList = unresolvedMap[nodeURL];
                    if (isResolved[currentNodeURL]) {
                        // Our node is resolved --> All parents of this node are resolved
                        workstack.pop();
                        // All parents are resolved.
                        let parentLiterals: Literal[][] = [];
                        for (const nonLiteral of currentNonLiteralList) {
                            let literals = resolvedMap[nonLiteral.url];
                            let currentParentLiterals = [];
                            for (const literal of literals) {
                                let clone = new Literal(literal.node);
                                clone.negated = literal.negated;
                                if (nonLiteral.negated) {
                                    clone.negated = !clone.negated;
                                }
                                for (const trace of literal.edgeTraces) {
                                    // TODO Traces can be flattend
                                    let newTrace = trace.slice();
                                    newTrace.push(nonLiteral.edge);
                                    clone.edgeTraces.push(newTrace);
                                }
                                currentParentLiterals.push(clone);
                            }
                            parentLiterals.push(currentParentLiterals);
                        }
                        // Compute the union (AND Case) or intersection (OR Case) of the parent literals
                        if (isAndNode[currentNodeURL]) {
                            resolvedMap[currentNodeURL] = ContradictoryCondidionValidator.literalUnion(parentLiterals);
                        } else {
                            resolvedMap[currentNodeURL] = ContradictoryCondidionValidator.literalIntersection(parentLiterals);
                        }
                        // TODO To Optimize one could find negated dublicates here
                        continue;
                    }
                    // The current node is not yet resolved.
                    // Resolve all parents of the node to resolve this node.
                    for (const nonLiteral of currentNonLiteralList) {
                        if (!isResolved[nonLiteral.url]) {
                            workstack.push(nonLiteral.url);
                        }
                    }
                    // We make the node as resolved to avoid infinite loops in cyclic CEGs.
                    isResolved[currentNodeURL] = true;
                }
            }
        }

        // Get all literals & check for negated dublicates
        // for each dublicate pair create Node + Trace List & Add to out
        let collisions: IContainer[] = [];
        for (const nodeURL in resolvedMap) {
            if (resolvedMap.hasOwnProperty(nodeURL)) {
                const literals = resolvedMap[nodeURL];
                for (const literal of literals) {
                    if (literal.negated) {
                        continue;
                    }
                    let negatedLiteral = literals.filter( lit => lit.node === literal.node && lit.negated != literal.negated);
                    if (negatedLiteral !== undefined) {

                    }
                }
            }
        }

        return ValidationResult.VALID;
    }

    private static literalUnion(literals: Literal[][]): Literal[] {
        let out: Literal[] = [];
        for (const literalList of literals) {
            for (const literal of literalList) {
                // If literal in out extend Literal Trace
                // Else add Literal
                let existingLiteral = out.find(lit => lit === literal);
                if (existingLiteral === undefined) {
                    existingLiteral = new Literal(literal.node);
                    existingLiteral.negated = literal.negated;
                    out.push(existingLiteral);
                }
                existingLiteral.edgeTraces.concat(literal.edgeTraces);
            }
        }
        return out;
    }

    private static literalIntersection(literals: Literal[][]): Literal[] {
        let out: Literal[] = [];
        let startIntersection = true;
        for (const literalList of literals) {
            if (startIntersection) {
                // out = literalList.deepCopy;
                startIntersection = false;
                continue;
            }

            let newOut = [];
            for (const literal of out) {
                // If literal in out extend Literal Trace
                // Else remove Literal
                let existingLiteral = literalList.find(lit => lit === literal);
                if (existingLiteral !== undefined) {
                    literal.edgeTraces.concat(existingLiteral.edgeTraces);
                    newOut.push(literal);
                }
            }
            out = newOut;
        }
        return out;
    }
}

class ToplogogicalNode {
    public node: CEGNode;
    public edges: CEGConnection[];
    public parentCount: number;
    public edgeSets: {[parentURL: string]: CEGConnection[]};
}

class Literal {
    public negated: boolean;
    public node: CEGNode;
    public involvedEdges: CEGConnection[];
    constructor(node: CEGNode) {
        this.negated = false;
        this.node = node;
        this.involvedEdges = [];
    }

    public equals(obj: Literal): boolean {
        return obj.negated == this.negated && obj.node == this.node;
    }
}

class NonLiteral {
    public edge: CEGConnection;
    constructor(edge: CEGConnection) {
        this.edge = edge;
    }

    public get negated(): boolean {
        return this.edge.negate;
    }

    public get url(): string {
        return this.edge.target.url;
    }

    public equals(obj: NonLiteral): boolean {
        return this.edge == obj.edge;
    }
}
