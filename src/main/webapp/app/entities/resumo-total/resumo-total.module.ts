import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ResumoTotalComponent, resumoTotalRoute } from './';
import { ObrasSharedModule } from 'app/shared';
import { ChartModule } from 'primeng/primeng';

const ENTITY_STATES = [...resumoTotalRoute];

@NgModule({
    imports: [ObrasSharedModule, ChartModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [ResumoTotalComponent],
    entryComponents: [ResumoTotalComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResumoTotalModule {}
