import { Url } from '../../util/Url';
import {ParamMap, ActivatedRoute,  Router,  Params,  Event,  NavigationEnd} from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { SpecmateDataService } from '../../services/specmate-data.service';
import { IContainer } from '../../model/IContainer';

@Component({
    moduleId: module.id,
    selector: 'project-explorer',
    templateUrl: 'project-explorer.component.html',
    styleUrls: ['project-explorer.component.css']
})
export class ProjectExplorer implements OnInit {

    baseUrl: string = '/';

    currentElementUrl: string = '';

    rootElements: IContainer[];

    constructor(private dataService: SpecmateDataService, private router: Router, private route: ActivatedRoute) {
        this.router.events.filter((event: Event) => event instanceof NavigationEnd).subscribe((event: Event) => this.onNavigation());
    }

    private onNavigation(): void {
        this.currentElementUrl = Url.fromParams(this.route.snapshot.firstChild.params);
    }

    ngOnInit() {
        this.dataService.readContents(this.baseUrl).then((children: IContainer[]) => this.rootElements = children);
    }
}  