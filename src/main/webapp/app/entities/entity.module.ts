import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ObrasObraModule } from './obra/obra.module';
import { ObrasResumoGastoModule } from './resumo-gasto/resumo-gasto.module';
import { ObrasGastoModule } from './gasto/gasto.module';
import { ResumoTotalModule } from './resumo-total/resumo-total.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        ObrasObraModule,
        ObrasResumoGastoModule,
        ObrasGastoModule,
        ResumoTotalModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ObrasEntityModule {}
