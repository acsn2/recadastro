import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RecadastroSharedModule } from 'app/shared';
import {
    RacaCorComponent,
    RacaCorDetailComponent,
    RacaCorUpdateComponent,
    RacaCorDeletePopupComponent,
    RacaCorDeleteDialogComponent,
    racaCorRoute,
    racaCorPopupRoute
} from './';

const ENTITY_STATES = [...racaCorRoute, ...racaCorPopupRoute];

@NgModule({
    imports: [RecadastroSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RacaCorComponent,
        RacaCorDetailComponent,
        RacaCorUpdateComponent,
        RacaCorDeleteDialogComponent,
        RacaCorDeletePopupComponent
    ],
    entryComponents: [RacaCorComponent, RacaCorUpdateComponent, RacaCorDeleteDialogComponent, RacaCorDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RecadastroRacaCorModule {}
