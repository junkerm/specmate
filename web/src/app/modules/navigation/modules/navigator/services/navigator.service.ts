import { Subscription } from 'rxjs/Subscription';
import { Injectable, EventEmitter } from '@angular/core';
import { IContainer } from '../../../../../model/IContainer';
import { SpecmateDataService } from '../../../../data/modules/data-service/services/specmate-data.service';
import { LoggingService } from '../../../../views/side/modules/log-list/services/logging.service';
import { Router, ActivatedRoute, NavigationEnd } from '@angular/router';
import { Url } from '../../../../../util/url';

@Injectable()
export class NavigatorService {

    private history: IContainer[] = [];
    private current = -1;
    private _hasNavigated: EventEmitter<IContainer>;
    private _currentContents: IContainer[];

    constructor(
        private dataService: SpecmateDataService,
        private logger: LoggingService,
        private router: Router,
        private route: ActivatedRoute) {

        let subscription: Subscription = this.router.events.subscribe((event) => {
            if (event instanceof NavigationEnd && !this.hasHistory) {
                if (!this.route.snapshot.children[0] || !Url.fromParams(this.route.snapshot.children[0].params)) {
                    return;
                }
                let currentUrl: string = Url.fromParams(this.route.snapshot.children[0].params);
                this.dataService.readElement(currentUrl, true)
                    .then((element: IContainer) => {
                        if (element) {
                            this.current = 0;
                            this.history[this.current] = element;
                            subscription.unsubscribe();
                            return Promise.resolve();
                        }
                        return Promise.reject('Could not load element: ' + currentUrl);
                    })
                    .then(() => this.dataService.readContents(currentUrl, true))
                    .then((contents: IContainer[]) => this._currentContents = contents)
                    .then(() => {
                        console.log(this.currentElement);
                        console.log(this.currentContents);
                        this.hasNavigated.emit(this.currentElement);
                    });
            }
        });
    }

    public get hasNavigated(): EventEmitter<IContainer> {
        if (!this._hasNavigated) {
            this._hasNavigated = new EventEmitter();
        }
        return this._hasNavigated;
    }

    public navigate(element: IContainer): void {
        if (this.history[this.current] !== element) {
            this.history.splice(this.current + 1, 0, element);
            this.performNavigation(this.current + 1).then(() => {
                this.history = this.history.splice(0, this.current + 1);
                this.logger.debug('Navigated', this.currentElement.url);
            }).catch(() => {
                this.history.splice(this.current + 1, 1);
            });
        }
    }

    public forward(): void {
        if (this.hasNext) {
            this.performNavigation(this.current + 1);
        }
    }

    public back(): void {
        if (this.hasPrevious) {
            this.performNavigation(this.current - 1);
        }
    }

    private performNavigation(index: number): Promise<void> {
        return this.router.navigate([Url.basePath(this.history[index]), this.history[index].url]).then((hasNavigated: boolean) => {
            if (hasNavigated) {
                this.current = index;
                this.dataService.discardChanges();
                this.dataService.clearCommits();
                return Promise.resolve();
            }
            return Promise.reject('Navigation was not performed');
        })
        .then(() => this.dataService.readContents(this.currentElement.url, true))
        .then((contents: IContainer[]) => this._currentContents = contents)
        .then(() => this.hasNavigated.emit(this.currentElement));
    }

    public get currentElement(): IContainer {
        return this.history[this.current];
    }

    public get currentContents(): IContainer[] {
        return this._currentContents;
    }

    public get hasPrevious(): boolean {
        return this.current > 0;
    }

    public get hasNext(): boolean {
        return this.current < this.history.length - 1;
    }

    private get previousElement(): IContainer {
        if (this.hasPrevious) {
            return this.history[this.current - 1];
        }
        return undefined;
    }

    private get nextElement(): IContainer {
        if (this.hasNext) {
            return this.history[this.current + 1];
        }
        return undefined;
    }

    private get hasHistory(): boolean {
        return this.current >= 0;
    }

    public get isWelcome(): boolean {
        return !this.hasHistory && this.currentElement === undefined;
    }
}
