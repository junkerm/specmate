import {IContentElement} from '../../../model/IContentElement';
import { Url } from '../../../util/Url';
import { Component, OnInit } from '@angular/core';
import { SpecmateDataService } from '../../../services/data/specmate-data.service';
import { IContainer } from '../../../model/IContainer';
import { NavigatorService } from '../../../services/navigation/navigator.service';
import { Observable }        from 'rxjs/Observable';
import { Subject }           from 'rxjs/Subject';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/distinctUntilChanged';
import 'rxjs/add/observable/of';

@Component({
    moduleId: module.id,
    selector: 'project-explorer',
    templateUrl: 'project-explorer.component.html',
    styleUrls: ['project-explorer.component.css']
})
export class ProjectExplorer implements OnInit {

    baseUrl: string = '/';

    rootElements: IContainer[];

    private searchQueries = new Subject<string>();
    protected searchResults: IContentElement[];

    public get currentElement(): IContainer {
        return this.navigator.currentElement;
    }

    constructor(private dataService: SpecmateDataService, private navigator: NavigatorService) { }

    ngOnInit() {
        this.dataService.readContents(this.baseUrl).then((children: IContainer[]) => this.rootElements = children);
        this.searchQueries
            .debounceTime(300)
            .distinctUntilChanged()
            .subscribe( query => {
                this.dataService.search(query).then(results => this.searchResults=results);
            }
        );
    }

    protected search(query: string):void {
        this.searchQueries.next(query);
    }   
}  