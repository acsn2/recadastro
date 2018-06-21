import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { Dependente } from 'app/shared/model/dependente.model';
import { DependenteService } from './dependente.service';
import { DependenteComponent } from './dependente.component';
import { DependenteDetailComponent } from './dependente-detail.component';
import { DependenteUpdateComponent } from './dependente-update.component';
import { DependenteDeletePopupComponent } from './dependente-delete-dialog.component';
import { IDependente } from 'app/shared/model/dependente.model';

@Injectable({ providedIn: 'root' })
export class DependenteResolve implements Resolve<IDependente> {
    constructor(private service: DependenteService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((dependente: HttpResponse<Dependente>) => dependente.body);
        }
        return Observable.of(new Dependente());
    }
}

export const dependenteRoute: Routes = [
    {
        path: 'dependente',
        component: DependenteComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Dependentes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dependente/:id/view',
        component: DependenteDetailComponent,
        resolve: {
            dependente: DependenteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Dependentes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dependente/new',
        component: DependenteUpdateComponent,
        resolve: {
            dependente: DependenteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Dependentes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dependente/:id/edit',
        component: DependenteUpdateComponent,
        resolve: {
            dependente: DependenteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Dependentes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dependentePopupRoute: Routes = [
    {
        path: 'dependente/:id/delete',
        component: DependenteDeletePopupComponent,
        resolve: {
            dependente: DependenteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Dependentes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
