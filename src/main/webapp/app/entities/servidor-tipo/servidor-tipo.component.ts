import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IServidorTipo } from 'app/shared/model/servidor-tipo.model';
import { Principal } from 'app/core';
import { ServidorTipoService } from './servidor-tipo.service';

@Component({
    selector: '-servidor-tipo',
    templateUrl: './servidor-tipo.component.html'
})
export class ServidorTipoComponent implements OnInit, OnDestroy {
    servidorTipos: IServidorTipo[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private servidorTipoService: ServidorTipoService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search']
                ? this.activatedRoute.snapshot.params['search']
                : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.servidorTipoService
                .search({
                    query: this.currentSearch
                })
                .subscribe(
                    (res: HttpResponse<IServidorTipo[]>) => (this.servidorTipos = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.servidorTipoService.query().subscribe(
            (res: HttpResponse<IServidorTipo[]>) => {
                this.servidorTipos = res.body;
                this.currentSearch = '';
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInServidorTipos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IServidorTipo) {
        return item.id;
    }

    registerChangeInServidorTipos() {
        this.eventSubscriber = this.eventManager.subscribe('servidorTipoListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
