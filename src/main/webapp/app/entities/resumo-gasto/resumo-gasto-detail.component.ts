import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IResumoGasto } from 'app/shared/model/resumo-gasto.model';

@Component({
    selector: 'jhi-resumo-gasto-detail',
    templateUrl: './resumo-gasto-detail.component.html'
})
export class ResumoGastoDetailComponent implements OnInit {
    resumoGasto: IResumoGasto;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ resumoGasto }) => {
            this.resumoGasto = resumoGasto;
        });
    }

    previousState() {
        window.history.back();
    }
}
