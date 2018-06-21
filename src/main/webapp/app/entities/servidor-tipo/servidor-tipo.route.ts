import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { ServidorTipo } from 'app/shared/model/servidor-tipo.model';
import { ServidorTipoService } from './servidor-tipo.service';
import { ServidorTipoComponent } from './servidor-tipo.component';
import { ServidorTipoDetailComponent } from './servidor-tipo-detail.component';
import { ServidorTipoUpdateComponent } from './servidor-tipo-update.component';
import { ServidorTipoDeletePopupComponent } from './servidor-tipo-delete-dialog.component';
import { IServidorTipo } from 'app/shared/model/servidor-tipo.model';

@Injectable({ providedIn: 'root' })
export class ServidorTipoResolve implements Resolve<IServidorTipo> {
    constructor(private service: ServidorTipoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((servidorTipo: HttpResponse<ServidorTipo>) => servidorTipo.body);
        }
        return Observable.of(new ServidorTipo());
    }
}

export const servidorTipoRoute: Routes = [
    {
        path: 'servidor-tipo',
        component: ServidorTipoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ServidorTipos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'servidor-tipo/:id/view',
        component: ServidorTipoDetailComponent,
        resolve: {
            servidorTipo: ServidorTipoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ServidorTipos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'servidor-tipo/new',
        component: ServidorTipoUpdateComponent,
        resolve: {
            servidorTipo: ServidorTipoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ServidorTipos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'servidor-tipo/:id/edit',
        component: ServidorTipoUpdateComponent,
        resolve: {
            servidorTipo: ServidorTipoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ServidorTipos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const servidorTipoPopupRoute: Routes = [
    {
        path: 'servidor-tipo/:id/delete',
        component: ServidorTipoDeletePopupComponent,
        resolve: {
            servidorTipo: ServidorTipoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ServidorTipos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
