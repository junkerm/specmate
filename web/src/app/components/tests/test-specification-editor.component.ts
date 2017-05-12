import {GenericForm} from '../core/forms/generic-form.component';
import { CEGModel } from '../../model/CEGModel';
import { Type } from '../../util/Type';
import { TestParameter } from '../../model/TestParameter';
import { TestCase } from '../../model/TestCase';
import { IContentElement } from '../../model/IContentElement';
import { TestSpecification } from '../../model/TestSpecification';
import { Url } from '../../util/Url';
import { Params, ActivatedRoute, Router } from '@angular/router';
import { SpecmateDataService } from '../../services/specmate-data.service';
import { IContainer } from '../../model/IContainer';
import { Requirement } from '../../model/Requirement';
import {ViewChild, OnInit,  Component} from '@angular/core';

@Component({
    moduleId: module.id,
    selector: 'test-specification-editor',
    templateUrl: 'test-specification-editor.component.html'
})
export class TestSpecificationEditor implements OnInit {

    /** The test specification to be shown */
    private testSpecification: TestSpecification;

    /** Input parameters */
    private inputParameters: IContentElement[];

    /** Output parameters */
    private outputParameters: IContentElement[];

    /** All parameters */
    private allParameters: IContentElement[];

    /** Test cases */
    private testCases: IContentElement[];

    /** The CEG model this test specification is linked to */
    private cegModel: CEGModel;

    /** The requirement this test specification is linked to */
    private requirement: Requirement;

    /** The type of a test case (used for filtering) */
    private testCaseType = TestCase;

    /** The type of a test parameter (used for filtering) */
    private parameterType = TestParameter;

    /** The generic form used in this component */
    @ViewChild(GenericForm)
    private genericForm : GenericForm;

    /** constructor  */
    constructor(private dataService: SpecmateDataService, private router: Router, private route: ActivatedRoute) { }

    /** Read contents and CEG and requirements parents */
    ngOnInit() {
        this.route.params
            .switchMap((params: Params) => this.dataService.readElement(Url.fromParams(params)))
            .subscribe((testSpec: IContainer) => {
                this.testSpecification = testSpec as TestSpecification;
                this.readContents();
                this.readParents();
            });
    }

    /** Rads to the contents of the test specification  */
    private readContents(): void {
        if (this.testSpecification) {
            this.dataService.readContents(this.testSpecification.url).then((
                contents: IContainer[]) => {
                this.inputParameters = contents.filter(c => {
                    return Type.is(c, TestParameter) && (<TestParameter>c).type==="INPUT";
                });
                this.outputParameters = contents.filter(c => {
                    return Type.is(c, TestParameter) && (<TestParameter>c).type==="OUTPUT";
                });
                this.allParameters=this.inputParameters.concat(this.outputParameters);
                this.testCases = contents.filter(c => {
                    return Type.is(c, TestCase);
                });
            });
        }
    }

    /** Reads the CEG and requirements parents of the test specficiation */
    private readParents(): void {
        if (this.testSpecification) {
            this.dataService.readElement(Url.parent(this.testSpecification.url)).then((
                element: IContainer) => {
                if (Type.is(element, CEGModel)) {
                    this.cegModel = <CEGModel>element;
                    this.readCEGParent();
                } else if (Type.is(element, Requirement)) {
                    this.requirement = <Requirement>element;
                }
            });
        }
    }

    /** Reads the requirement parent of the CEG model */
    private readCEGParent(): void {
        if (this.cegModel) {
            this.dataService.readElement(Url.parent(this.cegModel.url)).then((
                element: IContainer) => {
                if (Type.is(element, Requirement)) {
                    this.requirement = <Requirement>element;
                }
            });
        }
    }

    /** Return true if all user inputs are valid  */
    private get isValid(): boolean {
        if(!this.genericForm) {
            return true;
        }
        return  this.genericForm.isValid;
    }

    private save(): void {
        this.dataService.commit("Save");
    }
}