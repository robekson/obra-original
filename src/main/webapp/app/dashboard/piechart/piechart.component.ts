import { Component, OnInit } from '@angular/core';
import { JhiLanguageService } from 'ng-jhipster';
import { Message } from 'primeng/components/common/api';
import { Subscription, Observable } from 'rxjs';
import { PiechartService } from './piechart.service';
import { IGasto, IResumoGasto, ITipoContaDto } from 'app/shared/model/gasto.model';
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
        .query({idObra: 954})
        .subscribe(
            (res: HttpResponse<ITipoContaDto[]>) => this.montaGraficoPizza(res.body, res.headers),
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        
        
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

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
