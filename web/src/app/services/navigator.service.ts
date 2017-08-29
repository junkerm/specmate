import { Url } from '../util/Url';
import { Router, ActivatedRoute, Params, ParamMap, NavigationEnd } from '@angular/router';
import { Injectable } from '@angular/core';
import { IContainer } from "../model/IContainer";
import { ConfirmationModal } from "../components/core/forms/confirmation-modal.service";
import { SpecmateDataService } from "./specmate-data.service";
import { Config } from "../config/config";
import { Subscription } from "rxjs/Subscription";

@Injectable()
export class NavigatorService {

    private history: IContainer[] = [];
    private current: number = -1;

    constructor(private dataService: SpecmateDataService, private modal: ConfirmationModal, private router: Router, private route: ActivatedRoute) {
        let subscription: Subscription = this.router.events.subscribe((event) => {
            if(event instanceof NavigationEnd && !this.hasHistory) {
                if(!this.route.snapshot.children[0] || !Url.fromParams(this.route.snapshot.children[0].params)) {
                    return;
                }
                let currentUrl: string = Url.fromParams(this.route.snapshot.children[0].params);
                this.dataService.readElement(currentUrl).then((element: IContainer) => {
                    this.current = 0;
                    this.history[this.current] = element;
                    subscription.unsubscribe();
                });
            }
        });
    }

    public navigate(element: IContainer) : void {
        if(this.history[this.current] !== element) {
            this.history.splice(this.current + 1, 0, element);
            this.performNavigation(this.current + 1).then(() => {
                this.history = this.history.splice(0, this.current + 1);
            }).catch(() => {
                this.history.splice(this.current + 1, 1);
            });
        }
    }

    public forward(): void {
        if(this.hasNext) {
            this.performNavigation(this.current + 1);
        }
    }

    public back(): void {
        if(this.hasPrevious) {
            this.performNavigation(this.current - 1);
        }
    }

    private performNavigation(index: number): Promise<void> {
        return this.router.navigate([Url.basePath(this.history[index]), this.history[index].url]).then((hasNavigated: boolean) => {
            if(hasNavigated) {
                this.current = index;
                this.dataService.clearCommits();
            }
        });
    }

    public get currentElement(): IContainer {
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

    private get hasHistory(): boolean {
        return this.current >= 0;
    }
}
