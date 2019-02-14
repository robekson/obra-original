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
import { PiechartService } from 'app/dashboard/piechart/piechart.service';

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

    data: any;

    constructor(
        protected gastoService: GastoService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected router: Router,
        protected eventManager: JhiEventManager,
        protected pieChartService: PiechartService
    ) {
        this.data = {};
    }
    ngOnInit() {
        this.pieChartService
            .query({})
            .subscribe(
                (res: HttpResponse<IResumoGasto>) => this.montaGrafico(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );

        this.gastoService
            .resumoTotal({})
            .subscribe(
                (res: HttpResponse<IResumoGasto>) => this.montaGastos(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    protected montaGastos(data: IResumoGasto, headers: HttpHeaders) {
        this.resumo = data;
        console.log('Resumo =' + this.resumo);
    }

    previousState() {
        window.history.back();
    }

    protected montaGrafico(data: IResumoGasto, headers: HttpHeaders) {
        var v_json = {};

        var lineChartLabels = ['Com Nota', 'Sem Nota', 'Honorários'];
        var lineChartDataSet = [];
        var dataValues = [1, 2, 1];

        v_json['labels'] = lineChartLabels;
        lineChartDataSet.push({
            data: dataValues,
            backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56'],
            hoverBackgroundColor: ['#FF6384', '#36A2EB', '#FFCE56']
        });

        v_json['datasets'] = lineChartDataSet;
        this.data = v_json;
        console.log(JSON.stringify(v_json));
    }

    ngOnDestroy() {
        // this.eventManager.destroy(this.eventSubscriber);
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
