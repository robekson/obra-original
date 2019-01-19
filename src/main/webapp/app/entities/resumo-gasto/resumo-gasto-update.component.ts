import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IResumoGasto } from 'app/shared/model/resumo-gasto.model';
import { ResumoGastoService } from './resumo-gasto.service';

@Component({
    selector: 'jhi-resumo-gasto-update',
    templateUrl: './resumo-gasto-update.component.html'
})
export class ResumoGastoUpdateComponent implements OnInit {
    resumoGasto: IResumoGasto;
    isSaving: boolean;

    constructor(protected resumoGastoService: ResumoGastoService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ resumoGasto }) => {
            this.resumoGasto = resumoGasto;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.resumoGasto.id !== undefined) {
            this.subscribeToSaveResponse(this.resumoGastoService.update(this.resumoGasto));
        } else {
            this.subscribeToSaveResponse(this.resumoGastoService.create(this.resumoGasto));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IResumoGasto>>) {
        result.subscribe((res: HttpResponse<IResumoGasto>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
