import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { Pais } from 'app/shared/model/pais.model';
import { PaisService } from './pais.service';
import { PaisComponent } from './pais.component';
import { PaisDetailComponent } from './pais-detail.component';
import { PaisUpdateComponent } from './pais-update.component';
import { PaisDeletePopupComponent } from './pais-delete-dialog.component';
import { IPais } from 'app/shared/model/pais.model';

@Injectable({ providedIn: 'root' })
export class PaisResolve implements Resolve<IPais> {
    constructor(private service: PaisService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((pais: HttpResponse<Pais>) => pais.body);
        }
        return Observable.of(new Pais());
    }
}

export const paisRoute: Routes = [
    {
        path: 'pais',
        component: PaisComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pais'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pais/:id/view',
        component: PaisDetailComponent,
        resolve: {
            pais: PaisResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pais'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pais/new',
        component: PaisUpdateComponent,
        resolve: {
            pais: PaisResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pais'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pais/:id/edit',
        component: PaisUpdateComponent,
        resolve: {
            pais: PaisResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pais'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const paisPopupRoute: Routes = [
    {
        path: 'pais/:id/delete',
        component: PaisDeletePopupComponent,
        resolve: {
            pais: PaisResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pais'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
