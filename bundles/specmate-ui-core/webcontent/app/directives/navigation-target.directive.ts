import { Directive, ElementRef, Input, HostListener, OnInit } from '@angular/core';
import { IContainer } from "../model/IContainer";
import { NavigatorService } from "../services/navigation/navigator.service";

@Directive({ selector: '[navigationTarget]' })
export class NavigationTargetDirective implements OnInit {

    @Input('navigationTarget') target: IContainer;

    @HostListener('click', ['$event'])
    onClick(e: Event) {
        e.preventDefault();
        this.navigatorService.navigate(this.target);
    }

    constructor(private elementRef: ElementRef, private navigatorService: NavigatorService) {
        elementRef.nativeElement.href = '';
    }

    ngOnInit() {
        if(this.target) {
            this.elementRef.nativeElement.title = 'Navigate to ' + this.target.name;
        }
    }
}