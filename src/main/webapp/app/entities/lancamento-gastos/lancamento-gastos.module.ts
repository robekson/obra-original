import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ObrasSharedModule } from 'app/shared';
import {
    LancamentoGastosComponent,
    LancamentoGastosDetailComponent,
    LancamentoGastosUpdateComponent,
    LancamentoGastosDeletePopupComponent,
    LancamentoGastosDeleteDialogComponent,
    lancamentoGastosRoute,
    lancamentoGastosPopupRoute
} from './';

const ENTITY_STATES = [...lancamentoGastosRoute, ...lancamentoGastosPopupRoute];

@NgModule({
    imports: [ObrasSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        LancamentoGastosComponent,
        LancamentoGastosDetailComponent,
        LancamentoGastosUpdateComponent,
        LancamentoGastosDeleteDialogComponent,
        LancamentoGastosDeletePopupComponent
    ],
    entryComponents: [
        LancamentoGastosComponent,
        LancamentoGastosUpdateComponent,
        LancamentoGastosDeleteDialogComponent,
        LancamentoGastosDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ObrasLancamentoGastosModule {}
