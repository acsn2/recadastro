import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IPais } from 'app/shared/model/pais.model';
import { PaisService } from './pais.service';
import { IServidor } from 'app/shared/model/servidor.model';
import { ServidorService } from 'app/entities/servidor';

@Component({
    selector: '-pais-update',
    templateUrl: './pais-update.component.html'
})
export class PaisUpdateComponent implements OnInit {
    private _pais: IPais;
    isSaving: boolean;

    paisCollection: IServidor[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private paisService: PaisService,
        private servidorService: ServidorService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ pais }) => {
            this.pais = pais;
        });
        this.servidorService.query({ filter: 'pais-is-null' }).subscribe(
            (res: HttpResponse<IServidor[]>) => {
                if (!this.pais.pais || !this.pais.pais.id) {
                    this.paisCollection = res.body;
                } else {
                    this.servidorService.find(this.pais.pais.id).subscribe(
                        (subRes: HttpResponse<IServidor>) => {
                            this.paisCollection = [subRes.body].concat(res.body);
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
        if (this.pais.id !== undefined) {
            this.subscribeToSaveResponse(this.paisService.update(this.pais));
        } else {
            this.subscribeToSaveResponse(this.paisService.create(this.pais));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPais>>) {
        result.subscribe((res: HttpResponse<IPais>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get pais() {
        return this._pais;
    }

    set pais(pais: IPais) {
        this._pais = pais;
    }
}
