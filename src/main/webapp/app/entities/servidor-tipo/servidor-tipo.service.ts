import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IServidorTipo } from 'app/shared/model/servidor-tipo.model';

type EntityResponseType = HttpResponse<IServidorTipo>;
type EntityArrayResponseType = HttpResponse<IServidorTipo[]>;

@Injectable({ providedIn: 'root' })
export class ServidorTipoService {
    private resourceUrl = SERVER_API_URL + 'api/servidor-tipos';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/servidor-tipos';

    constructor(private http: HttpClient) {}

    create(servidorTipo: IServidorTipo): Observable<EntityResponseType> {
        return this.http.post<IServidorTipo>(this.resourceUrl, servidorTipo, { observe: 'response' });
    }

    update(servidorTipo: IServidorTipo): Observable<EntityResponseType> {
        return this.http.put<IServidorTipo>(this.resourceUrl, servidorTipo, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IServidorTipo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IServidorTipo[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IServidorTipo[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
