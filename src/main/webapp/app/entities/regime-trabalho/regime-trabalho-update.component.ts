import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IRegimeTrabalho } from 'app/shared/model/regime-trabalho.model';
import { RegimeTrabalhoService } from './regime-trabalho.service';
import { IServidor } from 'app/shared/model/servidor.model';
import { ServidorService } from 'app/entities/servidor';

@Component({
    selector: '-regime-trabalho-update',
    templateUrl: './regime-trabalho-update.component.html'
})
export class RegimeTrabalhoUpdateComponent implements OnInit {
    private _regimeTrabalho: IRegimeTrabalho;
    isSaving: boolean;

    regimetrabalhos: IServidor[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private regimeTrabalhoService: RegimeTrabalhoService,
        private servidorService: ServidorService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ regimeTrabalho }) => {
            this.regimeTrabalho = regimeTrabalho;
        });
        this.servidorService.query({ filter: 'regimetrabalho-is-null' }).subscribe(
            (res: HttpResponse<IServidor[]>) => {
                if (!this.regimeTrabalho.regimeTrabalho || !this.regimeTrabalho.regimeTrabalho.id) {
                    this.regimetrabalhos = res.body;
                } else {
                    this.servidorService.find(this.regimeTrabalho.regimeTrabalho.id).subscribe(
                        (subRes: HttpResponse<IServidor>) => {
                            this.regimetrabalhos = [subRes.body].concat(res.body);
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
        if (this.regimeTrabalho.id !== undefined) {
            this.subscribeToSaveResponse(this.regimeTrabalhoService.update(this.regimeTrabalho));
        } else {
            this.subscribeToSaveResponse(this.regimeTrabalhoService.create(this.regimeTrabalho));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IRegimeTrabalho>>) {
        result.subscribe((res: HttpResponse<IRegimeTrabalho>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get regimeTrabalho() {
        return this._regimeTrabalho;
    }

    set regimeTrabalho(regimeTrabalho: IRegimeTrabalho) {
        this._regimeTrabalho = regimeTrabalho;
    }
}
