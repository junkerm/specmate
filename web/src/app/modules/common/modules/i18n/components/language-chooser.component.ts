import { Component, Input } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';

@Component({
    selector: 'language-chooser',
    moduleId: module.id.toString(),
    templateUrl: 'language-chooser.component.html'
})
export class LanguageChooser {
    constructor(private translate: TranslateService) { }

    public set language(language: string) {
        this.translate.use(language);
    }

    public get languages(): string[] {
        return this.translate.getLangs();
    }
}
