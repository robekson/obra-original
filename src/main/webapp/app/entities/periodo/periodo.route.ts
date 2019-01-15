import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Periodo } from 'app/shared/model/periodo.model';
import { PeriodoService } from './periodo.service';
import { PeriodoComponent } from './periodo.component';
import { PeriodoDetailComponent } from './periodo-detail.component';
import { PeriodoUpdateComponent } from './periodo-update.component';
import { PeriodoDeletePopupComponent } from './periodo-delete-dialog.component';
import { IPeriodo } from 'app/shared/model/periodo.model';

@Injectable({ providedIn: 'root' })
export class PeriodoResolve implements Resolve<IPeriodo> {
    constructor(private service: PeriodoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Periodo> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Periodo>) => response.ok),
                map((periodo: HttpResponse<Periodo>) => periodo.body)
            );
        }
        return of(new Periodo());
    }
}

export const periodoRoute: Routes = [
    {
        path: 'periodo',
        component: PeriodoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'obrasApp.periodo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'periodo/:id/view',
        component: PeriodoDetailComponent,
        resolve: {
            periodo: PeriodoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'obrasApp.periodo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'periodo/new',
        component: PeriodoUpdateComponent,
        resolve: {
            periodo: PeriodoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'obrasApp.periodo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'periodo/:id/edit',
        component: PeriodoUpdateComponent,
        resolve: {
            periodo: PeriodoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'obrasApp.periodo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const periodoPopupRoute: Routes = [
    {
        path: 'periodo/:id/delete',
        component: PeriodoDeletePopupComponent,
        resolve: {
            periodo: PeriodoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'obrasApp.periodo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
