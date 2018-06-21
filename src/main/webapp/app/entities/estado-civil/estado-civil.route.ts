import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { EstadoCivil } from 'app/shared/model/estado-civil.model';
import { EstadoCivilService } from './estado-civil.service';
import { EstadoCivilComponent } from './estado-civil.component';
import { EstadoCivilDetailComponent } from './estado-civil-detail.component';
import { EstadoCivilUpdateComponent } from './estado-civil-update.component';
import { EstadoCivilDeletePopupComponent } from './estado-civil-delete-dialog.component';
import { IEstadoCivil } from 'app/shared/model/estado-civil.model';

@Injectable({ providedIn: 'root' })
export class EstadoCivilResolve implements Resolve<IEstadoCivil> {
    constructor(private service: EstadoCivilService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((estadoCivil: HttpResponse<EstadoCivil>) => estadoCivil.body);
        }
        return Observable.of(new EstadoCivil());
    }
}

export const estadoCivilRoute: Routes = [
    {
        path: 'estado-civil',
        component: EstadoCivilComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EstadoCivils'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'estado-civil/:id/view',
        component: EstadoCivilDetailComponent,
        resolve: {
            estadoCivil: EstadoCivilResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EstadoCivils'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'estado-civil/new',
        component: EstadoCivilUpdateComponent,
        resolve: {
            estadoCivil: EstadoCivilResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EstadoCivils'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'estado-civil/:id/edit',
        component: EstadoCivilUpdateComponent,
        resolve: {
            estadoCivil: EstadoCivilResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EstadoCivils'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const estadoCivilPopupRoute: Routes = [
    {
        path: 'estado-civil/:id/delete',
        component: EstadoCivilDeletePopupComponent,
        resolve: {
            estadoCivil: EstadoCivilResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EstadoCivils'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
