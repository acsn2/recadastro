import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IEscolaridade } from 'app/shared/model/escolaridade.model';
import { Principal } from 'app/core';
import { EscolaridadeService } from './escolaridade.service';

@Component({
    selector: '-escolaridade',
    templateUrl: './escolaridade.component.html'
})
export class EscolaridadeComponent implements OnInit, OnDestroy {
    escolaridades: IEscolaridade[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private escolaridadeService: EscolaridadeService,
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
            this.escolaridadeService
                .search({
                    query: this.currentSearch
                })
                .subscribe(
                    (res: HttpResponse<IEscolaridade[]>) => (this.escolaridades = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.escolaridadeService.query().subscribe(
            (res: HttpResponse<IEscolaridade[]>) => {
                this.escolaridades = res.body;
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
        this.registerChangeInEscolaridades();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IEscolaridade) {
        return item.id;
    }

    registerChangeInEscolaridades() {
        this.eventSubscriber = this.eventManager.subscribe('escolaridadeListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
