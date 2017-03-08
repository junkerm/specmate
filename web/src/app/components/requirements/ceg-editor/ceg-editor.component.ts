import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Params } from '@angular/router';
import { SpecmateDataService } from '../../../services/specmate-data.service';
import { CEGModel } from '../../../model/CEGModel';
import { CEGNode } from '../../../model/CEGNode';
import { CEGConnection } from '../../../model/CEGConnection';
import { Requirement } from '../../../model/Requirement';
import { IContainer } from '../../../model/IContainer';
import { Type } from '../../../util/Type';
import { Url } from '../../../util/Url';
import { Id } from '../../../util/Id';

@Component({
    moduleId: module.id,
    selector: 'ceg-editor',
    templateUrl: 'ceg-editor.component.html'
})
export class CEGEditor implements OnInit {

    private static INITIAL_NAME = "";
    private static INITIAL_DESCRIPTION = "";
    private static DESCRIPTION_ROWS = 10;

    constructor(private formBuilder: FormBuilder, private dataService: SpecmateDataService, private route: ActivatedRoute, private location: Location) {
        this.createForm();
    }

    private model: CEGModel;
    private name: string;
    private cegForm: FormGroup;
    private container: IContainer;
    private rows = CEGEditor.DESCRIPTION_ROWS;
    private isNew: boolean;

    private nodeType = CEGNode;
    private connectionType = CEGConnection;

    ngOnInit() {
        this.route.params
            .switchMap((params: Params) => this.dataService.getDetails(params['url']))
            .subscribe(container => {
                this.container = container;
                if (Type.is(container, CEGModel)) {
                    this.model = container as CEGModel;
                    this.isNew = false;
                    this.setFormValues();
                }
                else if (Type.is(container, Requirement)) {
                    this.model = new CEGModel();
                    this.model.name = CEGEditor.INITIAL_NAME;
                    this.model.description = CEGEditor.INITIAL_DESCRIPTION;
                    this.isNew = true;
                    this.setFormValues();
                    this.updateModel(this.cegForm);
                }
            });
    }

    createForm(): void {
        this.cegForm = this.formBuilder.group({
            name: ['', Validators.required],
            description: ''
        });
        this.cegForm.valueChanges.subscribe(formModel => {
            this.updateModel(formModel);
        });
    }

    updateModel(formModel: any): void {
        this.model.name = formModel.name as string;
        this.model.description = formModel.description as string;
        if (this.isNew && this.model.name) {
            this.model.id = Id.fromName(this.model.name);
            this.model.url = Url.build([this.container.url, this.model.id]);
        }
    }

    setFormValues(): void {
        this.cegForm.setValue({
            name: this.model.name,
            description: this.model.description
        });
    }

    discard(): void {
        //TODO: Really discard new data and go back. Implement a reset button? Reactive Forms in Angular should help.
        console.log("We do not have reset the values of the model! TODO!");
        this.location.back();
    }
}