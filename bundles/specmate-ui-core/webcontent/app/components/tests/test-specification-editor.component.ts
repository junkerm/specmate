import {TestParameter} from '../../model/TestParameter';
import {TestCase} from '../../model/TestCase';
import {IContentElement} from '../../model/IContentElement';
import {TestSpecification} from '../../model/TestSpecification';
import {Url} from '../../util/Url';
import {Params, ActivatedRoute,  Router} from '@angular/router';
import {SpecmateDataService} from '../../services/specmate-data.service';
import {IContainer} from '../../model/IContainer';
import {Requirement} from '../../model/Requirement';
import {OnInit, Component} from '@angular/core';

@Component({
    moduleId: module.id,
    selector: 'test-specification-editor',
    templateUrl: 'test-specification-editor.component.html'
})

export class TestSpecificationEditor implements OnInit {

    private testSpecification: TestSpecification;
    private contents: IContentElement[];

    private testCaseType = TestCase;
    private parameterType = TestParameter;

    constructor(private dataService: SpecmateDataService, private router: Router, private route: ActivatedRoute) { }

    ngOnInit() {
        this.route.params
            .switchMap((params: Params) => this.dataService.readElement(Url.fromParams(params)))
            .subscribe((testSpec: IContainer) => {
                this.testSpecification = testSpec as TestSpecification;
                this.dataService.readContents(testSpec.url).then((
                    contents: IContainer[]) => {
                    this.contents = contents;
                });
            });
    }
}