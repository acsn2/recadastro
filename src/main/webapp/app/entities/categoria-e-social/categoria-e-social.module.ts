import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RecadastroSharedModule } from 'app/shared';
import {
    CategoriaESocialComponent,
    CategoriaESocialDetailComponent,
    CategoriaESocialUpdateComponent,
    CategoriaESocialDeletePopupComponent,
    CategoriaESocialDeleteDialogComponent,
    categoriaESocialRoute,
    categoriaESocialPopupRoute
} from './';

const ENTITY_STATES = [...categoriaESocialRoute, ...categoriaESocialPopupRoute];

@NgModule({
    imports: [RecadastroSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CategoriaESocialComponent,
        CategoriaESocialDetailComponent,
        CategoriaESocialUpdateComponent,
        CategoriaESocialDeleteDialogComponent,
        CategoriaESocialDeletePopupComponent
    ],
    entryComponents: [
        CategoriaESocialComponent,
        CategoriaESocialUpdateComponent,
        CategoriaESocialDeleteDialogComponent,
        CategoriaESocialDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RecadastroCategoriaESocialModule {}
