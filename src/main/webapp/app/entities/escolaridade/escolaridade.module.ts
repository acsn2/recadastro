import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RecadastroSharedModule } from 'app/shared';
import {
    EscolaridadeComponent,
    EscolaridadeDetailComponent,
    EscolaridadeUpdateComponent,
    EscolaridadeDeletePopupComponent,
    EscolaridadeDeleteDialogComponent,
    escolaridadeRoute,
    escolaridadePopupRoute
} from './';

const ENTITY_STATES = [...escolaridadeRoute, ...escolaridadePopupRoute];

@NgModule({
    imports: [RecadastroSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EscolaridadeComponent,
        EscolaridadeDetailComponent,
        EscolaridadeUpdateComponent,
        EscolaridadeDeleteDialogComponent,
        EscolaridadeDeletePopupComponent
    ],
    entryComponents: [
        EscolaridadeComponent,
        EscolaridadeUpdateComponent,
        EscolaridadeDeleteDialogComponent,
        EscolaridadeDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RecadastroEscolaridadeModule {}
