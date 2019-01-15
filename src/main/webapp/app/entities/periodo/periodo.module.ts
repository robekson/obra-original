import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ObrasSharedModule } from 'app/shared';
import {
    PeriodoComponent,
    PeriodoDetailComponent,
    PeriodoUpdateComponent,
    PeriodoDeletePopupComponent,
    PeriodoDeleteDialogComponent,
    periodoRoute,
    periodoPopupRoute
} from './';

const ENTITY_STATES = [...periodoRoute, ...periodoPopupRoute];

@NgModule({
    imports: [ObrasSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PeriodoComponent,
        PeriodoDetailComponent,
        PeriodoUpdateComponent,
        PeriodoDeleteDialogComponent,
        PeriodoDeletePopupComponent
    ],
    entryComponents: [PeriodoComponent, PeriodoUpdateComponent, PeriodoDeleteDialogComponent, PeriodoDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ObrasPeriodoModule {}
