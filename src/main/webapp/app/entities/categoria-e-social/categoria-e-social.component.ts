import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICategoriaESocial } from 'app/shared/model/categoria-e-social.model';
import { Principal } from 'app/core';
import { CategoriaESocialService } from './categoria-e-social.service';

@Component({
    selector: '-categoria-e-social',
    templateUrl: './categoria-e-social.component.html'
})
export class CategoriaESocialComponent implements OnInit, OnDestroy {
    categoriaESocials: ICategoriaESocial[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private categoriaESocialService: CategoriaESocialService,
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
            this.categoriaESocialService
                .search({
                    query: this.currentSearch
                })
                .subscribe(
                    (res: HttpResponse<ICategoriaESocial[]>) => (this.categoriaESocials = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.categoriaESocialService.query().subscribe(
            (res: HttpResponse<ICategoriaESocial[]>) => {
                this.categoriaESocials = res.body;
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
        this.registerChangeInCategoriaESocials();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICategoriaESocial) {
        return item.id;
    }

    registerChangeInCategoriaESocials() {
        this.eventSubscriber = this.eventManager.subscribe('categoriaESocialListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
