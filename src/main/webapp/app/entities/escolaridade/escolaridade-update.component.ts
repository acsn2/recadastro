import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IEscolaridade } from 'app/shared/model/escolaridade.model';
import { EscolaridadeService } from './escolaridade.service';
import { IServidor } from 'app/shared/model/servidor.model';
import { ServidorService } from 'app/entities/servidor';

@Component({
    selector: '-escolaridade-update',
    templateUrl: './escolaridade-update.component.html'
})
export class EscolaridadeUpdateComponent implements OnInit {
    private _escolaridade: IEscolaridade;
    isSaving: boolean;

    escolaridades: IServidor[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private escolaridadeService: EscolaridadeService,
        private servidorService: ServidorService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ escolaridade }) => {
            this.escolaridade = escolaridade;
        });
        this.servidorService.query({ filter: 'escolaridade-is-null' }).subscribe(
            (res: HttpResponse<IServidor[]>) => {
                if (!this.escolaridade.escolaridade || !this.escolaridade.escolaridade.id) {
                    this.escolaridades = res.body;
                } else {
                    this.servidorService.find(this.escolaridade.escolaridade.id).subscribe(
                        (subRes: HttpResponse<IServidor>) => {
                            this.escolaridades = [subRes.body].concat(res.body);
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
        if (this.escolaridade.id !== undefined) {
            this.subscribeToSaveResponse(this.escolaridadeService.update(this.escolaridade));
        } else {
            this.subscribeToSaveResponse(this.escolaridadeService.create(this.escolaridade));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IEscolaridade>>) {
        result.subscribe((res: HttpResponse<IEscolaridade>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get escolaridade() {
        return this._escolaridade;
    }

    set escolaridade(escolaridade: IEscolaridade) {
        this._escolaridade = escolaridade;
    }
}
