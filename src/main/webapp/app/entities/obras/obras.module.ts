import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ObrasSharedModule } from 'app/shared';
import {
    ObrasComponent,
    ObrasDetailComponent,
    ObrasUpdateComponent,
    ObrasDeletePopupComponent,
    ObrasDeleteDialogComponent,
    obrasRoute,
    obrasPopupRoute
} from './';

const ENTITY_STATES = [...obrasRoute, ...obrasPopupRoute];

@NgModule({
    imports: [ObrasSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [ObrasComponent, ObrasDetailComponent, ObrasUpdateComponent, ObrasDeleteDialogComponent, ObrasDeletePopupComponent],
    entryComponents: [ObrasComponent, ObrasUpdateComponent, ObrasDeleteDialogComponent, ObrasDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ObrasObrasModule {}
