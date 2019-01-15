import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ILancamentoGastos } from 'app/shared/model/lancamento-gastos.model';
import { LancamentoGastosService } from './lancamento-gastos.service';

@Component({
    selector: 'jhi-lancamento-gastos-update',
    templateUrl: './lancamento-gastos-update.component.html'
})
export class LancamentoGastosUpdateComponent implements OnInit {
    lancamentoGastos: ILancamentoGastos;
    isSaving: boolean;

    constructor(protected lancamentoGastosService: LancamentoGastosService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ lancamentoGastos }) => {
            this.lancamentoGastos = lancamentoGastos;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.lancamentoGastos.id !== undefined) {
            this.subscribeToSaveResponse(this.lancamentoGastosService.update(this.lancamentoGastos));
        } else {
            this.subscribeToSaveResponse(this.lancamentoGastosService.create(this.lancamentoGastos));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ILancamentoGastos>>) {
        result.subscribe((res: HttpResponse<ILancamentoGastos>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
