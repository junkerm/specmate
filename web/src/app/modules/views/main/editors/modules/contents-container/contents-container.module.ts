import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { TranslateModule } from '@ngx-translate/core';
import { NavigatorModule } from '../../../../../navigation/modules/navigator/navigator.module';
import { TruncateModule } from '../../../../../common/modules/truncate/truncate.module';
// tslint:disable-next-line:max-line-length
import { TestSpecificationGeneratorButtonModule } from '../../../../../actions/modules/test-specification-generator-button/test-specification-generator-button.module';
import { IconsModule } from '../../../../../common/modules/icons/icons.module';
import { CEGModelContainer } from './components/ceg-model-container.component';
import { ProcessModelContainer } from './components/process-model-container.component';
import { TestSpecificationContainer } from './components/test-specification-container.component';
import { RelatedRequirementsContainer } from './components/related-requirements-container.component';
import { FolderContainer } from './components/folder-container.component';
import { SpecmateSharedModule } from '../../../../../specmate/specmate.shared.module';
// tslint:disable-next-line:max-line-length
import { ShortModelErrorDisplayModule } from '../../../../../actions/modules/short-error-message-display/short-model-error-message-display.module';

@NgModule({
    imports: [
        BrowserModule,
        NavigatorModule,
        TruncateModule,
        TestSpecificationGeneratorButtonModule,
        SpecmateSharedModule,
        IconsModule,
        ShortModelErrorDisplayModule,
        IconsModule,
        TranslateModule
    ],
    exports: [CEGModelContainer, ProcessModelContainer, TestSpecificationContainer, RelatedRequirementsContainer, FolderContainer],
    declarations: [CEGModelContainer, ProcessModelContainer, TestSpecificationContainer, RelatedRequirementsContainer, FolderContainer],
    providers: [],
})
export class ContentsContainerModule { }
