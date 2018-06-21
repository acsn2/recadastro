import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IAnelViario } from 'app/shared/model/anel-viario.model';
import { AnelViarioService } from './anel-viario.service';
import { IServidor } from 'app/shared/model/servidor.model';
import { ServidorService } from 'app/entities/servidor';

@Component({
    selector: '-anel-viario-update',
    templateUrl: './anel-viario-update.component.html'
})
export class AnelViarioUpdateComponent implements OnInit {
    private _anelViario: IAnelViario;
    isSaving: boolean;

    tasks: IServidor[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private anelViarioService: AnelViarioService,
        private servidorService: ServidorService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ anelViario }) => {
            this.anelViario = anelViario;
        });
        this.servidorService.query({ filter: 'anelviario-is-null' }).subscribe(
            (res: HttpResponse<IServidor[]>) => {
                if (!this.anelViario.task || !this.anelViario.task.id) {
                    this.tasks = res.body;
                } else {
                    this.servidorService.find(this.anelViario.task.id).subscribe(
                        (subRes: HttpResponse<IServidor>) => {
                            this.tasks = [subRes.body].concat(res.body);
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
        if (this.anelViario.id !== undefined) {
            this.subscribeToSaveResponse(this.anelViarioService.update(this.anelViario));
        } else {
            this.subscribeToSaveResponse(this.anelViarioService.create(this.anelViario));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAnelViario>>) {
        result.subscribe((res: HttpResponse<IAnelViario>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get anelViario() {
        return this._anelViario;
    }

    set anelViario(anelViario: IAnelViario) {
        this._anelViario = anelViario;
    }
}
