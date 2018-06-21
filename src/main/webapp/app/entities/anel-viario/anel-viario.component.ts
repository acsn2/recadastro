import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAnelViario } from 'app/shared/model/anel-viario.model';
import { Principal } from 'app/core';
import { AnelViarioService } from './anel-viario.service';

@Component({
    selector: '-anel-viario',
    templateUrl: './anel-viario.component.html'
})
export class AnelViarioComponent implements OnInit, OnDestroy {
    anelViarios: IAnelViario[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private anelViarioService: AnelViarioService,
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
            this.anelViarioService
                .search({
                    query: this.currentSearch
                })
                .subscribe(
                    (res: HttpResponse<IAnelViario[]>) => (this.anelViarios = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.anelViarioService.query().subscribe(
            (res: HttpResponse<IAnelViario[]>) => {
                this.anelViarios = res.body;
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
        this.registerChangeInAnelViarios();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAnelViario) {
        return item.id;
    }

    registerChangeInAnelViarios() {
        this.eventSubscriber = this.eventManager.subscribe('anelViarioListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
