import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ObrasSharedModule } from 'app/shared';
import {
    ContaComponent,
    ContaDetailComponent,
    ContaUpdateComponent,
    ContaDeletePopupComponent,
    ContaDeleteDialogComponent,
    contaRoute,
    contaPopupRoute
} from './';

const ENTITY_STATES = [...contaRoute, ...contaPopupRoute];

@NgModule({
    imports: [ObrasSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [ContaComponent, ContaDetailComponent, ContaUpdateComponent, ContaDeleteDialogComponent, ContaDeletePopupComponent],
    entryComponents: [ContaComponent, ContaUpdateComponent, ContaDeleteDialogComponent, ContaDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ObrasContaModule {}
