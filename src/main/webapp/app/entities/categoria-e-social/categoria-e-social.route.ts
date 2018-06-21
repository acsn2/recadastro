import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { CategoriaESocial } from 'app/shared/model/categoria-e-social.model';
import { CategoriaESocialService } from './categoria-e-social.service';
import { CategoriaESocialComponent } from './categoria-e-social.component';
import { CategoriaESocialDetailComponent } from './categoria-e-social-detail.component';
import { CategoriaESocialUpdateComponent } from './categoria-e-social-update.component';
import { CategoriaESocialDeletePopupComponent } from './categoria-e-social-delete-dialog.component';
import { ICategoriaESocial } from 'app/shared/model/categoria-e-social.model';

@Injectable({ providedIn: 'root' })
export class CategoriaESocialResolve implements Resolve<ICategoriaESocial> {
    constructor(private service: CategoriaESocialService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((categoriaESocial: HttpResponse<CategoriaESocial>) => categoriaESocial.body);
        }
        return Observable.of(new CategoriaESocial());
    }
}

export const categoriaESocialRoute: Routes = [
    {
        path: 'categoria-e-social',
        component: CategoriaESocialComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CategoriaESocials'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'categoria-e-social/:id/view',
        component: CategoriaESocialDetailComponent,
        resolve: {
            categoriaESocial: CategoriaESocialResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CategoriaESocials'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'categoria-e-social/new',
        component: CategoriaESocialUpdateComponent,
        resolve: {
            categoriaESocial: CategoriaESocialResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CategoriaESocials'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'categoria-e-social/:id/edit',
        component: CategoriaESocialUpdateComponent,
        resolve: {
            categoriaESocial: CategoriaESocialResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CategoriaESocials'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const categoriaESocialPopupRoute: Routes = [
    {
        path: 'categoria-e-social/:id/delete',
        component: CategoriaESocialDeletePopupComponent,
        resolve: {
            categoriaESocial: CategoriaESocialResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CategoriaESocials'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
