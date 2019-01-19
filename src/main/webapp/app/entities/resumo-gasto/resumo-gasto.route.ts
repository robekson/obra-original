import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ResumoGasto } from 'app/shared/model/resumo-gasto.model';
import { ResumoGastoService } from './resumo-gasto.service';
import { ResumoGastoComponent } from './resumo-gasto.component';
import { ResumoGastoDetailComponent } from './resumo-gasto-detail.component';
import { ResumoGastoUpdateComponent } from './resumo-gasto-update.component';
import { ResumoGastoDeletePopupComponent } from './resumo-gasto-delete-dialog.component';
import { IResumoGasto } from 'app/shared/model/resumo-gasto.model';

@Injectable({ providedIn: 'root' })
export class ResumoGastoResolve implements Resolve<IResumoGasto> {
    constructor(private service: ResumoGastoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ResumoGasto> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ResumoGasto>) => response.ok),
                map((resumoGasto: HttpResponse<ResumoGasto>) => resumoGasto.body)
            );
        }
        return of(new ResumoGasto());
    }
}

export const resumoGastoRoute: Routes = [
    {
        path: 'resumo-gasto',
        component: ResumoGastoComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'obrasApp.resumoGasto.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'resumo-gasto/:id/view',
        component: ResumoGastoDetailComponent,
        resolve: {
            resumoGasto: ResumoGastoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'obrasApp.resumoGasto.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'resumo-gasto/new',
        component: ResumoGastoUpdateComponent,
        resolve: {
            resumoGasto: ResumoGastoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'obrasApp.resumoGasto.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'resumo-gasto/:id/edit',
        component: ResumoGastoUpdateComponent,
        resolve: {
            resumoGasto: ResumoGastoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'obrasApp.resumoGasto.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const resumoGastoPopupRoute: Routes = [
    {
        path: 'resumo-gasto/:id/delete',
        component: ResumoGastoDeletePopupComponent,
        resolve: {
            resumoGasto: ResumoGastoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'obrasApp.resumoGasto.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
