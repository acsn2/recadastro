import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { RegimeTrabalho } from 'app/shared/model/regime-trabalho.model';
import { RegimeTrabalhoService } from './regime-trabalho.service';
import { RegimeTrabalhoComponent } from './regime-trabalho.component';
import { RegimeTrabalhoDetailComponent } from './regime-trabalho-detail.component';
import { RegimeTrabalhoUpdateComponent } from './regime-trabalho-update.component';
import { RegimeTrabalhoDeletePopupComponent } from './regime-trabalho-delete-dialog.component';
import { IRegimeTrabalho } from 'app/shared/model/regime-trabalho.model';

@Injectable({ providedIn: 'root' })
export class RegimeTrabalhoResolve implements Resolve<IRegimeTrabalho> {
    constructor(private service: RegimeTrabalhoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((regimeTrabalho: HttpResponse<RegimeTrabalho>) => regimeTrabalho.body);
        }
        return Observable.of(new RegimeTrabalho());
    }
}

export const regimeTrabalhoRoute: Routes = [
    {
        path: 'regime-trabalho',
        component: RegimeTrabalhoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RegimeTrabalhos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'regime-trabalho/:id/view',
        component: RegimeTrabalhoDetailComponent,
        resolve: {
            regimeTrabalho: RegimeTrabalhoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RegimeTrabalhos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'regime-trabalho/new',
        component: RegimeTrabalhoUpdateComponent,
        resolve: {
            regimeTrabalho: RegimeTrabalhoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RegimeTrabalhos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'regime-trabalho/:id/edit',
        component: RegimeTrabalhoUpdateComponent,
        resolve: {
            regimeTrabalho: RegimeTrabalhoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RegimeTrabalhos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const regimeTrabalhoPopupRoute: Routes = [
    {
        path: 'regime-trabalho/:id/delete',
        component: RegimeTrabalhoDeletePopupComponent,
        resolve: {
            regimeTrabalho: RegimeTrabalhoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RegimeTrabalhos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
