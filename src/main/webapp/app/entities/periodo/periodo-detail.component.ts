import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPeriodo } from 'app/shared/model/periodo.model';

@Component({
    selector: 'jhi-periodo-detail',
    templateUrl: './periodo-detail.component.html'
})
export class PeriodoDetailComponent implements OnInit {
    periodo: IPeriodo;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ periodo }) => {
            this.periodo = periodo;
        });
    }

    previousState() {
        window.history.back();
    }
}
