import { Validator } from '../validator-decorator';
import { CEGModel } from '../../model/CEGModel';
import { ElementValidatorBase } from '../element-validator-base';
import { IContainer } from '../../model/IContainer';
import { ValidationResult } from '../validation-result';
import { CEGNode } from '../../model/CEGNode';import { Type } from '../../util/type';
;

@Validator(CEGModel)
export class ContradictoryCondidionValidator extends ElementValidatorBase<CEGModel> {
    public validate(element: CEGModel, contents: IContainer[]): ValidationResult {
        // Use Johnsons Circuit finding Algorithm to get all cycles of nodes

        // We only consider Nodes that have both incomming and outgoing edges
        let nodes: CEGNode[] = contents.filter( e => Type.is(e, CEGNode))
                                        .map( n => n as CEGNode)
                                        .filter( n => n.incomingConnections !== undefined && n.incomingConnections.length > 0
                                                && n.outgoingConnections !== undefined && n.outgoingConnections.length > 0);
        // Get all SCCs

        this._workStack = [];
        for (const node of nodes) {
            let hasComponent = this.nextSCC();
            if (hasComponent) {
                this._startNode = this.getLeastNode();
                for (const nodeURL in this.Ak) {
                    if (this.Ak.hasOwnProperty(nodeURL)) {
                        const node = this.Ak[nodeURL];
                        this._isBlocked[nodeURL] = false;
                        this.B[nodeURL] = [];
                    }
                }
                let dummy = this.circuit(this._startNode);
                this._startNode = this.getNextNode(this._startNode);
            } else {
                break;
            }
        }

        return ValidationResult.VALID;
    }
    private _startNode: CEGNode;
    private _isBlocked: {[url: string]: boolean};
    private B: {[url: string]: CEGNode[]};
    private Ak: {[url: string]: CEGNode[]};
    private _workStack: CEGNode[];
    private _circuits: CEGNode[][];

    private circuit(node: CEGNode): boolean {
        let foundCircuit = false;
        this._workStack.push(node);
        this._isBlocked[node.url] = true;

        for (const w of this.Ak[node.url]) {
            if (w.url === this._startNode.url) {
                // Found Crcuit
                let circuit = this._workStack.slice();
                circuit.push(w);
                this._circuits.push(circuit);
                foundCircuit = true;
            } else if (!this._isBlocked[w.url]) {
                if (this.circuit(w)) {
                    foundCircuit = true;
                }
            }
        }

        if (foundCircuit) {
            this.unblock(node);
        } else {
            for (const w of this.Ak[node.url]) {
                if (this.B[node.url].indexOf(w) < 0) {
                    this.B[node.url].push(w);
                }
            }
        }
        this._workStack.pop();
        return foundCircuit;
    }

    private unblock(node: CEGNode) {
        this._isBlocked[node.url] = false;
        for (const w of this.B[node.url]) {
            if (this._isBlocked[w.url]) {
                this.unblock(w);
            }
        }
        this.B[node.url] = [];
    }
}
