import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEscolaridade } from 'app/shared/model/escolaridade.model';

type EntityResponseType = HttpResponse<IEscolaridade>;
type EntityArrayResponseType = HttpResponse<IEscolaridade[]>;

@Injectable({ providedIn: 'root' })
export class EscolaridadeService {
    private resourceUrl = SERVER_API_URL + 'api/escolaridades';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/escolaridades';

    constructor(private http: HttpClient) {}

    create(escolaridade: IEscolaridade): Observable<EntityResponseType> {
        return this.http.post<IEscolaridade>(this.resourceUrl, escolaridade, { observe: 'response' });
    }

    update(escolaridade: IEscolaridade): Observable<EntityResponseType> {
        return this.http.put<IEscolaridade>(this.resourceUrl, escolaridade, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IEscolaridade>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEscolaridade[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEscolaridade[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
