import { Directive, ElementRef, HostListener, Input, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { IContainer } from '../../../../../model/IContainer';
import { NavigatorService } from '../services/navigator.service';

@Directive({ selector: '[navigationTarget]' })
export class NavigationTargetDirective implements OnInit {

    @Input('navigationTarget') target: IContainer;

    @HostListener('click', ['$event'])
    onClick(e: Event) {
        e.preventDefault();
        this.navigatorService.navigate(this.target);
    }

    constructor(private elementRef: ElementRef, private navigatorService: NavigatorService, private translate: TranslateService) {
        elementRef.nativeElement.href = '';
        translate.onLangChange.subscribe(() => this.setTooltip());
    }

    ngOnInit() {
        this.setTooltip();
    }

    private setTooltip(): void {
        if (this.target) {
            this.elementRef.nativeElement.title = this.translate.instant('navigateTo', { name: this.target.name });
        }
    }
}
