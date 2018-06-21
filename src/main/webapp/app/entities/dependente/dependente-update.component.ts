import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IDependente } from 'app/shared/model/dependente.model';
import { DependenteService } from './dependente.service';
import { IServidor } from 'app/shared/model/servidor.model';
import { ServidorService } from 'app/entities/servidor';

@Component({
    selector: '-dependente-update',
    templateUrl: './dependente-update.component.html'
})
export class DependenteUpdateComponent implements OnInit {
    private _dependente: IDependente;
    isSaving: boolean;

    servidors: IServidor[];
    datNacDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private dependenteService: DependenteService,
        private servidorService: ServidorService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ dependente }) => {
            this.dependente = dependente;
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
        if (this.dependente.id !== undefined) {
            this.subscribeToSaveResponse(this.dependenteService.update(this.dependente));
        } else {
            this.subscribeToSaveResponse(this.dependenteService.create(this.dependente));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDependente>>) {
        result.subscribe((res: HttpResponse<IDependente>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get dependente() {
        return this._dependente;
    }

    set dependente(dependente: IDependente) {
        this._dependente = dependente;
    }
}
