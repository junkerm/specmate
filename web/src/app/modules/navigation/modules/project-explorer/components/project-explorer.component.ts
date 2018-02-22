import { Component, OnInit } from '@angular/core';
import { Subject } from 'rxjs/Subject';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/distinctUntilChanged';
import { IContainer } from '../../../../../model/IContainer';
import { IContentElement } from '../../../../../model/IContentElement';
import { SpecmateDataService } from '../../../../data/modules/data-service/services/specmate-data.service';
import { NavigatorService } from '../../navigator/services/navigator.service';

@Component({
    moduleId: module.id.toString(),
    selector: 'project-explorer',
    templateUrl: 'project-explorer.component.html',
    styleUrls: ['project-explorer.component.css']
})
export class ProjectExplorer implements OnInit {

    baseUrl = '/';

    rootElements: IContainer[];

    private searchQueries = new Subject<string>();
    protected searchResults: IContentElement[];

    public get currentElement(): IContainer {
        return this.navigator.currentElement;
    }

    constructor(private dataService: SpecmateDataService, private navigator: NavigatorService) { }

    ngOnInit() {
        this.dataService.readContents(this.baseUrl).then((children: IContainer[]) => this.rootElements = children);
        let filter = {'-type': 'Folder'};
        this.searchQueries
            .debounceTime(300)
            .distinctUntilChanged()
            .subscribe( query => {
                if (query && query.length >= 3) {
                 query = query.replace(/([^\(\):\s-+]+(-[^\(\):\s-+]+)*)\b(?!\:)/g, '$&*');
                 this.dataService.search(query, filter).then(results => this.searchResults = results);
                } else {
                    this.searchResults = [];
                }
            }
        );
    }

    protected search(query: string): void {
        this.searchQueries.next(query);
    }
}
