import { Config } from '../../../../../config/config';
import { Component, OnInit, HostListener, ViewChild } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { NgbDropdownConfig, NgbDropdown } from '@ng-bootstrap/ng-bootstrap';
import { CookieService } from 'ngx-cookie';
import { Key } from '../../../../../util/keycode';
import { FocusService } from '../../../../navigation/services/focus.service';

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
        private config: NgbDropdownConfig,
        private focus: FocusService) {
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

    private retrieveFromCookie(): string {
        return this.cookie.get(LanguageChooser.LANGUAGE_KEY);
    }

    // Navigation with arrow keys
    @HostListener('window:keyup', ['$event'])
    keyEvent(event: KeyboardEvent) {
        if (!this.focus.isFocused(this)) {
            return;
        }

        if (event.key === Key.ARROW_UP && this.selectionIndex > 0) {
            this.selectionIndex--;
        }

        if (event.key === Key.ARROW_DOWN && this.selectionIndex < this.otherLanguages.length) {
            this.selectionIndex++;
        }

        if (event.key === Key.SPACEBAR) {
            this.language = this.otherLanguages[this.selectionIndex];
            this.dropdownClose();
        }

        if (event.key === Key.ESC) {
            this.selectionIndex = 0;
            this.dropdownClose();
        }
    }

    public setSelectionIndex(newIndex: number) {
        this.selectionIndex = newIndex;
    }

    public dropdownChange(isOpen: boolean) {
        if (isOpen) {
            this.focus.demandFocus(this);
        } else {
            this.focus.returnFocus(this);
        }
    }

    public dropdownClose() {
        this._dropdownRef.close();
        this.focus.returnFocus(this);
    }
}
