import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IParenteServidor } from 'app/shared/model/parente-servidor.model';

type EntityResponseType = HttpResponse<IParenteServidor>;
type EntityArrayResponseType = HttpResponse<IParenteServidor[]>;

@Injectable({ providedIn: 'root' })
export class ParenteServidorService {
    private resourceUrl = SERVER_API_URL + 'api/parente-servidors';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/parente-servidors';

    constructor(private http: HttpClient) {}

    create(parenteServidor: IParenteServidor): Observable<EntityResponseType> {
        return this.http.post<IParenteServidor>(this.resourceUrl, parenteServidor, { observe: 'response' });
    }

    update(parenteServidor: IParenteServidor): Observable<EntityResponseType> {
        return this.http.put<IParenteServidor>(this.resourceUrl, parenteServidor, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IParenteServidor>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IParenteServidor[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IParenteServidor[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
