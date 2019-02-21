import { Component, OnInit, OnDestroy, ElementRef, ViewChild } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription, Observable } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { IObra } from 'app/shared/model/obra.model';
import { IGasto, IResumoGasto, MesAno, ITipoContaDto } from 'app/shared/model/gasto.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { GastoService } from 'app/entities/gasto/gasto.service';
import { PiechartService } from 'app/dashboard/piechart/piechart.service';
import { BarchartService } from 'app/dashboard/barchart/barchart.service';

import * as jspdf from 'jspdf';
import * as html2canvas from 'html2canvas';

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
    dataBar: any;

    constructor(
        protected gastoService: GastoService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected router: Router,
        protected eventManager: JhiEventManager,
        protected pieChartService: PiechartService,
        protected barChartService: BarchartService
    ) {
        this.data = {};
    }
    ngOnInit() {
        let id = localStorage.getItem('idObra');
        this.nomeObra = localStorage.getItem('nomeObra');

        this.pieChartService
            .query({ idObra: id })
            .subscribe(
                (res: HttpResponse<ITipoContaDto[]>) => this.montaGraficoPizza(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );

        this.barChartService
            .query({ idObra: id })
            .subscribe(
                (res: HttpResponse<IResumoGasto[]>) => this.montaGraficoBarra(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );

        this.gastoService
            .resumoTotal({ idObra: id })
            .subscribe(
                (res: HttpResponse<IResumoGasto>) => this.montaGastos(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    protected montaGastos(data: IResumoGasto, headers: HttpHeaders) {
        this.resumo = data;
        console.log('Resumo =' + this.resumo);
    }

    public exportar() {
        var data = document.getElementById('contentToConvert');
        console.log('exportar =' + data);
        html2canvas(data).then(canvas => {
            // Few necessary setting options
            var imgWidth = 200;
            var pageHeight = 695;
            var imgHeight = (canvas.height * imgWidth) / canvas.width;
            var heightLeft = imgHeight;

            const contentDataURL = canvas.toDataURL('image/png');
            let pdf = new jspdf('p', 'mm', 'a4'); // A4 size page of PDF
            var position = 0;
            pdf.addImage(contentDataURL, 'PNG', 2, position, imgWidth, imgHeight);
            pdf.save('resumoTotal.pdf'); // Generated PDF
        });
    }

    previousState() {
        window.history.back();
    }

    protected montaGraficoPizza(data: ITipoContaDto[], headers: HttpHeaders) {
        console.log(' montaGraficoPizza = ' + data);
        var v_json = {};

        var pieChartLabels = [];
        var pieChartDataSet = [];
        var dataValues = [];

        for (let tipoConta of data) {
            pieChartLabels.push(tipoConta.descricao);
            dataValues.push(tipoConta.valorDespesa);
        }

        v_json['labels'] = pieChartLabels;
        pieChartDataSet.push({
            data: dataValues,
            backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56'],
            hoverBackgroundColor: ['#FF6384', '#36A2EB', '#FFCE56']
        });

        v_json['datasets'] = pieChartDataSet;
        this.data = v_json;
        console.log(JSON.stringify(v_json));
    }

    protected montaGraficoBarra(data: IResumoGasto[], headers: HttpHeaders) {
        console.log(' montaGraficoBarra = ' + data);
        var v_json = {};
        var barChartLabels = [];
        var barChartDataSet = [];
        var dataValues = [];

        for (let resumoGasto of data) {
            barChartLabels.push(resumoGasto.mesAnoFormatado);
            dataValues.push(resumoGasto.totalDespesas);
        }

        v_json['labels'] = barChartLabels;
        barChartDataSet.push({
            label: 'Gastos - ' + this.nomeObra,
            backgroundColor: '#42A5F5',
            borderColor: '#1E88E5',
            data: dataValues
        });

        v_json['datasets'] = barChartDataSet;
        this.dataBar = v_json;
        console.log(JSON.stringify(v_json));
    }

    ngOnDestroy() {
        // this.eventManager.destroy(this.eventSubscriber);
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
