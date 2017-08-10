import { Router } from '@angular/router';
import { Injectable } from '@angular/core';
import { IContainer } from "../model/IContainer";
import { ConfirmationModal } from "../components/core/forms/confirmation-modal.service";
import { SpecmateDataService } from "./specmate-data.service";
import { Config } from "../config/config";

@Injectable()
export class NavigatorService {

    private history: IContainer[];

    constructor(private dataService: SpecmateDataService, private modal: ConfirmationModal, private router: Router) {
        this.history = [];
    }

    public navigate(element: IContainer) : void {
        if(this.dataService.hasCommits) {
            this.modal.open(Config.NAVIGATION_CONFIRMATION).then(() => {
                this.performNavigation(element);
            }).catch();
        } else {
            this.performNavigation(element);
        }
    }

    private performNavigation(element: IContainer): void {
        if(this.history[this.history.length - 1] !== element) {
            this.history.push(element);
        }
        this.dataService.clearCommits();
        this.router.navigate([element.className, element.url]);
    }

    public get hasHistory(): boolean {
        return this.history && this.history.length > 1;
    }

    public back(): void {
        
        if(this.dataService.hasCommits) {
            this.modal.open(Config.NAVIGATION_CONFIRMATION).then(() => {
                this.performBack();
            }).catch();
        } else {
            this.performBack();
        }
    }

    private performBack(): void {
        if(!this.hasHistory) {
            return;
        }
        this.history.pop();
        let lastElement: IContainer = this.history.pop();
        this.performNavigation(lastElement);
    }
}
