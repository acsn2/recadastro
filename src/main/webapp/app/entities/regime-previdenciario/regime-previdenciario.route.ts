import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { RegimePrevidenciario } from 'app/shared/model/regime-previdenciario.model';
import { RegimePrevidenciarioService } from './regime-previdenciario.service';
import { RegimePrevidenciarioComponent } from './regime-previdenciario.component';
import { RegimePrevidenciarioDetailComponent } from './regime-previdenciario-detail.component';
import { RegimePrevidenciarioUpdateComponent } from './regime-previdenciario-update.component';
import { RegimePrevidenciarioDeletePopupComponent } from './regime-previdenciario-delete-dialog.component';
import { IRegimePrevidenciario } from 'app/shared/model/regime-previdenciario.model';

@Injectable({ providedIn: 'root' })
export class RegimePrevidenciarioResolve implements Resolve<IRegimePrevidenciario> {
    constructor(private service: RegimePrevidenciarioService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((regimePrevidenciario: HttpResponse<RegimePrevidenciario>) => regimePrevidenciario.body);
        }
        return Observable.of(new RegimePrevidenciario());
    }
}

export const regimePrevidenciarioRoute: Routes = [
    {
        path: 'regime-previdenciario',
        component: RegimePrevidenciarioComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RegimePrevidenciarios'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'regime-previdenciario/:id/view',
        component: RegimePrevidenciarioDetailComponent,
        resolve: {
            regimePrevidenciario: RegimePrevidenciarioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RegimePrevidenciarios'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'regime-previdenciario/new',
        component: RegimePrevidenciarioUpdateComponent,
        resolve: {
            regimePrevidenciario: RegimePrevidenciarioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RegimePrevidenciarios'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'regime-previdenciario/:id/edit',
        component: RegimePrevidenciarioUpdateComponent,
        resolve: {
            regimePrevidenciario: RegimePrevidenciarioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RegimePrevidenciarios'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const regimePrevidenciarioPopupRoute: Routes = [
    {
        path: 'regime-previdenciario/:id/delete',
        component: RegimePrevidenciarioDeletePopupComponent,
        resolve: {
            regimePrevidenciario: RegimePrevidenciarioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RegimePrevidenciarios'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
