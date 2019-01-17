import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IObras } from 'app/shared/model/obras.model';
import { ObrasService } from './obras.service';
import { ILancamentoGastos } from 'app/shared/model/lancamento-gastos.model';
import { LancamentoGastosService } from 'app/entities/lancamento-gastos';

@Component({
    selector: 'jhi-obras-update',
    templateUrl: './obras-update.component.html'
})
export class ObrasUpdateComponent implements OnInit {
    obras: IObras;
    isSaving: boolean;

    lancamentogastos: ILancamentoGastos[];
    dataInicioDp: any;
    dataFimDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected obrasService: ObrasService,
        protected lancamentoGastosService: LancamentoGastosService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ obras }) => {
            this.obras = obras;
        });
        this.lancamentoGastosService.query().subscribe(
            (res: HttpResponse<ILancamentoGastos[]>) => {
                this.lancamentogastos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.obras.id !== undefined) {
            this.subscribeToSaveResponse(this.obrasService.update(this.obras));
        } else {
            this.subscribeToSaveResponse(this.obrasService.create(this.obras));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IObras>>) {
        result.subscribe((res: HttpResponse<IObras>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackLancamentoGastosById(index: number, item: ILancamentoGastos) {
        return item.id;
    }
}
