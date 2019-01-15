import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IPeriodo } from 'app/shared/model/periodo.model';
import { PeriodoService } from './periodo.service';
import { IObras } from 'app/shared/model/obras.model';
import { ObrasService } from 'app/entities/obras';
import { IConta } from 'app/shared/model/conta.model';
import { ContaService } from 'app/entities/conta';

@Component({
    selector: 'jhi-periodo-update',
    templateUrl: './periodo-update.component.html'
})
export class PeriodoUpdateComponent implements OnInit {
    periodo: IPeriodo;
    isSaving: boolean;

    obras: IObras[];

    periodos: IConta[];
    data: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected periodoService: PeriodoService,
        protected obrasService: ObrasService,
        protected contaService: ContaService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ periodo }) => {
            this.periodo = periodo;
            this.data = this.periodo.data != null ? this.periodo.data.format(DATE_TIME_FORMAT) : null;
        });
        this.obrasService.query().subscribe(
            (res: HttpResponse<IObras[]>) => {
                this.obras = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.contaService.query({ filter: 'periodo-is-null' }).subscribe(
            (res: HttpResponse<IConta[]>) => {
                if (!this.periodo.periodoId) {
                    this.periodos = res.body;
                } else {
                    this.contaService.find(this.periodo.periodoId).subscribe(
                        (subRes: HttpResponse<IConta>) => {
                            this.periodos = [subRes.body].concat(res.body);
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
        this.periodo.data = this.data != null ? moment(this.data, DATE_TIME_FORMAT) : null;
        if (this.periodo.id !== undefined) {
            this.subscribeToSaveResponse(this.periodoService.update(this.periodo));
        } else {
            this.subscribeToSaveResponse(this.periodoService.create(this.periodo));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPeriodo>>) {
        result.subscribe((res: HttpResponse<IPeriodo>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackObrasById(index: number, item: IObras) {
        return item.id;
    }

    trackContaById(index: number, item: IConta) {
        return item.id;
    }
}
