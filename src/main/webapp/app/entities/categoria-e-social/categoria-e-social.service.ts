import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICategoriaESocial } from 'app/shared/model/categoria-e-social.model';

type EntityResponseType = HttpResponse<ICategoriaESocial>;
type EntityArrayResponseType = HttpResponse<ICategoriaESocial[]>;

@Injectable({ providedIn: 'root' })
export class CategoriaESocialService {
    private resourceUrl = SERVER_API_URL + 'api/categoria-e-socials';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/categoria-e-socials';

    constructor(private http: HttpClient) {}

    create(categoriaESocial: ICategoriaESocial): Observable<EntityResponseType> {
        return this.http.post<ICategoriaESocial>(this.resourceUrl, categoriaESocial, { observe: 'response' });
    }

    update(categoriaESocial: ICategoriaESocial): Observable<EntityResponseType> {
        return this.http.put<ICategoriaESocial>(this.resourceUrl, categoriaESocial, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICategoriaESocial>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICategoriaESocial[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICategoriaESocial[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
