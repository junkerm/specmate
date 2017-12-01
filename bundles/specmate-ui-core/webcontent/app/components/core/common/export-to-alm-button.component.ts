import {ConfirmationModal} from '../../../services/notification/confirmation-modal.service';
import {SpecmateDataService} from '../../../services/data/specmate-data.service';
import {IContainer} from '../../../model/IContainer';
import {TestProcedure} from '../../../model/TestProcedure';
import { Component, OnInit, Input } from "@angular/core";
import { ValidationService } from '../../../services/validation/validation.service';


@Component({
    moduleId: module.id,
    selector: 'export-to-alm-button',
    templateUrl: 'export-to-alm-button.component.html',
    styleUrls: ['export-to-alm-button.component.css']
})

export class ExportToALMButton implements OnInit {

    @Input()
    public testProcedure: TestProcedure;

    private contents: IContainer[];

    constructor(private dataService: SpecmateDataService, private modal: ConfirmationModal, private validation: ValidationService) { }

    ngOnInit(): void {
        this.dataService.readContents(this.testProcedure.url).then((contents: IContainer[]) => this.contents = contents);
    }


    /** Pushes or updates a test procedure to HP ALM */
    public pushTestProcedure() : void {
        if(!this.isValid){
            return;
        }
        this.modal.confirmSave().then( () =>
            this.dataService.commit("Save before ALM Export").then( () =>
                this.dataService.performOperations(this.testProcedure.url, "syncalm")
                .then((result) => {
                        if(result){ 
                            this.modal.open("Procedure exported successfully", false);
                        }
                    }
                )
            )
        );
    }

    private get isValid(): boolean {
        return this.validation.isValid(this.testProcedure) && this.validation.allValid(this.contents);
    }
}
