import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDependente } from 'app/shared/model/dependente.model';

type EntityResponseType = HttpResponse<IDependente>;
type EntityArrayResponseType = HttpResponse<IDependente[]>;

@Injectable({ providedIn: 'root' })
export class DependenteService {
    private resourceUrl = SERVER_API_URL + 'api/dependentes';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/dependentes';

    constructor(private http: HttpClient) {}

    create(dependente: IDependente): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(dependente);
        return this.http
            .post<IDependente>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    update(dependente: IDependente): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(dependente);
        return this.http
            .put<IDependente>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IDependente>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IDependente[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IDependente[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res));
    }

    private convertDateFromClient(dependente: IDependente): IDependente {
        const copy: IDependente = Object.assign({}, dependente, {
            datNac: dependente.datNac != null && dependente.datNac.isValid() ? dependente.datNac.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.datNac = res.body.datNac != null ? moment(res.body.datNac) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((dependente: IDependente) => {
            dependente.datNac = dependente.datNac != null ? moment(dependente.datNac) : null;
        });
        return res;
    }
}
