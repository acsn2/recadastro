import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IParenteServidor } from 'app/shared/model/parente-servidor.model';
import { ParenteServidorService } from './parente-servidor.service';
import { IServidor } from 'app/shared/model/servidor.model';
import { ServidorService } from 'app/entities/servidor';

@Component({
    selector: '-parente-servidor-update',
    templateUrl: './parente-servidor-update.component.html'
})
export class ParenteServidorUpdateComponent implements OnInit {
    private _parenteServidor: IParenteServidor;
    isSaving: boolean;

    servidors: IServidor[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private parenteServidorService: ParenteServidorService,
        private servidorService: ServidorService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ parenteServidor }) => {
            this.parenteServidor = parenteServidor;
        });
        this.servidorService.query().subscribe(
            (res: HttpResponse<IServidor[]>) => {
                this.servidors = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.parenteServidor.id !== undefined) {
            this.subscribeToSaveResponse(this.parenteServidorService.update(this.parenteServidor));
        } else {
            this.subscribeToSaveResponse(this.parenteServidorService.create(this.parenteServidor));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IParenteServidor>>) {
        result.subscribe((res: HttpResponse<IParenteServidor>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackServidorById(index: number, item: IServidor) {
        return item.id;
    }
    get parenteServidor() {
        return this._parenteServidor;
    }

    set parenteServidor(parenteServidor: IParenteServidor) {
        this._parenteServidor = parenteServidor;
    }
}
