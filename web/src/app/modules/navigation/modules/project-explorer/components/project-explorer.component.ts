import { Component, OnInit, HostListener } from '@angular/core';
import { Subject } from 'rxjs/Subject';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/distinctUntilChanged';
import { IContainer } from '../../../../../model/IContainer';
import { IContentElement } from '../../../../../model/IContentElement';
import { SpecmateDataService } from '../../../../data/modules/data-service/services/specmate-data.service';
import { NavigatorService } from '../../navigator/services/navigator.service';
import { AuthenticationService } from '../../../../views/main/authentication/modules/auth/services/authentication.service';
import { Url } from '../../../../../util/url';
import { TreeNavigatorService } from '../services/tree-navigator.service';
import { Key } from '../../../../../util/keycode';

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
        private auth: AuthenticationService, private treeNav: TreeNavigatorService) { }

    ngOnInit() {
        this.initialize();
        this.auth.authChanged.subscribe(() => {
            this.initialize();
        });
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
                 query = query.replace(/([^\(\):\s-+]+(-[^\(\):\s-+]+)*)\b(?!\:)/g, '$&*');
                 this.dataService.search(query, filter).then(results => {
                     this.searchResults = results;
                     this.treeNav.roots = this.searchResults.map( x => x.url);
                    });
                } else {
                    this.searchResults = [];
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

    @HostListener('window:keyup', ['$event'])
    keyEvent(event: KeyboardEvent) {
        // TODO Check focus

        console.log('Keyboard');
        console.log(event);

        if (event.keyCode === Key.ARROW_UP) {
            this.treeNav.navigateUp();
        }

        if (event.keyCode === Key.ARROW_DOWN) {
            this.treeNav.navigateDown();
        }

        if (event.keyCode === Key.ARROW_LEFT) {
            this.treeNav.navigateLeft();
        }

        if (event.keyCode === Key.ARROW_RIGTH) {
            this.treeNav.navigateRight();
        }

        if (event.keyCode === Key.SPACEBAR || event.keyCode === Key.ENTER) {
            this.treeNav.selectElement();
        }
    }
}
