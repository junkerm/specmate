import { Component, OnInit } from '@angular/core';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/distinctUntilChanged';
import { Subject } from 'rxjs/Subject';
import { IContainer } from '../../../../../model/IContainer';
import { IContentElement } from '../../../../../model/IContentElement';
import { Search } from '../../../../../util/search';
import { SpecmateDataService } from '../../../../data/modules/data-service/services/specmate-data.service';
import { AuthenticationService } from '../../../../views/main/authentication/modules/auth/services/authentication.service';
import { NavigatorService } from '../../navigator/services/navigator.service';


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
        private auth: AuthenticationService) { }

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

        const project: IContainer = await this.dataService.readElement(this.auth.token.project);
        this.rootElements = [project];

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
        this.searchQueries = undefined;
        this.searchResults = undefined;
    }

    public get projectName(): string {
        return this.auth.token.project;
    }
}
