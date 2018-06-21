import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { Escolaridade } from 'app/shared/model/escolaridade.model';
import { EscolaridadeService } from './escolaridade.service';
import { EscolaridadeComponent } from './escolaridade.component';
import { EscolaridadeDetailComponent } from './escolaridade-detail.component';
import { EscolaridadeUpdateComponent } from './escolaridade-update.component';
import { EscolaridadeDeletePopupComponent } from './escolaridade-delete-dialog.component';
import { IEscolaridade } from 'app/shared/model/escolaridade.model';

@Injectable({ providedIn: 'root' })
export class EscolaridadeResolve implements Resolve<IEscolaridade> {
    constructor(private service: EscolaridadeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((escolaridade: HttpResponse<Escolaridade>) => escolaridade.body);
        }
        return Observable.of(new Escolaridade());
    }
}

export const escolaridadeRoute: Routes = [
    {
        path: 'escolaridade',
        component: EscolaridadeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Escolaridades'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'escolaridade/:id/view',
        component: EscolaridadeDetailComponent,
        resolve: {
            escolaridade: EscolaridadeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Escolaridades'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'escolaridade/new',
        component: EscolaridadeUpdateComponent,
        resolve: {
            escolaridade: EscolaridadeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Escolaridades'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'escolaridade/:id/edit',
        component: EscolaridadeUpdateComponent,
        resolve: {
            escolaridade: EscolaridadeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Escolaridades'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const escolaridadePopupRoute: Routes = [
    {
        path: 'escolaridade/:id/delete',
        component: EscolaridadeDeletePopupComponent,
        resolve: {
            escolaridade: EscolaridadeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Escolaridades'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
