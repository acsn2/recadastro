import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IRegimePrevidenciario } from 'app/shared/model/regime-previdenciario.model';
import { Principal } from 'app/core';
import { RegimePrevidenciarioService } from './regime-previdenciario.service';

@Component({
    selector: '-regime-previdenciario',
    templateUrl: './regime-previdenciario.component.html'
})
export class RegimePrevidenciarioComponent implements OnInit, OnDestroy {
    regimePrevidenciarios: IRegimePrevidenciario[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private regimePrevidenciarioService: RegimePrevidenciarioService,
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
            this.regimePrevidenciarioService
                .search({
                    query: this.currentSearch
                })
                .subscribe(
                    (res: HttpResponse<IRegimePrevidenciario[]>) => (this.regimePrevidenciarios = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.regimePrevidenciarioService.query().subscribe(
            (res: HttpResponse<IRegimePrevidenciario[]>) => {
                this.regimePrevidenciarios = res.body;
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
        this.registerChangeInRegimePrevidenciarios();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IRegimePrevidenciario) {
        return item.id;
    }

    registerChangeInRegimePrevidenciarios() {
        this.eventSubscriber = this.eventManager.subscribe('regimePrevidenciarioListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
