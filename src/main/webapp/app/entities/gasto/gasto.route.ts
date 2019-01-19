import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Gasto } from 'app/shared/model/gasto.model';
import { GastoService } from './gasto.service';
import { GastoComponent } from './gasto.component';
import { GastoDetailComponent } from './gasto-detail.component';
import { GastoUpdateComponent } from './gasto-update.component';
import { GastoDeletePopupComponent } from './gasto-delete-dialog.component';
import { IGasto } from 'app/shared/model/gasto.model';

@Injectable({ providedIn: 'root' })
export class GastoResolve implements Resolve<IGasto> {
    constructor(private service: GastoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Gasto> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Gasto>) => response.ok),
                map((gasto: HttpResponse<Gasto>) => gasto.body)
            );
        }
        return of(new Gasto());
    }
}

export const gastoRoute: Routes = [
    {
        path: 'gasto',
        component: GastoComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'obrasApp.gasto.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'gasto/:id/view',
        component: GastoDetailComponent,
        resolve: {
            gasto: GastoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'obrasApp.gasto.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'gasto/new',
        component: GastoUpdateComponent,
        resolve: {
            gasto: GastoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'obrasApp.gasto.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'gasto/:id/edit',
        component: GastoUpdateComponent,
        resolve: {
            gasto: GastoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'obrasApp.gasto.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const gastoPopupRoute: Routes = [
    {
        path: 'gasto/:id/delete',
        component: GastoDeletePopupComponent,
        resolve: {
            gasto: GastoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'obrasApp.gasto.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
