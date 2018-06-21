import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RecadastroSharedModule } from 'app/shared';
import {
    RegimePrevidenciarioComponent,
    RegimePrevidenciarioDetailComponent,
    RegimePrevidenciarioUpdateComponent,
    RegimePrevidenciarioDeletePopupComponent,
    RegimePrevidenciarioDeleteDialogComponent,
    regimePrevidenciarioRoute,
    regimePrevidenciarioPopupRoute
} from './';

const ENTITY_STATES = [...regimePrevidenciarioRoute, ...regimePrevidenciarioPopupRoute];

@NgModule({
    imports: [RecadastroSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RegimePrevidenciarioComponent,
        RegimePrevidenciarioDetailComponent,
        RegimePrevidenciarioUpdateComponent,
        RegimePrevidenciarioDeleteDialogComponent,
        RegimePrevidenciarioDeletePopupComponent
    ],
    entryComponents: [
        RegimePrevidenciarioComponent,
        RegimePrevidenciarioUpdateComponent,
        RegimePrevidenciarioDeleteDialogComponent,
        RegimePrevidenciarioDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RecadastroRegimePrevidenciarioModule {}
