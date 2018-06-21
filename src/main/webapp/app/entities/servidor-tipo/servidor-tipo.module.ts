import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RecadastroSharedModule } from 'app/shared';
import {
    ServidorTipoComponent,
    ServidorTipoDetailComponent,
    ServidorTipoUpdateComponent,
    ServidorTipoDeletePopupComponent,
    ServidorTipoDeleteDialogComponent,
    servidorTipoRoute,
    servidorTipoPopupRoute
} from './';

const ENTITY_STATES = [...servidorTipoRoute, ...servidorTipoPopupRoute];

@NgModule({
    imports: [RecadastroSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ServidorTipoComponent,
        ServidorTipoDetailComponent,
        ServidorTipoUpdateComponent,
        ServidorTipoDeleteDialogComponent,
        ServidorTipoDeletePopupComponent
    ],
    entryComponents: [
        ServidorTipoComponent,
        ServidorTipoUpdateComponent,
        ServidorTipoDeleteDialogComponent,
        ServidorTipoDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RecadastroServidorTipoModule {}
