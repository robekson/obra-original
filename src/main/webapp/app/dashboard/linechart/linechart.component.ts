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
        this.data = {
            labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
            datasets: [
                {
                    label: 'First Dataset',
                    data: [65, 59, 80, 81, 56, 55, 40],
                    fill: false,
                    borderColor: '#4bc0c0'
                },
                {
                    label: 'Second Dataset',
                    data: [28, 48, 40, 19, 86, 27, 90],
                    fill: false,
                    borderColor: '#565656'
                }
            ]
        };
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
        //this.resumo = data;
        console.log('data = ' + data);      
        for (let resumoGasto of data) {
            console.log(resumoGasto.totalDespesas); 
            console.log(resumoGasto.mesAnoFormatadoExtenso); 
        }
        
      
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
