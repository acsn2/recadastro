import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { RacaCor } from 'app/shared/model/raca-cor.model';
import { RacaCorService } from './raca-cor.service';
import { RacaCorComponent } from './raca-cor.component';
import { RacaCorDetailComponent } from './raca-cor-detail.component';
import { RacaCorUpdateComponent } from './raca-cor-update.component';
import { RacaCorDeletePopupComponent } from './raca-cor-delete-dialog.component';
import { IRacaCor } from 'app/shared/model/raca-cor.model';

@Injectable({ providedIn: 'root' })
export class RacaCorResolve implements Resolve<IRacaCor> {
    constructor(private service: RacaCorService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((racaCor: HttpResponse<RacaCor>) => racaCor.body);
        }
        return Observable.of(new RacaCor());
    }
}

export const racaCorRoute: Routes = [
    {
        path: 'raca-cor',
        component: RacaCorComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RacaCors'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'raca-cor/:id/view',
        component: RacaCorDetailComponent,
        resolve: {
            racaCor: RacaCorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RacaCors'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'raca-cor/new',
        component: RacaCorUpdateComponent,
        resolve: {
            racaCor: RacaCorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RacaCors'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'raca-cor/:id/edit',
        component: RacaCorUpdateComponent,
        resolve: {
            racaCor: RacaCorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RacaCors'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const racaCorPopupRoute: Routes = [
    {
        path: 'raca-cor/:id/delete',
        component: RacaCorDeletePopupComponent,
        resolve: {
            racaCor: RacaCorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RacaCors'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
