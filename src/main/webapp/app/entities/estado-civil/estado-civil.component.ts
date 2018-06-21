import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IEstadoCivil } from 'app/shared/model/estado-civil.model';
import { Principal } from 'app/core';
import { EstadoCivilService } from './estado-civil.service';

@Component({
    selector: '-estado-civil',
    templateUrl: './estado-civil.component.html'
})
export class EstadoCivilComponent implements OnInit, OnDestroy {
    estadoCivils: IEstadoCivil[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private estadoCivilService: EstadoCivilService,
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
            this.estadoCivilService
                .search({
                    query: this.currentSearch
                })
                .subscribe(
                    (res: HttpResponse<IEstadoCivil[]>) => (this.estadoCivils = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.estadoCivilService.query().subscribe(
            (res: HttpResponse<IEstadoCivil[]>) => {
                this.estadoCivils = res.body;
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
        this.registerChangeInEstadoCivils();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IEstadoCivil) {
        return item.id;
    }

    registerChangeInEstadoCivils() {
        this.eventSubscriber = this.eventManager.subscribe('estadoCivilListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
