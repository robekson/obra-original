import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILancamentoGastos } from 'app/shared/model/lancamento-gastos.model';

@Component({
    selector: 'jhi-lancamento-gastos-detail',
    templateUrl: './lancamento-gastos-detail.component.html'
})
export class LancamentoGastosDetailComponent implements OnInit {
    lancamentoGastos: ILancamentoGastos;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ lancamentoGastos }) => {
            this.lancamentoGastos = lancamentoGastos;
        });
    }

    previousState() {
        window.history.back();
    }
}
