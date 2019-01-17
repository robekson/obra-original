import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';

import { IConta } from 'app/shared/model/conta.model';
import { ContaService } from './conta.service';

@Component({
    selector: 'jhi-conta-update',
    templateUrl: './conta-update.component.html'
})
export class ContaUpdateComponent implements OnInit {
    conta: IConta;
    isSaving: boolean;
    dataVencimentoDp: any;

    constructor(protected contaService: ContaService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ conta }) => {
            this.conta = conta;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.conta.id !== undefined) {
            this.subscribeToSaveResponse(this.contaService.update(this.conta));
        } else {
            this.subscribeToSaveResponse(this.contaService.create(this.conta));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IConta>>) {
        result.subscribe((res: HttpResponse<IConta>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
