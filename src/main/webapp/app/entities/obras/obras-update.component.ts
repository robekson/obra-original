import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
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

    obrasCollection: ILancamentoGastos[];
    dataInicio: string;
    dataFim: string;

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
            this.dataInicio = this.obras.dataInicio != null ? this.obras.dataInicio.format(DATE_TIME_FORMAT) : null;
            this.dataFim = this.obras.dataFim != null ? this.obras.dataFim.format(DATE_TIME_FORMAT) : null;
        });
        this.lancamentoGastosService.query({ filter: 'obras-is-null' }).subscribe(
            (res: HttpResponse<ILancamentoGastos[]>) => {
                if (!this.obras.obraId) {
                    this.obrasCollection = res.body;
                } else {
                    this.lancamentoGastosService.find(this.obras.obraId).subscribe(
                        (subRes: HttpResponse<ILancamentoGastos>) => {
                            this.obrasCollection = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.obras.dataInicio = this.dataInicio != null ? moment(this.dataInicio, DATE_TIME_FORMAT) : null;
        this.obras.dataFim = this.dataFim != null ? moment(this.dataFim, DATE_TIME_FORMAT) : null;
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
