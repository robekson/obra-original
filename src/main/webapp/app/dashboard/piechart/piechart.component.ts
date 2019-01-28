import { Component, OnInit } from '@angular/core';
import { JhiLanguageService } from 'ng-jhipster';
import { Message } from 'primeng/components/common/api';
import { Subscription, Observable } from 'rxjs';
import { PiechartService } from './piechart.service';
import { IGasto, IResumoGasto } from 'app/shared/model/gasto.model';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

@Component({
    selector: 'jhi-piechart',
    templateUrl: './piechart.component.html',
    styles: []
})
export class PiechartComponent implements OnInit {
    data: any;

    constructor(protected pieChartService: PiechartService, protected jhiAlertService: JhiAlertService) {
        this.data = {};
    }

    ngOnInit() {
        this.pieChartService
            .query({})
            .subscribe(
                (res: HttpResponse<IResumoGasto>) => this.montaGrafico(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
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

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
