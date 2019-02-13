import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription, Observable } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { IObra } from 'app/shared/model/obra.model';
import { IGasto, IResumoGasto, MesAno } from 'app/shared/model/gasto.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { GastoService } from 'app/entities/gasto/gasto.service';

@Component({
    selector: 'jhi-resumo-total',
    templateUrl: './resumo-total.component.html'
})
export class ResumoTotalComponent implements OnInit, OnDestroy {
    currentAccount: any;
    gastos: IGasto[];
    mesesAno: MesAno[];
    resumo: IResumoGasto;
    error: any;
    success: any;
    eventSubscriber: Subscription;
    routeData: any;
    page: any;
    predicate: any;
    previousPage: any;
    nomeObra: any;

    constructor(
        protected gastoService: GastoService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected router: Router,
        protected eventManager: JhiEventManager
    ) {}
    ngOnInit() {}

    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        // this.eventManager.destroy(this.eventSubscriber);
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
