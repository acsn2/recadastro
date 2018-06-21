import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRegimePrevidenciario } from 'app/shared/model/regime-previdenciario.model';

type EntityResponseType = HttpResponse<IRegimePrevidenciario>;
type EntityArrayResponseType = HttpResponse<IRegimePrevidenciario[]>;

@Injectable({ providedIn: 'root' })
export class RegimePrevidenciarioService {
    private resourceUrl = SERVER_API_URL + 'api/regime-previdenciarios';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/regime-previdenciarios';

    constructor(private http: HttpClient) {}

    create(regimePrevidenciario: IRegimePrevidenciario): Observable<EntityResponseType> {
        return this.http.post<IRegimePrevidenciario>(this.resourceUrl, regimePrevidenciario, { observe: 'response' });
    }

    update(regimePrevidenciario: IRegimePrevidenciario): Observable<EntityResponseType> {
        return this.http.put<IRegimePrevidenciario>(this.resourceUrl, regimePrevidenciario, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IRegimePrevidenciario>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRegimePrevidenciario[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRegimePrevidenciario[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
