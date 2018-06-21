import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IServidorTipo } from 'app/shared/model/servidor-tipo.model';
import { ServidorTipoService } from './servidor-tipo.service';
import { IServidor } from 'app/shared/model/servidor.model';
import { ServidorService } from 'app/entities/servidor';

@Component({
    selector: '-servidor-tipo-update',
    templateUrl: './servidor-tipo-update.component.html'
})
export class ServidorTipoUpdateComponent implements OnInit {
    private _servidorTipo: IServidorTipo;
    isSaving: boolean;

    servidortipos: IServidor[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private servidorTipoService: ServidorTipoService,
        private servidorService: ServidorService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ servidorTipo }) => {
            this.servidorTipo = servidorTipo;
        });
        this.servidorService.query({ filter: 'servidortipo-is-null' }).subscribe(
            (res: HttpResponse<IServidor[]>) => {
                if (!this.servidorTipo.servidorTipo || !this.servidorTipo.servidorTipo.id) {
                    this.servidortipos = res.body;
                } else {
                    this.servidorService.find(this.servidorTipo.servidorTipo.id).subscribe(
                        (subRes: HttpResponse<IServidor>) => {
                            this.servidortipos = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.servidorTipo.id !== undefined) {
            this.subscribeToSaveResponse(this.servidorTipoService.update(this.servidorTipo));
        } else {
            this.subscribeToSaveResponse(this.servidorTipoService.create(this.servidorTipo));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IServidorTipo>>) {
        result.subscribe((res: HttpResponse<IServidorTipo>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get servidorTipo() {
        return this._servidorTipo;
    }

    set servidorTipo(servidorTipo: IServidorTipo) {
        this._servidorTipo = servidorTipo;
    }
}
