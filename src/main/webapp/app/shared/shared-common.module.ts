import { NgModule } from '@angular/core';

import { RecadastroSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [RecadastroSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [RecadastroSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class RecadastroSharedCommonModule {}
