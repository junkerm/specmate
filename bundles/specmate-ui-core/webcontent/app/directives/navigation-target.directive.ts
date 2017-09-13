import { Directive, ElementRef, Input, HostListener } from '@angular/core';
import { IContainer } from "../model/IContainer";
import { NavigatorService } from "../services/navigation/navigator.service";

@Directive({ selector: '[navigationTarget]' })
export class NavigationTargetDirective {

    @Input('navigationTarget') target: IContainer;

    @HostListener('click', ['$event'])
    onClick(e: Event) {
        e.preventDefault();
        this.navigatorService.navigate(this.target);
    }

    constructor(private elementRef: ElementRef, private navigatorService: NavigatorService) {
        elementRef.nativeElement.href = '';
        elementRef.nativeElement.title = 'Navigate to ' + this.target.name;
    }
}