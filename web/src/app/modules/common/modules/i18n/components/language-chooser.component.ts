import { Config } from '../../../../../config/config';
import { Component, Input, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';

@Component({
    selector: 'language-chooser',
    moduleId: module.id.toString(),
    templateUrl: 'language-chooser.component.html'
})
export class LanguageChooser implements OnInit {
    constructor(private translate: TranslateService) { }

    public ngOnInit(): void {
        this.translate.addLangs(Config.LANGUAGES.map(languageObject => languageObject.code));
        const browserLang = this.translate.getBrowserLang();
        if (this.translate.getLangs().indexOf(browserLang) > 0) {
            this.translate.setDefaultLang(browserLang);
            this.language = browserLang;
        } else {
            this.translate.setDefaultLang(Config.DEFAULT_LANGUAGE.code);
            this.language = Config.DEFAULT_LANGUAGE.code;
        }
    }

    public set language(language: string) {
        this.translate.use(language);
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
}
