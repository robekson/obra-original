import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPeriodo } from 'app/shared/model/periodo.model';
import { AccountService } from 'app/core';
import { PeriodoService } from './periodo.service';

@Component({
    selector: 'jhi-periodo',
    templateUrl: './periodo.component.html'
})
export class PeriodoComponent implements OnInit, OnDestroy {
    periodos: IPeriodo[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected periodoService: PeriodoService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.periodoService.query().subscribe(
            (res: HttpResponse<IPeriodo[]>) => {
                this.periodos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPeriodos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPeriodo) {
        return item.id;
    }

    registerChangeInPeriodos() {
        this.eventSubscriber = this.eventManager.subscribe('periodoListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
