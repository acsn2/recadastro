import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RecadastroSharedModule } from 'app/shared';
import {
    ParenteServidorComponent,
    ParenteServidorDetailComponent,
    ParenteServidorUpdateComponent,
    ParenteServidorDeletePopupComponent,
    ParenteServidorDeleteDialogComponent,
    parenteServidorRoute,
    parenteServidorPopupRoute
} from './';

const ENTITY_STATES = [...parenteServidorRoute, ...parenteServidorPopupRoute];

@NgModule({
    imports: [RecadastroSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ParenteServidorComponent,
        ParenteServidorDetailComponent,
        ParenteServidorUpdateComponent,
        ParenteServidorDeleteDialogComponent,
        ParenteServidorDeletePopupComponent
    ],
    entryComponents: [
        ParenteServidorComponent,
        ParenteServidorUpdateComponent,
        ParenteServidorDeleteDialogComponent,
        ParenteServidorDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RecadastroParenteServidorModule {}
