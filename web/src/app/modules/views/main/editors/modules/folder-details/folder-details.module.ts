import { NgModule } from '@angular/core';
import { FolderDetails } from './components/folder-details.component';
import { BrowserModule } from '@angular/platform-browser';
import { NavigatorModule } from '../../../../../navigation/modules/navigator/navigator.module';
import { TruncateModule } from '../../../../../common/modules/truncate/truncate.module';
import { TestSpecificationGeneratorButtonModule } from
    '../../../../../actions/modules/test-specification-generator-button/test-specification-generator-button.module';
import { IconsModule } from '../../../../../common/modules/icons/icons.module';
import { TranslateModule } from '@ngx-translate/core';

@NgModule({
    imports: [
        BrowserModule,
        NavigatorModule,
        TruncateModule,
        TestSpecificationGeneratorButtonModule,
        IconsModule,
        TranslateModule
    ],
    exports: [FolderDetails],
    declarations: [FolderDetails],
    providers: [],
})
export class FolderDetailsModule { }
