import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IServidor } from 'app/shared/model/servidor.model';
import { ServidorService } from './servidor.service';

@Component({
    selector: '-servidor-update',
    templateUrl: './servidor-update.component.html'
})
export class ServidorUpdateComponent implements OnInit {
    private _servidor: IServidor;
    isSaving: boolean;
    datNascimentoDp: any;
    rgDataExpDp: any;
    ctpsEmissaoDp: any;
    orclDtaExpDp: any;
    orclDtaValidDp: any;
    datExpedRICDp: any;
    cnhDatValidadeDp: any;
    cnhDatExpedDp: any;
    cnhDatPrimHabDp: any;
    datExpeRNEDp: any;
    datChegadaDp: any;

    constructor(private servidorService: ServidorService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ servidor }) => {
            this.servidor = servidor;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.servidor.id !== undefined) {
            this.subscribeToSaveResponse(this.servidorService.update(this.servidor));
        } else {
            this.subscribeToSaveResponse(this.servidorService.create(this.servidor));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IServidor>>) {
        result.subscribe((res: HttpResponse<IServidor>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get servidor() {
        return this._servidor;
    }

    set servidor(servidor: IServidor) {
        this._servidor = servidor;
    }
}
