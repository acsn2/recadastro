import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { RecadastroServidorModule } from './servidor/servidor.module';
import { RecadastroDependenteModule } from './dependente/dependente.module';
import { RecadastroParenteServidorModule } from './parente-servidor/parente-servidor.module';
import { RecadastroEstadoCivilModule } from './estado-civil/estado-civil.module';
import { RecadastroOrgaoModule } from './orgao/orgao.module';
import { RecadastroServidorTipoModule } from './servidor-tipo/servidor-tipo.module';
import { RecadastroPaisModule } from './pais/pais.module';
import { RecadastroCidadeModule } from './cidade/cidade.module';
import { RecadastroRacaCorModule } from './raca-cor/raca-cor.module';
import { RecadastroEscolaridadeModule } from './escolaridade/escolaridade.module';
import { RecadastroCategoriaESocialModule } from './categoria-e-social/categoria-e-social.module';
import { RecadastroRegimeTrabalhoModule } from './regime-trabalho/regime-trabalho.module';
import { RecadastroRegimePrevidenciarioModule } from './regime-previdenciario/regime-previdenciario.module';
import { RecadastroAnelViarioModule } from './anel-viario/anel-viario.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        RecadastroServidorModule,
        RecadastroDependenteModule,
        RecadastroParenteServidorModule,
        RecadastroEstadoCivilModule,
        RecadastroOrgaoModule,
        RecadastroServidorTipoModule,
        RecadastroPaisModule,
        RecadastroCidadeModule,
        RecadastroRacaCorModule,
        RecadastroEscolaridadeModule,
        RecadastroCategoriaESocialModule,
        RecadastroRegimeTrabalhoModule,
        RecadastroRegimePrevidenciarioModule,
        RecadastroAnelViarioModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RecadastroEntityModule {}
