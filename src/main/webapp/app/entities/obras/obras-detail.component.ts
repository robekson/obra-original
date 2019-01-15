import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IObras } from 'app/shared/model/obras.model';

@Component({
    selector: 'jhi-obras-detail',
    templateUrl: './obras-detail.component.html'
})
export class ObrasDetailComponent implements OnInit {
    obras: IObras;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ obras }) => {
            this.obras = obras;
        });
    }

    previousState() {
        window.history.back();
    }
}
