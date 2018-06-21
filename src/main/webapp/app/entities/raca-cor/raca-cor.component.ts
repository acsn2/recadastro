import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IRacaCor } from 'app/shared/model/raca-cor.model';
import { Principal } from 'app/core';
import { RacaCorService } from './raca-cor.service';

@Component({
    selector: '-raca-cor',
    templateUrl: './raca-cor.component.html'
})
export class RacaCorComponent implements OnInit, OnDestroy {
    racaCors: IRacaCor[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private racaCorService: RacaCorService,
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
            this.racaCorService
                .search({
                    query: this.currentSearch
                })
                .subscribe(
                    (res: HttpResponse<IRacaCor[]>) => (this.racaCors = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.racaCorService.query().subscribe(
            (res: HttpResponse<IRacaCor[]>) => {
                this.racaCors = res.body;
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
        this.registerChangeInRacaCors();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IRacaCor) {
        return item.id;
    }

    registerChangeInRacaCors() {
        this.eventSubscriber = this.eventManager.subscribe('racaCorListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
