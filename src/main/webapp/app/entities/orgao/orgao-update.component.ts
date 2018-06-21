import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IOrgao } from 'app/shared/model/orgao.model';
import { OrgaoService } from './orgao.service';
import { IServidor } from 'app/shared/model/servidor.model';
import { ServidorService } from 'app/entities/servidor';

@Component({
    selector: '-orgao-update',
    templateUrl: './orgao-update.component.html'
})
export class OrgaoUpdateComponent implements OnInit {
    private _orgao: IOrgao;
    isSaving: boolean;

    orgaos: IServidor[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private orgaoService: OrgaoService,
        private servidorService: ServidorService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ orgao }) => {
            this.orgao = orgao;
        });
        this.servidorService.query({ filter: 'orgao-is-null' }).subscribe(
            (res: HttpResponse<IServidor[]>) => {
                if (!this.orgao.orgao || !this.orgao.orgao.id) {
                    this.orgaos = res.body;
                } else {
                    this.servidorService.find(this.orgao.orgao.id).subscribe(
                        (subRes: HttpResponse<IServidor>) => {
                            this.orgaos = [subRes.body].concat(res.body);
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
        if (this.orgao.id !== undefined) {
            this.subscribeToSaveResponse(this.orgaoService.update(this.orgao));
        } else {
            this.subscribeToSaveResponse(this.orgaoService.create(this.orgao));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IOrgao>>) {
        result.subscribe((res: HttpResponse<IOrgao>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get orgao() {
        return this._orgao;
    }

    set orgao(orgao: IOrgao) {
        this._orgao = orgao;
    }
}
