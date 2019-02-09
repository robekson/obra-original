import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Obra } from 'app/shared/model/obra.model';
import { ObraService } from './obra.service';
import { ObraComponent } from './obra.component';
import { ObraDetailComponent } from './obra-detail.component';
import { ObraUpdateComponent } from './obra-update.component';
import { ObraDeletePopupComponent } from './obra-delete-dialog.component';
import { IObra } from 'app/shared/model/obra.model';
import { GastoComponent } from 'app/entities/gasto/gasto.component';

@Injectable({ providedIn: 'root' })
export class ObraResolve implements Resolve<IObra> {
    constructor(private service: ObraService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Obra> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Obra>) => response.ok),
                map((obra: HttpResponse<Obra>) => obra.body)
            );
        }
        return of(new Obra());
    }
}

export const obraRoute: Routes = [
    {
        path: 'obra',
        component: ObraComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'obrasApp.obra.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'gasto/:id/:nome/view',
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
        path: 'obra/:id/view',
        component: ObraDetailComponent,
        resolve: {
            obra: ObraResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'obrasApp.obra.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'obra/new',
        component: ObraUpdateComponent,
        resolve: {
            obra: ObraResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'obrasApp.obra.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'obra/:id/edit',
        component: ObraUpdateComponent,
        resolve: {
            obra: ObraResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'obrasApp.obra.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const obraPopupRoute: Routes = [
    {
        path: 'obra/:id/delete',
        component: ObraDeletePopupComponent,
        resolve: {
            obra: ObraResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'obrasApp.obra.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
