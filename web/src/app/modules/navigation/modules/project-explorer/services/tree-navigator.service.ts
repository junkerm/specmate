import { Injectable } from '@angular/core';
import { ElementTree } from '../components/element-tree.component';

@Injectable()
export class TreeNavigatorService {
    public set roots(rootlist: string[]) {
        if (rootlist && rootlist.length > 0) {
            this._roots = rootlist;
            this._trace = [this._roots[0]];
        }
    }

    private _roots: string[];
    private _trace: string[];
    private treeNodes: {[url: string]: ElementTree} = {};

    public clean(): void {
        this._roots = [];
        this._trace = [];
        this.treeNodes = {};
    }

    public announceTreeNode(node: ElementTree): void {
        this.treeNodes[node.baseUrl] = node;
    }

    private get selected(): string {
        return this._trace[this._trace.length - 1];
    }

    public isSelected(nodeUrl: string): boolean {
        if (this._trace.length > 0) {
            return this.selected == nodeUrl;
        }
        return false;
    }

    public navigateLeft(): void {
        if (this._trace.length === 0) {
            return;
        }

        if (this._trace.length > 1) {
            this._trace.pop();
        }

        let endNode = this.treeNodes[this.selected];
        if (endNode) {
            if (endNode.expanded) {
                endNode.toggle();
            }
        }
    }

    public navigateRight(): void {
        if (this._trace.length <= 0) {
            return;
        }

        let endNode = this.treeNodes[this.selected];
        if (endNode) {
            if (!endNode.expanded) {
                endNode.toggle();
            } else {
                this._trace.push(endNode.contents[0].url);
            }
        }
    }

    public navigateUp(): void {
        let cIndex = -1;
        let pList = [];
        if (this._trace.length == 1) {
            // We navigate through the root nodes
            cIndex = this._roots.findIndex(x => x == this.selected);
            pList = this._roots;
        } else {
            // We are bellow the root nodes
            let parentUrl = this._trace[this._trace.length - 2];
            let parent = this.treeNodes[parentUrl];
            pList = parent.contents.map(x => x.url);
            cIndex = pList.findIndex(x => x == this.selected);
        }

        if (cIndex > 1) {
            this._trace[this._trace.length - 1] = pList[cIndex - 1];
        }
    }

    public navigateDown(): void {
        let cIndex = -1;
        let pList = [];
        if (this._trace.length == 1) {
            // We navigate through the root nodes
            cIndex = this._roots.findIndex(x => x == this.selected);
            pList = this._roots;
        } else {
            // We are bellow the root nodes
            let parentUrl = this._trace[this._trace.length - 2];
            let parent = this.treeNodes[parentUrl];
            pList = parent.contents.map(x => x.url);
            cIndex = pList.findIndex(x => x == this.selected);
        }

        if (cIndex < pList.length - 2 && cIndex >= 0) {
            this._trace[this._trace.length - 1] = pList[cIndex - 1];
        }
    }

    public selectElement(): void {

    }
}
