import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAnelViario } from 'app/shared/model/anel-viario.model';

type EntityResponseType = HttpResponse<IAnelViario>;
type EntityArrayResponseType = HttpResponse<IAnelViario[]>;

@Injectable({ providedIn: 'root' })
export class AnelViarioService {
    private resourceUrl = SERVER_API_URL + 'api/anel-viarios';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/anel-viarios';

    constructor(private http: HttpClient) {}

    create(anelViario: IAnelViario): Observable<EntityResponseType> {
        return this.http.post<IAnelViario>(this.resourceUrl, anelViario, { observe: 'response' });
    }

    update(anelViario: IAnelViario): Observable<EntityResponseType> {
        return this.http.put<IAnelViario>(this.resourceUrl, anelViario, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAnelViario>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAnelViario[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAnelViario[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
