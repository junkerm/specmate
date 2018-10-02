import { Component, OnInit } from '@angular/core';
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
import { TranslateService } from '../../../../../../../node_modules/@ngx-translate/core';


@Component({
    moduleId: module.id.toString(),
    selector: 'project-explorer',
    templateUrl: 'project-explorer.component.html',
    styleUrls: ['project-explorer.component.css']
})
export class ProjectExplorer implements OnInit {

    public rootElements: IContainer[];
    public rootLibraries: IContainer[];

    private searchQueries: Subject<string>;
    protected searchResults: IContentElement[];

    public get currentElement(): IContainer {
        return this.navigator.currentElement;
    }

    constructor(private translate: TranslateService, private dataService: SpecmateDataService,
        private navigator: NavigatorService, private auth: AuthenticationService) { }

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
        if (!this.auth.isAuthenticated) {
            this.clean();
            return;
        }

        // const project: IContainer = await this.dataService.readElement(this.auth.token.project);
        let libraryFolders: string[] = this.auth.token.libraryFolders;
        let projectContents: IContainer[] = await this.dataService.readContents(this.auth.token.project);

        this.rootElements = projectContents.filter(c => libraryFolders.indexOf(c.id) == -1);
        this.rootLibraries = projectContents.filter(c => libraryFolders.indexOf(c.id) > -1);

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
                    });
                } else {
                    this.searchResults = [];
                }
            }
        );
    }

    private clean(): void {
        this.rootElements = undefined;
        this.rootLibraries = undefined;
        this.searchQueries = undefined;
        this.searchResults = undefined;
    }

    public get projectName(): string {
        return this.auth.token.project;
    }
}
