import {Url} from '../util/Url';
import { Router } from '@angular/router';
import { Injectable } from '@angular/core';
import { IContainer } from "../model/IContainer";
import { ConfirmationModal } from "../components/core/forms/confirmation-modal.service";
import { SpecmateDataService } from "./specmate-data.service";
import { Config } from "../config/config";

@Injectable()
export class NavigatorService {

    private history: IContainer[] = [];
    private current: number = -1;

    constructor(private dataService: SpecmateDataService, private modal: ConfirmationModal, private router: Router) { }

    public navigate(element: IContainer) : void {
        if(this.history[this.current] !== element) {
            this.history[++this.current] = element;
            this.history = this.history.splice(0, this.current + 1);
            this.performNavigation();
        }
    }

    public forward(): void {
        if(this.hasNext) {
            this.current++;
            this.performNavigation();
        }
    }

    public back(): void {
        if(this.hasPrevious) {
            this.current--;
            this.performNavigation();
        }
    }

    private performNavigation(): void {
        this.router.navigate([Url.basePath(this.currentElement), this.currentElement.url]).then((hasNavigated: boolean) => {
            if(hasNavigated) {
                this.dataService.clearCommits();
            }
        });
    }

    private get currentElement(): IContainer {
        return this.history[this.current];
    }

    public get hasPrevious(): boolean {
        return this.current > 0;
    }

    public get hasNext(): boolean {
        return this.current < this.history.length - 1;
    }

    private get previousElement(): IContainer {
        if(this.hasPrevious) {
            return this.history[this.current - 1];
        }
        return undefined;
    }

    private get nextElement(): IContainer {
        if(this.hasNext) {
            return this.history[this.current + 1];
        }
        return undefined;
    }


}
