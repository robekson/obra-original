import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription, Observable } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { IObra } from 'app/shared/model/obra.model';
import { IGasto, IResumoGasto, MesAno } from 'app/shared/model/gasto.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { GastoService } from './gasto.service';

@Component({
    selector: 'jhi-gasto',
    templateUrl: './gasto.component.html'
})
export class GastoComponent implements OnInit, OnDestroy {
    currentAccount: any;
    gastos: IGasto[];
    mesesAno: MesAno[];
    resumo: IResumoGasto;
    error: any;
    success: any;
    eventSubscriber: Subscription;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;
    nomeObra: any;

    constructor(
        protected gastoService: GastoService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected router: Router,
        protected eventManager: JhiEventManager
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe(data => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
        });
    }

    loadAll() {
        let param = localStorage.getItem('data');
        let id = localStorage.getItem('idObra');
        if (param !== null) {
            this.gastoService
                .query({
                    page: this.page - 1,
                    size: this.itemsPerPage,
                    sort: this.sort(),
                    data: param,
                    idObra: id
                })
                .subscribe(
                    (res: HttpResponse<IGasto[]>) => this.paginateGastos(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );

            this.gastoService
                .resumo({
                    page: this.page - 1,
                    size: this.itemsPerPage,
                    sort: this.sort(),
                    data: param,
                    idObra: id
                })
                .subscribe(
                    (res: HttpResponse<IResumoGasto>) => this.montaGastos(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        }
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    selecionaData(param: any) {
        console.log(param);
        localStorage.setItem('data', param);
        let id = localStorage.getItem('idObra');
        this.nomeObra = localStorage.getItem('nomeObra');
        this.gastoService
            .query({
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort(),
                data: param,
                idObra: id
            })
            .subscribe(
                (res: HttpResponse<IGasto[]>) => this.paginateGastos(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );

        this.gastoService
            .resumo({
                data: param,
                idObra: id
            })
            .subscribe(
                (res: HttpResponse<IResumoGasto>) => this.montaGastos(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    transition() {
        let param = localStorage.getItem('data');
        if (param !== null) {
            this.router.navigate(['/gasto'], {
                queryParams: {
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
                }
            });
        }
        this.loadAll();
    }

    clear() {
        this.page = 0;
        this.router.navigate([
            '/gasto',
            {
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        ]);
        this.loadAll();
    }

    ngOnInit() {
        const idObra = this.activatedRoute.snapshot.paramMap.get('id');
        if (idObra !== null) {
            localStorage.setItem('idObra', idObra);
            this.nomeObra = this.activatedRoute.snapshot.paramMap.get('nome');
            localStorage.setItem('nomeObra', this.nomeObra);
        }
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInGastos();

        this.gastoService
            .findMesesAno({})
            .subscribe(
                (res: HttpResponse<MesAno[]>) => this.montaMeses(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IGasto) {
        return item.id;
    }

    registerChangeInGastos() {
        this.eventSubscriber = this.eventManager.subscribe('gastoListModification', response => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    protected paginateGastos(data: IGasto[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.queryCount = this.totalItems;
        this.gastos = data;
    }

    protected montaGastos(data: IResumoGasto, headers: HttpHeaders) {
        this.resumo = data;
        console.log('Resumo =' + this.resumo);
    }

    protected montaMeses(data: MesAno[], headers: HttpHeaders) {
        this.mesesAno = data;
        console.log('Meses Ano =' + this.mesesAno);
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
