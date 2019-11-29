import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ObrasSharedModule } from 'app/shared';
import { FormsModule } from "@angular/forms";
import { NgxCurrencyModule } from "ngx-currency";
import { NgxMaskModule } from 'ngx-mask'

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

export const customCurrencyMaskConfig = {
        align: "right",
        allowNegative: true,
        allowZero: true,
        decimal: ",",
        precision: 2,
        prefix: "R$ ",
        suffix: "",
        thousands: ".",
        nullable: true
    };

@NgModule({
    imports: [ObrasSharedModule, RouterModule.forChild(ENTITY_STATES),FormsModule,  NgxMaskModule.forRoot(),NgxCurrencyModule.forRoot(customCurrencyMaskConfig) ],
    declarations: [ObraComponent, ObraDetailComponent, ObraUpdateComponent, ObraDeleteDialogComponent, ObraDeletePopupComponent],
    entryComponents: [ObraComponent, ObraUpdateComponent, ObraDeleteDialogComponent, ObraDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ObrasObraModule {}
