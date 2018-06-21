import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ICategoriaESocial } from 'app/shared/model/categoria-e-social.model';
import { CategoriaESocialService } from './categoria-e-social.service';
import { IServidor } from 'app/shared/model/servidor.model';
import { ServidorService } from 'app/entities/servidor';

@Component({
    selector: '-categoria-e-social-update',
    templateUrl: './categoria-e-social-update.component.html'
})
export class CategoriaESocialUpdateComponent implements OnInit {
    private _categoriaESocial: ICategoriaESocial;
    isSaving: boolean;

    categoriaesocials: IServidor[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private categoriaESocialService: CategoriaESocialService,
        private servidorService: ServidorService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ categoriaESocial }) => {
            this.categoriaESocial = categoriaESocial;
        });
        this.servidorService.query({ filter: 'categoriaesocial-is-null' }).subscribe(
            (res: HttpResponse<IServidor[]>) => {
                if (!this.categoriaESocial.categoriaESocial || !this.categoriaESocial.categoriaESocial.id) {
                    this.categoriaesocials = res.body;
                } else {
                    this.servidorService.find(this.categoriaESocial.categoriaESocial.id).subscribe(
                        (subRes: HttpResponse<IServidor>) => {
                            this.categoriaesocials = [subRes.body].concat(res.body);
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
        if (this.categoriaESocial.id !== undefined) {
            this.subscribeToSaveResponse(this.categoriaESocialService.update(this.categoriaESocial));
        } else {
            this.subscribeToSaveResponse(this.categoriaESocialService.create(this.categoriaESocial));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICategoriaESocial>>) {
        result.subscribe((res: HttpResponse<ICategoriaESocial>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get categoriaESocial() {
        return this._categoriaESocial;
    }

    set categoriaESocial(categoriaESocial: ICategoriaESocial) {
        this._categoriaESocial = categoriaESocial;
    }
}
