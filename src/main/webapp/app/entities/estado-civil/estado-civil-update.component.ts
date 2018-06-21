import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IEstadoCivil } from 'app/shared/model/estado-civil.model';
import { EstadoCivilService } from './estado-civil.service';
import { IServidor } from 'app/shared/model/servidor.model';
import { ServidorService } from 'app/entities/servidor';

@Component({
    selector: '-estado-civil-update',
    templateUrl: './estado-civil-update.component.html'
})
export class EstadoCivilUpdateComponent implements OnInit {
    private _estadoCivil: IEstadoCivil;
    isSaving: boolean;

    estadocivils: IServidor[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private estadoCivilService: EstadoCivilService,
        private servidorService: ServidorService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ estadoCivil }) => {
            this.estadoCivil = estadoCivil;
        });
        this.servidorService.query({ filter: 'estadocivil-is-null' }).subscribe(
            (res: HttpResponse<IServidor[]>) => {
                if (!this.estadoCivil.estadoCivil || !this.estadoCivil.estadoCivil.id) {
                    this.estadocivils = res.body;
                } else {
                    this.servidorService.find(this.estadoCivil.estadoCivil.id).subscribe(
                        (subRes: HttpResponse<IServidor>) => {
                            this.estadocivils = [subRes.body].concat(res.body);
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
        if (this.estadoCivil.id !== undefined) {
            this.subscribeToSaveResponse(this.estadoCivilService.update(this.estadoCivil));
        } else {
            this.subscribeToSaveResponse(this.estadoCivilService.create(this.estadoCivil));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IEstadoCivil>>) {
        result.subscribe((res: HttpResponse<IEstadoCivil>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get estadoCivil() {
        return this._estadoCivil;
    }

    set estadoCivil(estadoCivil: IEstadoCivil) {
        this._estadoCivil = estadoCivil;
    }
}
