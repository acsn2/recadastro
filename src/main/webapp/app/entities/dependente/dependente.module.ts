import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RecadastroSharedModule } from 'app/shared';
import {
    DependenteComponent,
    DependenteDetailComponent,
    DependenteUpdateComponent,
    DependenteDeletePopupComponent,
    DependenteDeleteDialogComponent,
    dependenteRoute,
    dependentePopupRoute
} from './';

const ENTITY_STATES = [...dependenteRoute, ...dependentePopupRoute];

@NgModule({
    imports: [RecadastroSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DependenteComponent,
        DependenteDetailComponent,
        DependenteUpdateComponent,
        DependenteDeleteDialogComponent,
        DependenteDeletePopupComponent
    ],
    entryComponents: [DependenteComponent, DependenteUpdateComponent, DependenteDeleteDialogComponent, DependenteDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RecadastroDependenteModule {}
