import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEstadoCivil } from 'app/shared/model/estado-civil.model';

type EntityResponseType = HttpResponse<IEstadoCivil>;
type EntityArrayResponseType = HttpResponse<IEstadoCivil[]>;

@Injectable({ providedIn: 'root' })
export class EstadoCivilService {
    private resourceUrl = SERVER_API_URL + 'api/estado-civils';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/estado-civils';

    constructor(private http: HttpClient) {}

    create(estadoCivil: IEstadoCivil): Observable<EntityResponseType> {
        return this.http.post<IEstadoCivil>(this.resourceUrl, estadoCivil, { observe: 'response' });
    }

    update(estadoCivil: IEstadoCivil): Observable<EntityResponseType> {
        return this.http.put<IEstadoCivil>(this.resourceUrl, estadoCivil, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IEstadoCivil>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEstadoCivil[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEstadoCivil[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
