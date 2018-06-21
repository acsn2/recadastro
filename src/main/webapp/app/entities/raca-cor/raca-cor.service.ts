import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRacaCor } from 'app/shared/model/raca-cor.model';

type EntityResponseType = HttpResponse<IRacaCor>;
type EntityArrayResponseType = HttpResponse<IRacaCor[]>;

@Injectable({ providedIn: 'root' })
export class RacaCorService {
    private resourceUrl = SERVER_API_URL + 'api/raca-cors';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/raca-cors';

    constructor(private http: HttpClient) {}

    create(racaCor: IRacaCor): Observable<EntityResponseType> {
        return this.http.post<IRacaCor>(this.resourceUrl, racaCor, { observe: 'response' });
    }

    update(racaCor: IRacaCor): Observable<EntityResponseType> {
        return this.http.put<IRacaCor>(this.resourceUrl, racaCor, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IRacaCor>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRacaCor[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRacaCor[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
