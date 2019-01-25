import { Component, OnInit } from '@angular/core';
import { JhiLanguageService } from 'ng-jhipster';
import { Message } from 'primeng/components/common/api';
import { Subscription, Observable } from 'rxjs';
import { LinechartService } from './linechart.service';
import { IResumoGasto } from 'app/shared/model/gasto.model';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

@Component({
    selector: 'jhi-linechart',
    templateUrl: './linechart.component.html',
    styles: []
})
export class LinechartComponent implements OnInit {
    data: any;
    msgs: Message[];

    constructor(protected lineChartService: LinechartService, protected jhiAlertService: JhiAlertService) {
        this.data = {};
    }

    ngOnInit() {
        this.lineChartService
            .query({})
            .subscribe(
                (res: HttpResponse<IResumoGasto[]>) => this.montaGrafico(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    protected montaGrafico(data: IResumoGasto[], headers: HttpHeaders) {
        console.log('data = ' + data);
        var v_json = {};
        var lineChartLabels = [];
        var lineChartDataSet = [];
        var dataValues = [];

        //data: [65, 59, 80, 81, 56, 55, 40],

        for (let resumoGasto of data) {
            lineChartLabels.push(resumoGasto.mesAnoFormatado);
            dataValues.push(resumoGasto.totalDespesas);
            console.log(resumoGasto.totalDespesas);
            console.log(resumoGasto.mesAnoFormatado);
        }

        v_json['labels'] = lineChartLabels;
        lineChartDataSet.push({
            label: 'Gastos',
            fill: false,
            data: dataValues,
            lineTension: 0.3,
            backgroundColor: 'rgba(2,117,216,0.2)',
            borderColor: 'rgba(2,117,216,1)',
            pointRadius: 5,
            pointBackgroundColor: 'rgba(2,117,216,1)',
            pointBorderColor: 'rgba(255,255,255,0.8)',
            pointHoverRadius: 5,
            pointHoverBackgroundColor: 'rgba(2,117,216,1)',
            pointHitRadius: 50,
            pointBorderWidth: 2
        });

        v_json['datasets'] = lineChartDataSet;
        this.data = v_json;
        console.log(JSON.stringify(v_json));
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    selectData(event) {
        this.msgs = [];
        this.msgs.push({
            severity: 'info',
            summary: 'Data Selected',
            detail: this.data.datasets[event.element._datasetIndex].data[event.element._index]
        });
    }
}
