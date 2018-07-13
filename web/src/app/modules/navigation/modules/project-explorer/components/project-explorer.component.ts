import { Component, OnInit, HostListener, ViewChild, ElementRef } from '@angular/core';
import { Subject } from 'rxjs/Subject';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/distinctUntilChanged';
import { IContainer } from '../../../../../model/IContainer';
import { IContentElement } from '../../../../../model/IContentElement';
import { SpecmateDataService } from '../../../../data/modules/data-service/services/specmate-data.service';
import { NavigatorService } from '../../navigator/services/navigator.service';
import { AuthenticationService } from '../../../../views/main/authentication/modules/auth/services/authentication.service';
import { Search } from '../../../../../util/search';
import { Url } from '../../../../../util/url';
import { TreeNavigatorService } from '../services/tree-navigator.service';
import { Key } from '../../../../../util/keycode';
import { FocusService } from '../../../services/focus.service';


@Component({
    moduleId: module.id.toString(),
    selector: 'project-explorer',
    templateUrl: 'project-explorer.component.html',
    styleUrls: ['project-explorer.component.css']
})
export class ProjectExplorer implements OnInit {

    public rootElements: IContainer[];

    private searchQueries: Subject<string>;
    protected searchResults: IContentElement[];

    public get currentElement(): IContainer {
        return this.navigator.currentElement;
    }

    constructor(private dataService: SpecmateDataService, private navigator: NavigatorService,
        private auth: AuthenticationService, private treeNav: TreeNavigatorService,
        private focus: FocusService) { }

    ngOnInit() {
        this.initialize();
        this.auth.authChanged.subscribe(() => {
            this.initialize();
        });
        this.focus.demandFocus(this);
    }

    ngOnDestroy() {
        this.focus.returnFocus(this);
    }

    protected search(query: string): void {
        this.searchQueries.next(query);
    }

    private async initialize(): Promise<void> {
        this.treeNav.clean();

        if (!this.auth.isAuthenticated) {
            this.clean();
            return;
        }

        const project: IContainer = await this.dataService.readElement(this.auth.token.project);
        this.rootElements = [project];
        this.treeNav.roots = this.rootElements.map( x => x.url);

        let filter = {'-type': 'Folder'};

        // We clean this in case we're logged out. Thus, we need to reinit here.
        if (this.searchQueries === undefined) {
            this.searchQueries = new Subject<string>();
        }
        this.searchQueries
            .debounceTime(300)
            .distinctUntilChanged()
            .subscribe( query => {
                if (query && query.length >= 3) {
                 query = Search.processSearchQuery(query);
                 this.dataService.search(query, filter).then(results => {
                     this.searchResults = results;
                     this.searchIndex = 0;
                     this.treeNav.endNavigation();
                    });
                } else {
                    this.searchResults = [];
                    this.treeNav.startNavigation();
                }
            }
        );
    }

    private clean(): void {
        this.rootElements = undefined;
        this.searchQueries = undefined;
        this.searchResults = undefined;
    }

    public get projectName(): string {
        return this.auth.token.project;
    }

    public searchIndex = 0;

    @HostListener('window:keydown', ['$event'])
    keyEvent(event: KeyboardEvent) {
        if (!this.focus.isFocused(this)) {
            return;
        }

        const interceptedKeys = [Key.ARROW_UP, Key.ARROW_DOWN, Key.ARROW_LEFT, Key.ARROW_RIGTH, Key.SPACEBAR, Key.ENTER];
        if (interceptedKeys.indexOf(event.keyCode) >= 0) {
            event.preventDefault();
            event.stopPropagation();
        }

        let hasSearchresults = (this.searchResults ? (this.searchResults.length > 0) : false);

        if (hasSearchresults) {
            switch (event.keyCode) {
                case Key.ARROW_UP:
                    if (this.searchIndex > 0) {
                        this.searchIndex--;
                    }
                    break;

                case Key.ARROW_DOWN:
                    if (this.searchIndex < this.searchResults.length - 1) {
                        this.searchIndex++;
                    }
                    break;

                case Key.SPACEBAR: // Falls through
                case Key.ENTER:
                    this.navigator.navigate(this.searchResults[this.searchIndex]);
                    break;
            }

            return;
        }

        switch (event.keyCode) {
            case Key.ARROW_UP:
                this.treeNav.navigateUp();
                break;

            case Key.ARROW_DOWN:
                this.treeNav.navigateDown();
                break;

            case Key.ARROW_LEFT:
                this.treeNav.navigateLeft();
                break;

            case Key.ARROW_RIGTH:
                this.treeNav.navigateRight();
                break;

            case Key.SPACEBAR: // Falls through
            case Key.ENTER:
                this.treeNav.selectElement();
                break;
        }

        this.treeNav.scrollToSelection();
    }
}
