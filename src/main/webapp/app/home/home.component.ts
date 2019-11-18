import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiParseLinks, JhiAlertService, JhiLanguageService } from 'ng-jhipster';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { LoginModalService, AccountService, Account } from 'app/core';
import { Message } from 'primeng/components/common/api';
import { Subscription, Observable } from 'rxjs';
import { IObra } from 'app/shared/model/obra.model';
import { IGasto, IResumoGasto, MesAno } from 'app/shared/model/gasto.model';
import { LinechartService } from 'app/dashboard/linechart/linechart.service';
import { GastoService } from 'app/entities/gasto/gasto.service';
import { NgxUiLoaderService } from 'ngx-ui-loader'; 

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: ['home.css']
})
export class HomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
    data: any;
    msgs: Message[];
    nomeObra: any;

    constructor(
        private accountService: AccountService,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        protected lineChartService: LinechartService,
        protected jhiAlertService: JhiAlertService,
        private ngxService: NgxUiLoaderService
    ) {
        this.data = {};
    }

    ngOnInit() {
        this.ngxService.start(); 
        this.accountService.identity().then(account => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();

        let id = localStorage.getItem('idObra');
        this.nomeObra = localStorage.getItem('nomeObra');

        this.lineChartService
            .query({ idObra: id })
            .subscribe(
                (res: HttpResponse<IResumoGasto[]>) => this.montaGraficoLinha(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        
        this.ngxService.stop();
    }

    registerAuthenticationSuccess() {
        this.ngxService.start(); 
        this.eventManager.subscribe('authenticationSuccess', message => {
            this.accountService.identity().then(account => {
                this.account = account;
            });
        });
        
        this.ngxService.stop();
    }

    isAuthenticated() {
        return this.accountService.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    protected montaGraficoLinha(data: IResumoGasto[], headers: HttpHeaders) {
        this.ngxService.start(); 
        console.log('data = ' + data);
        var v_json = {};
        var lineChartLabels = [];
        var lineChartDataSet = [];
        var dataValues = [];

        for (let resumoGasto of data) {
            lineChartLabels.push(resumoGasto.mesAnoFormatado);
            dataValues.push(resumoGasto.totalDespesas);
        }

        v_json['labels'] = lineChartLabels;
        lineChartDataSet.push({
            label: 'Gastos',
            // fill: false,
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
        this.ngxService.stop();
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
