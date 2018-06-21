import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { ParenteServidor } from 'app/shared/model/parente-servidor.model';
import { ParenteServidorService } from './parente-servidor.service';
import { ParenteServidorComponent } from './parente-servidor.component';
import { ParenteServidorDetailComponent } from './parente-servidor-detail.component';
import { ParenteServidorUpdateComponent } from './parente-servidor-update.component';
import { ParenteServidorDeletePopupComponent } from './parente-servidor-delete-dialog.component';
import { IParenteServidor } from 'app/shared/model/parente-servidor.model';

@Injectable({ providedIn: 'root' })
export class ParenteServidorResolve implements Resolve<IParenteServidor> {
    constructor(private service: ParenteServidorService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((parenteServidor: HttpResponse<ParenteServidor>) => parenteServidor.body);
        }
        return Observable.of(new ParenteServidor());
    }
}

export const parenteServidorRoute: Routes = [
    {
        path: 'parente-servidor',
        component: ParenteServidorComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ParenteServidors'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'parente-servidor/:id/view',
        component: ParenteServidorDetailComponent,
        resolve: {
            parenteServidor: ParenteServidorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ParenteServidors'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'parente-servidor/new',
        component: ParenteServidorUpdateComponent,
        resolve: {
            parenteServidor: ParenteServidorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ParenteServidors'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'parente-servidor/:id/edit',
        component: ParenteServidorUpdateComponent,
        resolve: {
            parenteServidor: ParenteServidorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ParenteServidors'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const parenteServidorPopupRoute: Routes = [
    {
        path: 'parente-servidor/:id/delete',
        component: ParenteServidorDeletePopupComponent,
        resolve: {
            parenteServidor: ParenteServidorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ParenteServidors'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
