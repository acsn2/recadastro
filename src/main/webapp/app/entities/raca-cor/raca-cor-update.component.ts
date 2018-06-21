import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IRacaCor } from 'app/shared/model/raca-cor.model';
import { RacaCorService } from './raca-cor.service';
import { IServidor } from 'app/shared/model/servidor.model';
import { ServidorService } from 'app/entities/servidor';

@Component({
    selector: '-raca-cor-update',
    templateUrl: './raca-cor-update.component.html'
})
export class RacaCorUpdateComponent implements OnInit {
    private _racaCor: IRacaCor;
    isSaving: boolean;

    racacors: IServidor[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private racaCorService: RacaCorService,
        private servidorService: ServidorService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ racaCor }) => {
            this.racaCor = racaCor;
        });
        this.servidorService.query({ filter: 'racacor-is-null' }).subscribe(
            (res: HttpResponse<IServidor[]>) => {
                if (!this.racaCor.racaCor || !this.racaCor.racaCor.id) {
                    this.racacors = res.body;
                } else {
                    this.servidorService.find(this.racaCor.racaCor.id).subscribe(
                        (subRes: HttpResponse<IServidor>) => {
                            this.racacors = [subRes.body].concat(res.body);
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
        if (this.racaCor.id !== undefined) {
            this.subscribeToSaveResponse(this.racaCorService.update(this.racaCor));
        } else {
            this.subscribeToSaveResponse(this.racaCorService.create(this.racaCor));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IRacaCor>>) {
        result.subscribe((res: HttpResponse<IRacaCor>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get racaCor() {
        return this._racaCor;
    }

    set racaCor(racaCor: IRacaCor) {
        this._racaCor = racaCor;
    }
}
