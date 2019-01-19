import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ObrasSharedModule } from 'app/shared';
import {
    ResumoGastoComponent,
    ResumoGastoDetailComponent,
    ResumoGastoUpdateComponent,
    ResumoGastoDeletePopupComponent,
    ResumoGastoDeleteDialogComponent,
    resumoGastoRoute,
    resumoGastoPopupRoute
} from './';

const ENTITY_STATES = [...resumoGastoRoute, ...resumoGastoPopupRoute];

@NgModule({
    imports: [ObrasSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ResumoGastoComponent,
        ResumoGastoDetailComponent,
        ResumoGastoUpdateComponent,
        ResumoGastoDeleteDialogComponent,
        ResumoGastoDeletePopupComponent
    ],
    entryComponents: [ResumoGastoComponent, ResumoGastoUpdateComponent, ResumoGastoDeleteDialogComponent, ResumoGastoDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ObrasResumoGastoModule {}
