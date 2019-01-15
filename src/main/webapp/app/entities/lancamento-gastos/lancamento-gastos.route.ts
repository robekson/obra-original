import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { LancamentoGastos } from 'app/shared/model/lancamento-gastos.model';
import { LancamentoGastosService } from './lancamento-gastos.service';
import { LancamentoGastosComponent } from './lancamento-gastos.component';
import { LancamentoGastosDetailComponent } from './lancamento-gastos-detail.component';
import { LancamentoGastosUpdateComponent } from './lancamento-gastos-update.component';
import { LancamentoGastosDeletePopupComponent } from './lancamento-gastos-delete-dialog.component';
import { ILancamentoGastos } from 'app/shared/model/lancamento-gastos.model';

@Injectable({ providedIn: 'root' })
export class LancamentoGastosResolve implements Resolve<ILancamentoGastos> {
    constructor(private service: LancamentoGastosService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<LancamentoGastos> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<LancamentoGastos>) => response.ok),
                map((lancamentoGastos: HttpResponse<LancamentoGastos>) => lancamentoGastos.body)
            );
        }
        return of(new LancamentoGastos());
    }
}

export const lancamentoGastosRoute: Routes = [
    {
        path: 'lancamento-gastos',
        component: LancamentoGastosComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'obrasApp.lancamentoGastos.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'lancamento-gastos/:id/view',
        component: LancamentoGastosDetailComponent,
        resolve: {
            lancamentoGastos: LancamentoGastosResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'obrasApp.lancamentoGastos.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'lancamento-gastos/new',
        component: LancamentoGastosUpdateComponent,
        resolve: {
            lancamentoGastos: LancamentoGastosResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'obrasApp.lancamentoGastos.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'lancamento-gastos/:id/edit',
        component: LancamentoGastosUpdateComponent,
        resolve: {
            lancamentoGastos: LancamentoGastosResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'obrasApp.lancamentoGastos.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const lancamentoGastosPopupRoute: Routes = [
    {
        path: 'lancamento-gastos/:id/delete',
        component: LancamentoGastosDeletePopupComponent,
        resolve: {
            lancamentoGastos: LancamentoGastosResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'obrasApp.lancamentoGastos.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
