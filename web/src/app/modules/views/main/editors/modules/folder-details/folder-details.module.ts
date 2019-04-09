import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { TestSpecificationGeneratorButtonModule } from '../../../../../actions/modules/test-specification-generator-button/test-specification-generator-button.module';
import { IconsModule } from '../../../../../common/modules/icons/icons.module';
import { TruncateModule } from '../../../../../common/modules/truncate/truncate.module';
import { NavigatorModule } from '../../../../../navigation/modules/navigator/navigator.module';
import { SpecmateSharedModule } from '../../../../../specmate/specmate.shared.module';
import { ContentsContainerModule } from '../contents-container/contents-container.module';
import { FolderDetails } from './components/folder-details.component';

@NgModule({
    imports: [
        BrowserModule,
        NavigatorModule,
        TruncateModule,
        TestSpecificationGeneratorButtonModule,
        SpecmateSharedModule,
        IconsModule,
        ContentsContainerModule
    ],
    exports: [FolderDetails],
    declarations: [FolderDetails],
    providers: [],
})
export class FolderDetailsModule { }
