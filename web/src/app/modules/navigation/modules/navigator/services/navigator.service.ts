import { Subscription } from 'rxjs/Subscription';
import { Injectable, EventEmitter } from '@angular/core';
import { IContainer } from '../../../../../model/IContainer';
import { SpecmateDataService } from '../../../../data/modules/data-service/services/specmate-data.service';
import { LoggingService } from '../../../../views/side/modules/log-list/services/logging.service';
import { Router, NavigationEnd, NavigationCancel, ActivatedRoute } from '@angular/router';
import { Url } from '../../../../../util/url';
import { Location } from '@angular/common';
import { TranslateService } from '@ngx-translate/core';
import { Config } from '../../../../../config/config';
import { AuthenticationService } from '../../../../views/main/authentication/modules/auth/services/authentication.service';

@Injectable()
export class NavigatorService {

    private history: IContainer[];
    private current: number;
    private _hasNavigated: EventEmitter<IContainer>;
    private _currentContents: IContainer[];
    private redirect: string;

    private get currentElementUrl(): string {
        if (this.redirect !== undefined) {
            return Url.stripBasePath(this.redirect);
        }
        return Url.stripBasePath(this.location.path());
    }

    constructor(
        private dataService: SpecmateDataService,
        private auth: AuthenticationService,
        private logger: LoggingService,
        private router: Router,
        private route: ActivatedRoute,
        private location: Location,
        private translate: TranslateService) {

        this.initHistory();

        this.auth.authChanged.subscribe(() => {
            if (!this.auth.isAuthenticated) {
                this.initHistory();
                this.router.navigate([Config.LOGIN_URL], Url.getNavigationExtrasRedirect(this.location.path()));
            }
        });

        this.route.queryParams.subscribe(params => {
            this.redirect = params.r;
        });

        this.location.subscribe(pse => {
            this.handleBrowserBackForwardButton(Url.stripBasePath(pse.url));
        });

        this.router.events.subscribe(async event => {
            if (event instanceof NavigationEnd && this.location && this.location.path()) {
                let currentUrl: string = this.currentElementUrl;
                if (currentUrl === undefined || Config.LOGIN_URL.endsWith(currentUrl) || Config.WELCOME_URL.endsWith(currentUrl)) {
                    return Promise.resolve();
                }
                const element = await this.dataService.readElement(currentUrl, true);
                if (element) {
                    if (!this.hasHistory) {
                        this.current = 0;
                        this.history[this.current] = element;
                    }
                } else {
                    if (this.auth.isAuthenticated) {
                        return Promise.reject(this.translate.instant('couldNotLoadElement') + ': ' + currentUrl);
                    }
                }
                this._currentContents = await this.dataService.readContents(currentUrl, true);
                this.hasNavigated.emit(this.currentElement);
            }
        });
    }

    public get hasNavigated(): EventEmitter<IContainer> {
        if (!this._hasNavigated) {
            this._hasNavigated = new EventEmitter();
        }
        return this._hasNavigated;
    }

    private navigateToWelcome(): void {
        this.router.navigate([Config.WELCOME_URL]);
    }

    private async navigateDefault(): Promise<void> {
        const url = this.currentElementUrl;
        if (url === undefined || url === null || url === '') {
            this.navigateToWelcome();
        } else if (this.auth.isAuthenticatedForUrl(url)) {
            const element = await this.dataService.readElement(url);
            this.navigate(element);
        } else if (this.auth.isAuthenticated) {
            this.navigateToWelcome();
        } else {
            this.auth.deauthenticate();
        }
    }

    public async navigate(target: IContainer | 'default'): Promise<void> {
        if (target === 'default') {
            await this.navigateDefault();
            return;
        }
        const element = target as IContainer;
        if (this.history[this.current] !== element) {
            this.history.splice(this.current + 1, 0, element);
            this.performNavigation(this.current + 1).then(() => {
                this.history = this.history.splice(0, this.current + 1);
                this.logger.debug(this.translate.instant('navigated'), this.currentElement.url);
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
            return Promise.reject(this.translate.instant('navigationWasNotPerformed'));
        });
    }

    private handleBrowserBackForwardButton(navigatedTo: String): void {
        let previous: IContainer = this.previousElement;
        let next: IContainer = this.nextElement;

        if (previous && navigatedTo == previous.url) {
            this.current -= 1;
        } else if (next && navigatedTo == next.url) {
            this.current += 1;
        }

        this.dataService.discardChanges();
        this.dataService.clearCommits();
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

    private initHistory(): void {
        this.history = [];
        this.current = -1;
    }
}
