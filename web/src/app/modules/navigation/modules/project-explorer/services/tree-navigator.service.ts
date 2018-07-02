import { Injectable } from '@angular/core';
import { ElementTree } from '../components/element-tree.component';
import { NavigatorService } from '../../navigator/services/navigator.service';

@Injectable()
export class TreeNavigatorService {
    constructor(private nav: NavigatorService) {}

    public set roots(rootlist: string[]) {
        if (rootlist && rootlist.length > 0) {
            this._roots = rootlist;
            this.startNavigation();
        }
    }

    private _roots: string[];
    private _trace: string[];
    private treeNodes: {[url: string]: ElementTree} = {};

    public clean(): void {
        this._roots = [];
        this.endNavigation();
        this.treeNodes = {};
    }

    public startNavigation(): void {
        this._trace = [this._roots[0]];
    }

    public endNavigation(): void {
        this._trace = [];
    }

    public announceTreeNode(node: ElementTree): void {
        this.treeNodes[node.baseUrl] = node;
    }

    private get selected(): string {
        return this._trace[this._trace.length - 1];
    }

    public setSelection(url: string): void {
        let comp = url.split('/');
        for (let i = 0; i < comp.length; i++) {
            let build = '';
            for (let j = 0; j <= i; j++) {
                if (j != 0) {
                    build += '/';
                }
                build += comp[j];
            }
            this._trace.push(build);
        }
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

        let endNode = this.treeNodes[this.selected];
        if (endNode && endNode.expanded && !endNode.isMustOpen) {
            endNode.toggle();
        } else if (this._trace.length > 1) {
            this._trace.pop();
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
            } else if (endNode.contents.length > 0) {
                this._trace.push(endNode.contents[0].url);
            }
        }
    }

    public navigateUp(): void {
        let cIndex = -1;
        let pList = [];
        if (this._trace.length == 1) {
            // We navigate through the root nodes
            cIndex = this._roots.indexOf(this.selected);
            pList = this._roots;
        } else {
            // We are bellow the root nodes
            let parentUrl = this._trace[this._trace.length - 2];
            let parent = this.treeNodes[parentUrl];
            pList = parent.contents.map(x => x.url);
            cIndex = pList.indexOf(this.selected);
        }

        if (cIndex > 0) {
            this._trace[this._trace.length - 1] = pList[cIndex - 1];
        }
    }

    public navigateDown(): void {
        let cIndex = -1;
        let pList = [];
        if (this._trace.length == 1) {
            // We navigate through the root nodes
            cIndex = this._roots.indexOf(this.selected);
            pList = this._roots;
        } else {
            // We are bellow the root nodes
            let parentUrl = this._trace[this._trace.length - 2];
            let parent = this.treeNodes[parentUrl];
            pList = parent.contents.map(x => x.url);
            cIndex = pList.indexOf(this.selected);
        }

        if (cIndex < pList.length - 1 && cIndex >= 0) {
            this._trace[this._trace.length - 1] = pList[cIndex + 1];
        }
    }

    public selectElement(): void {
        let node = this.treeNodes[this.selected];
        if (! node.isFolderNode) {
            this.nav.navigate(node.element);
        } else {
            this.navigateRight();
        }
    }
}
