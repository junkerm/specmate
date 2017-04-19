import {ParameterAssignment} from '../../model/ParameterAssignment';
import {IContentElement} from '../../model/IContentElement';
import {TestParameter} from '../../model/TestParameter';
import {TestCase} from '../../model/TestCase';
import {SpecmateDataService} from '../../services/specmate-data.service';
import {OnInit, Component, Input} from '@angular/core';
import {Params, ActivatedRoute,  Router} from '@angular/router';
import {Url} from '../../util/Url';
import {IContainer} from '../../model/IContainer';

@Component({
    moduleId: module.id,
    selector: 'test-case-row',
    templateUrl: 'test-case-row.component.html'
})

export class TestCaseRow implements OnInit {

    private testCase : TestCase;

    @Input()
    private parameters: TestParameter[];

    private assignments: ParameterAssignment[];

    private contents: IContentElement[];

    private testCaseType = TestCase;

    constructor(private dataService: SpecmateDataService, private router: Router, private route: ActivatedRoute) { }

    ngOnInit() {
        this.route.params
            .switchMap((params: Params) => this.dataService.readElement(Url.fromParams(params)))
            .subscribe((testCase: IContainer) => {
                this.testCase = testCase as TestCase;
                this.dataService.readContents(testCase.url).then((
                    contents: IContainer[]) => {
                    this.contents = contents;

                });
            });
        
    }

    private deriveAsignments(contents: IContentElement){
        
    }
}