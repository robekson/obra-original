import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Obras } from 'app/shared/model/obras.model';
import { ObrasService } from './obras.service';
import { ObrasComponent } from './obras.component';
import { ObrasDetailComponent } from './obras-detail.component';
import { ObrasUpdateComponent } from './obras-update.component';
import { ObrasDeletePopupComponent } from './obras-delete-dialog.component';
import { IObras } from 'app/shared/model/obras.model';

@Injectable({ providedIn: 'root' })
export class ObrasResolve implements Resolve<IObras> {
    constructor(private service: ObrasService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Obras> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Obras>) => response.ok),
                map((obras: HttpResponse<Obras>) => obras.body)
            );
        }
        return of(new Obras());
    }
}

export const obrasRoute: Routes = [
    {
        path: 'obras',
        component: ObrasComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'obrasApp.obras.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'obras/:id/view',
        component: ObrasDetailComponent,
        resolve: {
            obras: ObrasResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'obrasApp.obras.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'obras/new',
        component: ObrasUpdateComponent,
        resolve: {
            obras: ObrasResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'obrasApp.obras.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'obras/:id/edit',
        component: ObrasUpdateComponent,
        resolve: {
            obras: ObrasResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'obrasApp.obras.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const obrasPopupRoute: Routes = [
    {
        path: 'obras/:id/delete',
        component: ObrasDeletePopupComponent,
        resolve: {
            obras: ObrasResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'obrasApp.obras.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
