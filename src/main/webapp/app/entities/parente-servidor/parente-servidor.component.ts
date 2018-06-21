import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IParenteServidor } from 'app/shared/model/parente-servidor.model';
import { Principal } from 'app/core';
import { ParenteServidorService } from './parente-servidor.service';

@Component({
    selector: '-parente-servidor',
    templateUrl: './parente-servidor.component.html'
})
export class ParenteServidorComponent implements OnInit, OnDestroy {
    parenteServidors: IParenteServidor[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private parenteServidorService: ParenteServidorService,
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
            this.parenteServidorService
                .search({
                    query: this.currentSearch
                })
                .subscribe(
                    (res: HttpResponse<IParenteServidor[]>) => (this.parenteServidors = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.parenteServidorService.query().subscribe(
            (res: HttpResponse<IParenteServidor[]>) => {
                this.parenteServidors = res.body;
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
        this.registerChangeInParenteServidors();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IParenteServidor) {
        return item.id;
    }

    registerChangeInParenteServidors() {
        this.eventSubscriber = this.eventManager.subscribe('parenteServidorListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
