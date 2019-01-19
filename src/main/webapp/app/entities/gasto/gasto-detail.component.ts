import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGasto } from 'app/shared/model/gasto.model';

@Component({
    selector: 'jhi-gasto-detail',
    templateUrl: './gasto-detail.component.html'
})
export class GastoDetailComponent implements OnInit {
    gasto: IGasto;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ gasto }) => {
            this.gasto = gasto;
        });
    }

    previousState() {
        window.history.back();
    }
}
