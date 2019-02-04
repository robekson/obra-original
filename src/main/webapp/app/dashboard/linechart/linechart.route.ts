import { Route } from '@angular/router';
import { UserRouteAccessService } from '../../core';
import { LinechartComponent } from './linechart.component';

export const linechartRoute: Route = {
   // path: 'linechart',
    path: '',
    component: LinechartComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'dashboard.linechart.home.title'
    },
    canActivate: [UserRouteAccessService]
};
