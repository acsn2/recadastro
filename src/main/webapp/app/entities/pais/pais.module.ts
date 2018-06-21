import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RecadastroSharedModule } from 'app/shared';
import {
    PaisComponent,
    PaisDetailComponent,
    PaisUpdateComponent,
    PaisDeletePopupComponent,
    PaisDeleteDialogComponent,
    paisRoute,
    paisPopupRoute
} from './';

const ENTITY_STATES = [...paisRoute, ...paisPopupRoute];

@NgModule({
    imports: [RecadastroSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [PaisComponent, PaisDetailComponent, PaisUpdateComponent, PaisDeleteDialogComponent, PaisDeletePopupComponent],
    entryComponents: [PaisComponent, PaisUpdateComponent, PaisDeleteDialogComponent, PaisDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RecadastroPaisModule {}
