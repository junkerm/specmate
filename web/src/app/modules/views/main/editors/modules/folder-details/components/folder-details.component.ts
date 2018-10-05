import { Component, OnInit } from '@angular/core';
import { SpecmateViewBase } from '../../../base/specmate-view-base';
import { SpecmateDataService } from '../../../../../../data/modules/data-service/services/specmate-data.service';
import { NavigatorService } from '../../../../../../navigation/modules/navigator/services/navigator.service';
import { ActivatedRoute } from '@angular/router';
import { ConfirmationModal } from '../../../../../../notification/modules/modals/services/confirmation-modal.service';
import { TranslateService } from '@ngx-translate/core';
import { IContainer } from '../../../../../../../model/IContainer';
import { Folder } from '../../../../../../../model/Folder';
import { CEGModel } from '../../../../../../../model/CEGModel';
import { Process } from '../../../../../../../model/Process';
import { Type } from '../../../../../../../util/type';
import { Requirement } from '../../../../../../../model/Requirement';
import { ModelFactoryBase } from '../../../../../../../factory/model-factory-base';
import { FolderFactory } from '../../../../../../../factory/folder-factory';
import { CEGModelFactory } from '../../../../../../../factory/ceg-model-factory';
import { ProcessFactory } from '../../../../../../../factory/process-factory';
import { Id } from '../../../../../../../util/id';
import { ViewControllerService } from '../../../../../controller/modules/view-controller/services/view-controller.service';

@Component({
    moduleId: module.id.toString(),
    selector: 'folder-details',
    templateUrl: 'folder-details.component.html',
    styleUrls: ['folder-details.component.css']
})

export class FolderDetails extends SpecmateViewBase {
    cegModelType = CEGModel;
    processModelType = Process;
    folderType = Folder;
    reqType = Requirement;

    /** Constructor */
    constructor(
        dataService: SpecmateDataService,
        navigator: NavigatorService,
        route: ActivatedRoute,
        modal: ConfirmationModal,
        translate: TranslateService,
        viewController: ViewControllerService) {
        super(dataService, navigator, route, modal, translate);
        this.viewController = viewController;
    }

    private viewController: ViewControllerService;
    private folder: Folder;
    private contents: IContainer[];
    // private transitiveContents: IContainer[];

    // private async updateTransitiveContent() {
    //     this.transitiveContents = [];
    //     if (this.contents) {
    //         for (const element of this.contents) {
    //             if (Type.is(element, this.folderType) || Type.is(element, this.reqType)) {
    //                 let subFolderContent = await this.dataService.readContents(element.url);
    //                 this.transitiveContents.push(...subFolderContent);
    //             } else {
    //                 this.transitiveContents.push(element);
    //             }
    //         }
    //     }
    // }

    protected onElementResolved(element: IContainer): void {
        // The timeout create a macrotask to prevent uncheckt update errors in Angular.


         setTimeout(() => {

        this.folder = element as Folder;
        this.dataService.readContents(this.folder.url)
            .then((contents: IContainer[]) => this.contents = contents);
        //               .then(() => this.updateTransitiveContent());
        });
    }

    public get cegModels(): CEGModel[] {
        if (!this.contents) {
            return [];
        }
        return <CEGModel[]>this.contents.filter((element: IContainer) => Type.is(element, this.cegModelType));
    }

    public get processModels(): Process[] {
        if (!this.contents) {
            return [];
        }
        return this.contents.filter((element: IContainer) => Type.is(element, this.processModelType));
    }

    public get subFolders(): Folder[] {
        if (!this.contents) {
            return [];
        }
        return this.contents
            .filter((element: IContainer) => Type.is(element, this.folderType))
            .map(container => <Folder>container);
    }

    public createFolder(): void {
         let factory: ModelFactoryBase = new FolderFactory(this.dataService);
         factory.create(this.folder, true).then((element: IContainer) => this.navigator.navigate(element));
    }

    public delete(element: IContainer): void {
      let msg: string;
      let msgPromise: Promise<string>;

      if (Type.is(element, Folder)) {
          msg = this.translate.instant('doYouReallyWantToDeleteFolder', {name: element.name});
      } else {
          msg = this.translate.instant('doYouReallyWantToDeleteAll', {name: element.name});
      }

      msgPromise = Promise.resolve(msg);
      msgPromise.then((msg: string) => this.modal.openOkCancel('ConfirmationRequired', msg)
          .then(() => this.dataService.deleteElement(element.url, true, Id.uuid))
          .then(() => this.dataService.commit(this.translate.instant('delete')))
          .then(() => this.dataService.readContents(this.folder.url, true))
          .then((contents: IContainer[]) => this.contents = contents)
          .catch(() => {}));
    }

    public createModel(): void {
        let factory: ModelFactoryBase = new CEGModelFactory(this.dataService);
        factory.create(this.folder, true).then((element: IContainer) => this.navigator.navigate(element));
    }

    public createProcess(): void {
        let factory: ModelFactoryBase = new ProcessFactory(this.dataService);
        factory.create(this.folder, true).then((element: IContainer) => this.navigator.navigate(element));
    }
    protected get isValid(): boolean {
        return true;
    }

    public get showFolderProperties(): boolean {
        return !this.viewController.areFolderPropertiesEditable;
    }
}
