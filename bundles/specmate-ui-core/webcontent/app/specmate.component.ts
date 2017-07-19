import { Component } from '@angular/core';

/**
 * This is the Specmate main component
 */
@Component({
    selector:'specmate',
    moduleId:module.id,
    templateUrl:'specmate.component.html',
    styleUrls:['specmate.component.css']
})

export class SpecmateComponent {
    public title: string = "Specmate";
}