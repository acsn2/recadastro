import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IRegimePrevidenciario } from 'app/shared/model/regime-previdenciario.model';
import { RegimePrevidenciarioService } from './regime-previdenciario.service';
import { IServidor } from 'app/shared/model/servidor.model';
import { ServidorService } from 'app/entities/servidor';

@Component({
    selector: '-regime-previdenciario-update',
    templateUrl: './regime-previdenciario-update.component.html'
})
export class RegimePrevidenciarioUpdateComponent implements OnInit {
    private _regimePrevidenciario: IRegimePrevidenciario;
    isSaving: boolean;

    regimeprevidenciarios: IServidor[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private regimePrevidenciarioService: RegimePrevidenciarioService,
        private servidorService: ServidorService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ regimePrevidenciario }) => {
            this.regimePrevidenciario = regimePrevidenciario;
        });
        this.servidorService.query({ filter: 'regimeprevidenciario-is-null' }).subscribe(
            (res: HttpResponse<IServidor[]>) => {
                if (!this.regimePrevidenciario.regimePrevidenciario || !this.regimePrevidenciario.regimePrevidenciario.id) {
                    this.regimeprevidenciarios = res.body;
                } else {
                    this.servidorService.find(this.regimePrevidenciario.regimePrevidenciario.id).subscribe(
                        (subRes: HttpResponse<IServidor>) => {
                            this.regimeprevidenciarios = [subRes.body].concat(res.body);
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
        if (this.regimePrevidenciario.id !== undefined) {
            this.subscribeToSaveResponse(this.regimePrevidenciarioService.update(this.regimePrevidenciario));
        } else {
            this.subscribeToSaveResponse(this.regimePrevidenciarioService.create(this.regimePrevidenciario));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IRegimePrevidenciario>>) {
        result.subscribe(
            (res: HttpResponse<IRegimePrevidenciario>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
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
    get regimePrevidenciario() {
        return this._regimePrevidenciario;
    }

    set regimePrevidenciario(regimePrevidenciario: IRegimePrevidenciario) {
        this._regimePrevidenciario = regimePrevidenciario;
    }
}
