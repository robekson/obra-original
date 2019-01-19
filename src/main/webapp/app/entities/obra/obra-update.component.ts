import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IObra } from 'app/shared/model/obra.model';
import { ObraService } from './obra.service';
import { IResumoGasto } from 'app/shared/model/resumo-gasto.model';
import { ResumoGastoService } from 'app/entities/resumo-gasto';

@Component({
    selector: 'jhi-obra-update',
    templateUrl: './obra-update.component.html'
})
export class ObraUpdateComponent implements OnInit {
    obra: IObra;
    isSaving: boolean;

    resumogastos: IResumoGasto[];
    dataInicioDp: any;
    dataFimDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected obraService: ObraService,
        protected resumoGastoService: ResumoGastoService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ obra }) => {
            this.obra = obra;
        });
        this.resumoGastoService.query({ filter: 'obra-is-null' }).subscribe(
            (res: HttpResponse<IResumoGasto[]>) => {
                if (!this.obra.resumoGastoId) {
                    this.resumogastos = res.body;
                } else {
                    this.resumoGastoService.find(this.obra.resumoGastoId).subscribe(
                        (subRes: HttpResponse<IResumoGasto>) => {
                            this.resumogastos = [subRes.body].concat(res.body);
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
        if (this.obra.id !== undefined) {
            this.subscribeToSaveResponse(this.obraService.update(this.obra));
        } else {
            this.subscribeToSaveResponse(this.obraService.create(this.obra));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IObra>>) {
        result.subscribe((res: HttpResponse<IObra>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackResumoGastoById(index: number, item: IResumoGasto) {
        return item.id;
    }
}
