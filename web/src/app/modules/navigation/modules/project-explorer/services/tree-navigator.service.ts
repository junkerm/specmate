import { Injectable, ElementRef } from '@angular/core';
import { ElementTree } from '../components/element-tree.component';
import { NavigatorService } from '../../navigator/services/navigator.service';
import { runInThisContext } from 'vm';

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
    private _treeNodes: {[url: string]: ElementTree} = {};
    private _treeElements: {[url: string]: ElementRef} = {};

    public clean(): void {
        this._roots = [];
        this.endNavigation();
        this._treeNodes = {};
        this._treeElements = {};
    }

    public startNavigation(): void {
        this._trace = [this._roots[0]];
    }

    public endNavigation(): void {
        this._trace = [];
    }

    public announceTreeNode(node: ElementTree): void {
        this._treeNodes[node.baseUrl] = node;
    }

    public announceTreeElement(node: ElementTree, element: ElementRef) {
        this._treeElements[node.baseUrl] = element;
    }

    private get selected(): string {
        return this._trace[this._trace.length - 1];
    }

    public scrollToSelection(): void {
        this._treeElements[this.selected].nativeElement.scrollIntoView();
    }

    public setSelection(url: string): void {
        this._trace = [];
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

    private getNext(onlyMoveUp = false): string {
        let currentNode = this._treeNodes[this.selected];
        // Move up the tree
        if (currentNode.expanded
        && currentNode.contents.length > 0
        && this._treeNodes[currentNode.contents[0].url].showElement) {
            let childCandidateURL = currentNode.contents[0].url;
            let childCandidate = this._treeNodes[childCandidateURL];
            if (childCandidate.showElement) {
                return childCandidateURL;
            }
        }

        if (onlyMoveUp) {
            return '';
        }

        // Move on the same level / Down the tree
        for (let level = this._trace.length - 1; level >= 0; level--) {
            let cIndex = -1;
            let pList = [];

            let levelElement = this._trace[level];

            if (level == 0) {
                // Root
                cIndex = this._roots.indexOf(levelElement);
                pList = this._roots;
            } else {
                // Above Root
                let parentUrl = this._trace[level - 1];
                let parent = this._treeNodes[parentUrl];
                pList = parent.contents.map(x => x.url);
                cIndex = pList.indexOf(levelElement);
            }
            if (cIndex < pList.length - 1 && cIndex >= 0) {
                return pList[cIndex + 1];
            }
        }
        // No next element
        return '';
    }

    private getPrev(): string {
        // Move on the same level / Down the tree
        for (let level = this._trace.length; level >= 0; level--) {
            let cIndex = -1;
            let pList = [];

            let levelElement = this._trace[level];

            if (level == 0) {
                // Root
                cIndex = this._roots.indexOf(levelElement);
                pList = this._roots;
            } else {
                // Above Root
                let parentUrl = this._trace[level - 1];
                let parent = this._treeNodes[parentUrl];
                pList = parent.contents.map(x => x.url);
                cIndex = pList.indexOf(levelElement);
            }
            if (cIndex > 0) {
                let currentUrl = pList[cIndex - 1];
                let currentNode = this._treeNodes[currentUrl];
                // We found the previous sibling node
                // Go down the tree until we hit a leaf node
                while (currentNode.expanded
                    && currentNode.contents.length > 0
                    && this._treeNodes[currentNode.contents[0].url].showElement) {
                        let childCount = currentNode.contents.length;
                        currentUrl = currentNode.contents[childCount - 1].url;
                        currentNode = this._treeNodes[currentUrl];
                }
                return currentUrl;
            } else if (cIndex == 0 && level > 0) {
                return this._trace[level - 1];
            }
        }
        // No prev element
        return '';
    }

    public navigateLeft(): void {
        if (this._trace.length === 0) {
            return;
        }

        let endNode = this._treeNodes[this.selected];
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

        let endNode = this._treeNodes[this.selected];
        if (endNode) {
            if (!endNode.expanded) {
                endNode.toggle();
            } else {
                let next = this.getNext(true);
                if (next) {
                    this.setSelection(next);
                }
            }
        }
    }

    public navigateUp(): void {
        let prev = this.getPrev();
        if (prev) {
            this.setSelection(prev);
        }
    }

    public navigateDown(stayInLayer = false): void {
        let next = this.getNext();
        if (next) {
            this.setSelection(next);
        }
    }

    public selectElement(): void {
        let node = this._treeNodes[this.selected];
        if (! node.isFolderNode) {
            this.nav.navigate(node.element);
        } else {
            this.navigateRight();
        }
    }
}
