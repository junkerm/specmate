import 'rxjs/add/operator/switchMap';
import { Config } from '../../config/config';
import { CEGModel } from '../../model/CEGModel';
import { IContainer } from '../../model/IContainer';
import { Requirement } from '../../model/Requirement';
import { SpecmateDataService } from '../../services/specmate-data.service';
import { Id } from '../../util/Id';
import { Url } from '../../util/Url';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';


@Component({
    moduleId: module.id,
    selector: 'requirements-details',
    templateUrl: 'requirement-details.component.html',
    styleUrls: ['requirement-details.component.css']
})

export class RequirementsDetails implements OnInit {

    private requirement: Requirement;
    private contents: IContainer[];

    constructor(private dataService: SpecmateDataService, private router: Router, private route: ActivatedRoute) { }

    ngOnInit() {
        this.route.params
            .switchMap((params: Params) => this.dataService.getElement(params['url']))
            .subscribe(
            requirement => {
                this.requirement = requirement as Requirement;
                this.dataService.getContents(requirement.url).then((
                    contents: IContainer[]) => {
                    this.contents = contents;
                });
            });
    }

    delete(model: CEGModel): void {
        this.dataService.deleteElement(model).then((contents: IContainer[]) => {
            this.contents = contents;
            //this.router.navigate(['/requirements', { outlets: { 'main': [this.requirement.url] } }]);
        });
    }

    createModel(): void {
        if (!this.contents) {
            return;
        }
        let model: CEGModel = new CEGModel();
        model.id = Id.generate(this.contents, Config.CEG_MODEL_BASE_ID);
        model.url = Url.build([this.requirement.url, model.id]);
        model.name = Config.CEG_NEW_MODEL_NAME;
        model.description = Config.CEG_NEW_NODE_DESCRIPTION;
        this.dataService.createElement(model)
            .then((contents: IContainer[]) => {
                this.contents = contents;  
            })
            .then(() => {
                this.dataService.commit();
            })
            .then(() => {
                this.router.navigate(['/requirements', { outlets: { 'main': [model.url, 'ceg'] } }]);
            });
    }
}
