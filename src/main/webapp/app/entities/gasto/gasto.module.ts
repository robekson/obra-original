import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ObrasSharedModule } from 'app/shared';
import { NgxCurrencyModule } from "ngx-currency";

import {
    GastoComponent,
    GastoDetailComponent,
    GastoUpdateComponent,
    GastoDeletePopupComponent,
    GastoDeleteDialogComponent,
    gastoRoute,
    gastoPopupRoute
} from './';
import { FormsModule } from "@angular/forms";

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

const ENTITY_STATES = [...gastoRoute, ...gastoPopupRoute];

@NgModule({
    imports: [ObrasSharedModule, RouterModule.forChild(ENTITY_STATES),FormsModule,NgxCurrencyModule.forRoot(customCurrencyMaskConfig)],
    declarations: [GastoComponent, GastoDetailComponent, GastoUpdateComponent, GastoDeleteDialogComponent, GastoDeletePopupComponent],
    entryComponents: [GastoComponent, GastoUpdateComponent, GastoDeleteDialogComponent, GastoDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ObrasGastoModule {}
