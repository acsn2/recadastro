import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { Cidade } from 'app/shared/model/cidade.model';
import { CidadeService } from './cidade.service';
import { CidadeComponent } from './cidade.component';
import { CidadeDetailComponent } from './cidade-detail.component';
import { CidadeUpdateComponent } from './cidade-update.component';
import { CidadeDeletePopupComponent } from './cidade-delete-dialog.component';
import { ICidade } from 'app/shared/model/cidade.model';

@Injectable({ providedIn: 'root' })
export class CidadeResolve implements Resolve<ICidade> {
    constructor(private service: CidadeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((cidade: HttpResponse<Cidade>) => cidade.body);
        }
        return Observable.of(new Cidade());
    }
}

export const cidadeRoute: Routes = [
    {
        path: 'cidade',
        component: CidadeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Cidades'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cidade/:id/view',
        component: CidadeDetailComponent,
        resolve: {
            cidade: CidadeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Cidades'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cidade/new',
        component: CidadeUpdateComponent,
        resolve: {
            cidade: CidadeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Cidades'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cidade/:id/edit',
        component: CidadeUpdateComponent,
        resolve: {
            cidade: CidadeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Cidades'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const cidadePopupRoute: Routes = [
    {
        path: 'cidade/:id/delete',
        component: CidadeDeletePopupComponent,
        resolve: {
            cidade: CidadeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Cidades'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
