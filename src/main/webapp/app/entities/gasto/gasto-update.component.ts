import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import 'moment/locale/pt-br';
import { JhiAlertService } from 'ng-jhipster';

import { IGasto, Pago } from 'app/shared/model/gasto.model';
import { GastoService } from './gasto.service';
import { IObra } from 'app/shared/model/obra.model';
import { ObraService } from 'app/entities/obra';

@Component({
    selector: 'jhi-gasto-update',
    templateUrl: './gasto-update.component.html'
})
export class GastoUpdateComponent implements OnInit {
    gasto: IGasto;
    isSaving: boolean;

    obras: IObra[];
    dataVencimentoDp: any;
    mesAnoDp: any;
    nomeObra: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected gastoService: GastoService,
        protected obraService: ObraService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.nomeObra = localStorage.getItem('nomeObra');
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ gasto }) => {
            this.gasto = gasto;
            let dateString = localStorage.getItem('data');
            if (dateString != null && this.gasto.mesAno == null) {
                this.gasto.mesAno = moment(dateString, 'MMM/YYYY', 'pt-BR');
            }

            let id = localStorage.getItem('idObra');
            this.gasto.obraId = parseInt(id);
        });
        this.obraService.query().subscribe(
            (res: HttpResponse<IObra[]>) => {
                this.obras = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.gasto.id !== undefined) {
            this.subscribeToSaveResponse(this.gastoService.update(this.gasto));
        } else {
            this.subscribeToSaveResponse(this.gastoService.create(this.gasto));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IGasto>>) {
        result.subscribe((res: HttpResponse<IGasto>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    tipoContaChanged(event) {
        if (event == 'INVESTIMENTO_DEPOSITO') {
            this.gasto.pagamento = Pago.SIM;
        }
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

    trackObraById(index: number, item: IObra) {
        return item.id;
    }
}
