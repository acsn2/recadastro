import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { AnelViario } from 'app/shared/model/anel-viario.model';
import { AnelViarioService } from './anel-viario.service';
import { AnelViarioComponent } from './anel-viario.component';
import { AnelViarioDetailComponent } from './anel-viario-detail.component';
import { AnelViarioUpdateComponent } from './anel-viario-update.component';
import { AnelViarioDeletePopupComponent } from './anel-viario-delete-dialog.component';
import { IAnelViario } from 'app/shared/model/anel-viario.model';

@Injectable({ providedIn: 'root' })
export class AnelViarioResolve implements Resolve<IAnelViario> {
    constructor(private service: AnelViarioService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((anelViario: HttpResponse<AnelViario>) => anelViario.body);
        }
        return Observable.of(new AnelViario());
    }
}

export const anelViarioRoute: Routes = [
    {
        path: 'anel-viario',
        component: AnelViarioComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AnelViarios'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'anel-viario/:id/view',
        component: AnelViarioDetailComponent,
        resolve: {
            anelViario: AnelViarioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AnelViarios'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'anel-viario/new',
        component: AnelViarioUpdateComponent,
        resolve: {
            anelViario: AnelViarioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AnelViarios'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'anel-viario/:id/edit',
        component: AnelViarioUpdateComponent,
        resolve: {
            anelViario: AnelViarioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AnelViarios'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const anelViarioPopupRoute: Routes = [
    {
        path: 'anel-viario/:id/delete',
        component: AnelViarioDeletePopupComponent,
        resolve: {
            anelViario: AnelViarioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AnelViarios'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
