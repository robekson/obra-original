import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ObrasObrasModule } from './obras/obras.module';
import { ObrasLancamentoGastosModule } from './lancamento-gastos/lancamento-gastos.module';
import { ObrasPeriodoModule } from './periodo/periodo.module';
import { ObrasContaModule } from './conta/conta.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        ObrasObrasModule,
        ObrasLancamentoGastosModule,
        ObrasPeriodoModule,
        ObrasContaModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ObrasEntityModule {}
