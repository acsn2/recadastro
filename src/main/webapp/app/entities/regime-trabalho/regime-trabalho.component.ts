import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IRegimeTrabalho } from 'app/shared/model/regime-trabalho.model';
import { Principal } from 'app/core';
import { RegimeTrabalhoService } from './regime-trabalho.service';

@Component({
    selector: '-regime-trabalho',
    templateUrl: './regime-trabalho.component.html'
})
export class RegimeTrabalhoComponent implements OnInit, OnDestroy {
    regimeTrabalhos: IRegimeTrabalho[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private regimeTrabalhoService: RegimeTrabalhoService,
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
            this.regimeTrabalhoService
                .search({
                    query: this.currentSearch
                })
                .subscribe(
                    (res: HttpResponse<IRegimeTrabalho[]>) => (this.regimeTrabalhos = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.regimeTrabalhoService.query().subscribe(
            (res: HttpResponse<IRegimeTrabalho[]>) => {
                this.regimeTrabalhos = res.body;
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
        this.registerChangeInRegimeTrabalhos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IRegimeTrabalho) {
        return item.id;
    }

    registerChangeInRegimeTrabalhos() {
        this.eventSubscriber = this.eventManager.subscribe('regimeTrabalhoListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
