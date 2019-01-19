import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ObrasSharedModule } from 'app/shared';
import {
    ObraComponent,
    ObraDetailComponent,
    ObraUpdateComponent,
    ObraDeletePopupComponent,
    ObraDeleteDialogComponent,
    obraRoute,
    obraPopupRoute
} from './';

const ENTITY_STATES = [...obraRoute, ...obraPopupRoute];

@NgModule({
    imports: [ObrasSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [ObraComponent, ObraDetailComponent, ObraUpdateComponent, ObraDeleteDialogComponent, ObraDeletePopupComponent],
    entryComponents: [ObraComponent, ObraUpdateComponent, ObraDeleteDialogComponent, ObraDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ObrasObraModule {}
