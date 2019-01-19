import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ObrasSharedModule } from 'app/shared';
import {
    GastoComponent,
    GastoDetailComponent,
    GastoUpdateComponent,
    GastoDeletePopupComponent,
    GastoDeleteDialogComponent,
    gastoRoute,
    gastoPopupRoute
} from './';

const ENTITY_STATES = [...gastoRoute, ...gastoPopupRoute];

@NgModule({
    imports: [ObrasSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [GastoComponent, GastoDetailComponent, GastoUpdateComponent, GastoDeleteDialogComponent, GastoDeletePopupComponent],
    entryComponents: [GastoComponent, GastoUpdateComponent, GastoDeleteDialogComponent, GastoDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ObrasGastoModule {}
