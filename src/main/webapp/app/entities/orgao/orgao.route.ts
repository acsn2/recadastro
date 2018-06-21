import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { Orgao } from 'app/shared/model/orgao.model';
import { OrgaoService } from './orgao.service';
import { OrgaoComponent } from './orgao.component';
import { OrgaoDetailComponent } from './orgao-detail.component';
import { OrgaoUpdateComponent } from './orgao-update.component';
import { OrgaoDeletePopupComponent } from './orgao-delete-dialog.component';
import { IOrgao } from 'app/shared/model/orgao.model';

@Injectable({ providedIn: 'root' })
export class OrgaoResolve implements Resolve<IOrgao> {
    constructor(private service: OrgaoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((orgao: HttpResponse<Orgao>) => orgao.body);
        }
        return Observable.of(new Orgao());
    }
}

export const orgaoRoute: Routes = [
    {
        path: 'orgao',
        component: OrgaoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Orgaos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'orgao/:id/view',
        component: OrgaoDetailComponent,
        resolve: {
            orgao: OrgaoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Orgaos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'orgao/new',
        component: OrgaoUpdateComponent,
        resolve: {
            orgao: OrgaoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Orgaos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'orgao/:id/edit',
        component: OrgaoUpdateComponent,
        resolve: {
            orgao: OrgaoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Orgaos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const orgaoPopupRoute: Routes = [
    {
        path: 'orgao/:id/delete',
        component: OrgaoDeletePopupComponent,
        resolve: {
            orgao: OrgaoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Orgaos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
