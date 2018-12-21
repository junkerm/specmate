import { Config } from '../../../../../config/config';
import { Component, OnInit, HostListener, ViewChild, Inject } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { NgbDropdownConfig, NgbDropdown } from '@ng-bootstrap/ng-bootstrap';
import { CookieService } from 'ngx-cookie';
import { DOCUMENT } from '@angular/platform-browser';

@Component({
    selector: 'language-chooser',
    moduleId: module.id.toString(),
    templateUrl: 'language-chooser.component.html',
    providers: [NgbDropdownConfig]
})
export class LanguageChooser implements OnInit {

    private static LANGUAGE_KEY = 'specmate-display-language';

    private selectionIndex = 0;

    @ViewChild('dropdownRef')
    set dropdownRef(ref: NgbDropdown) {
        this._dropdownRef = ref;
    }
    private _dropdownRef: NgbDropdown;

    constructor(private translate: TranslateService,
        private cookie: CookieService,
        config: NgbDropdownConfig,
        @Inject(DOCUMENT) private document: any) {
        config.autoClose = true;
        config.placement = 'bottom-right';
    }

    public ngOnInit(): void {
        this.translate.addLangs(Config.LANGUAGES.map(languageObject => languageObject.code));
        const cookieLang = this.retrieveFromCookie();
        if (cookieLang !== undefined && cookieLang !== null && cookieLang.length > 0) {
            this.translate.setDefaultLang(cookieLang);
            this.language = cookieLang;
            return;
        }
        const browserLang = this.translate.getBrowserLang();
        if (Config.USE_BROWSER_LANGUAGE && this.translate.getLangs().indexOf(browserLang) > 0) {
            this.translate.setDefaultLang(browserLang);
            this.language = browserLang;
        } else {
            this.translate.setDefaultLang(Config.DEFAULT_LANGUAGE.code);
            this.language = Config.DEFAULT_LANGUAGE.code;
        }
    }

    public set language(language: string) {
        this.translate.use(language);
        this.setLangAttr(language);
        this.storeInCookie();
    }

    public get language(): string {
        return this.translate.currentLang;
    }

    public get otherLanguages(): string[] {
        return this.translate.getLangs();
    }

    public getLanguageName(code: string): string {
        return Config.LANGUAGES.find(languageObject => languageObject.code === code).name;
    }

    public get enabled(): boolean {
        return Config.LANGUAGE_CHOOSER_ENABLED;
    }

    private storeInCookie(): void {
        this.cookie.put(LanguageChooser.LANGUAGE_KEY, this.language);
    }

    private setLangAttr(language: string): void {
        this.document.documentElement.lang = language;
    }

    private retrieveFromCookie(): string {
        return this.cookie.get(LanguageChooser.LANGUAGE_KEY);
    }

    public setSelectionIndex(newIndex: number) {
        this.selectionIndex = newIndex;
    }
}
