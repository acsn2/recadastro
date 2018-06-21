import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPais } from 'app/shared/model/pais.model';
import { Principal } from 'app/core';
import { PaisService } from './pais.service';

@Component({
    selector: '-pais',
    templateUrl: './pais.component.html'
})
export class PaisComponent implements OnInit, OnDestroy {
    pais: IPais[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private paisService: PaisService,
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
            this.paisService
                .search({
                    query: this.currentSearch
                })
                .subscribe((res: HttpResponse<IPais[]>) => (this.pais = res.body), (res: HttpErrorResponse) => this.onError(res.message));
            return;
        }
        this.paisService.query().subscribe(
            (res: HttpResponse<IPais[]>) => {
                this.pais = res.body;
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
        this.registerChangeInPais();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPais) {
        return item.id;
    }

    registerChangeInPais() {
        this.eventSubscriber = this.eventManager.subscribe('paisListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
