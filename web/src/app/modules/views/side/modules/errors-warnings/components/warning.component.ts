import { Component, Input, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { CEGNode } from '../../../../../../model/CEGNode';
import { IContainer } from '../../../../../../model/IContainer';
import { Type } from '../../../../../../util/type';
import { ValidationResult } from '../../../../../../validation/validation-result';
import { AdditionalInformationService } from '../../links-actions/services/additional-information.service';
import { SelectedElementService } from '../../selected-element/services/selected-element.service';

@Component({
    selector: '[warning]',
    templateUrl: 'warning.component.html',
    styleUrls: ['warning.component.css']
})

export class Warning implements OnInit {
    public valResults: ValidationResult[] = [];
    private affectedElements: IContainer[] = [];

    @Input()
    public set warnElements(elems: ValidationResult[]) {
        if (!elems) {
            return;
        }
        this.valResults = elems;

        this.affectedElements = [];
        if (elems.length > 0) {
            this.affectedElements = elems[0].elements;
            for (const elem of elems[0].elements) {
                if (this.affectedElements.indexOf(elem) < 0) {
                    this.affectedElements.push(elem);
                }
            }
        }
    }

    constructor(
        private selectedElementService: SelectedElementService,
        private additionalInformation: AdditionalInformationService,
        public translate: TranslateService) { }

    ngOnInit() { }

    public get name(): string {
        if (this.affectedElements.length == 0) {
            return 'Model';
        }
        return this.affectedElements.map( e => {
            if (Type.is(e, CEGNode)) {
                let node = e as CEGNode;
                let varN = node.variable ? node.variable : 'Unnamed Variable';
                let cond = node.condition ? node.condition : '';
                return (varN + ' (' + cond + ')').trim();
            }

            if (!e.name || e.name.length == 0) {
                return 'Unnamed Element';
            }

            let name = e.name;
            name = name.replace(/(\d{4})-(\d{2})-(\d{2}) (\d{2}):(\d{2}):(\d{2})$/, ''); // Remove Timestamp of Node
            return name.trim();
        }).join(',\n'); // Add forced linebreaks after each name. Ensured by css 'white-space:pre-line'
    }

    public selectElement() {
        if (this.affectedElements.length > 0) {
            this.selectedElementService.selectAll(this.affectedElements);
        } else {
            if (this.additionalInformation.model) {
                this.selectedElementService.select(this.additionalInformation.model);
            } else if (this.additionalInformation.element) {
                this.selectedElementService.select(this.additionalInformation.element);
            } else {
                this.selectedElementService.deselect();
            }
        }
        // Prevent default Link event
        return false;
    }
}
