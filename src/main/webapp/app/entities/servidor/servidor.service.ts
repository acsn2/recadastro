import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IServidor } from 'app/shared/model/servidor.model';

type EntityResponseType = HttpResponse<IServidor>;
type EntityArrayResponseType = HttpResponse<IServidor[]>;

@Injectable({ providedIn: 'root' })
export class ServidorService {
    private resourceUrl = SERVER_API_URL + 'api/servidors';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/servidors';

    constructor(private http: HttpClient) {}

    create(servidor: IServidor): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(servidor);
        return this.http
            .post<IServidor>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    update(servidor: IServidor): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(servidor);
        return this.http
            .put<IServidor>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IServidor>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IServidor[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IServidor[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res));
    }

    private convertDateFromClient(servidor: IServidor): IServidor {
        const copy: IServidor = Object.assign({}, servidor, {
            datNascimento:
                servidor.datNascimento != null && servidor.datNascimento.isValid() ? servidor.datNascimento.format(DATE_FORMAT) : null,
            rgDataExp: servidor.rgDataExp != null && servidor.rgDataExp.isValid() ? servidor.rgDataExp.format(DATE_FORMAT) : null,
            ctpsEmissao: servidor.ctpsEmissao != null && servidor.ctpsEmissao.isValid() ? servidor.ctpsEmissao.format(DATE_FORMAT) : null,
            orclDtaExp: servidor.orclDtaExp != null && servidor.orclDtaExp.isValid() ? servidor.orclDtaExp.format(DATE_FORMAT) : null,
            orclDtaValid:
                servidor.orclDtaValid != null && servidor.orclDtaValid.isValid() ? servidor.orclDtaValid.format(DATE_FORMAT) : null,
            datExpedRIC: servidor.datExpedRIC != null && servidor.datExpedRIC.isValid() ? servidor.datExpedRIC.format(DATE_FORMAT) : null,
            cnhDatValidade:
                servidor.cnhDatValidade != null && servidor.cnhDatValidade.isValid() ? servidor.cnhDatValidade.format(DATE_FORMAT) : null,
            cnhDatExped: servidor.cnhDatExped != null && servidor.cnhDatExped.isValid() ? servidor.cnhDatExped.format(DATE_FORMAT) : null,
            cnhDatPrimHab:
                servidor.cnhDatPrimHab != null && servidor.cnhDatPrimHab.isValid() ? servidor.cnhDatPrimHab.format(DATE_FORMAT) : null,
            datExpeRNE: servidor.datExpeRNE != null && servidor.datExpeRNE.isValid() ? servidor.datExpeRNE.format(DATE_FORMAT) : null,
            datChegada: servidor.datChegada != null && servidor.datChegada.isValid() ? servidor.datChegada.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.datNascimento = res.body.datNascimento != null ? moment(res.body.datNascimento) : null;
        res.body.rgDataExp = res.body.rgDataExp != null ? moment(res.body.rgDataExp) : null;
        res.body.ctpsEmissao = res.body.ctpsEmissao != null ? moment(res.body.ctpsEmissao) : null;
        res.body.orclDtaExp = res.body.orclDtaExp != null ? moment(res.body.orclDtaExp) : null;
        res.body.orclDtaValid = res.body.orclDtaValid != null ? moment(res.body.orclDtaValid) : null;
        res.body.datExpedRIC = res.body.datExpedRIC != null ? moment(res.body.datExpedRIC) : null;
        res.body.cnhDatValidade = res.body.cnhDatValidade != null ? moment(res.body.cnhDatValidade) : null;
        res.body.cnhDatExped = res.body.cnhDatExped != null ? moment(res.body.cnhDatExped) : null;
        res.body.cnhDatPrimHab = res.body.cnhDatPrimHab != null ? moment(res.body.cnhDatPrimHab) : null;
        res.body.datExpeRNE = res.body.datExpeRNE != null ? moment(res.body.datExpeRNE) : null;
        res.body.datChegada = res.body.datChegada != null ? moment(res.body.datChegada) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((servidor: IServidor) => {
            servidor.datNascimento = servidor.datNascimento != null ? moment(servidor.datNascimento) : null;
            servidor.rgDataExp = servidor.rgDataExp != null ? moment(servidor.rgDataExp) : null;
            servidor.ctpsEmissao = servidor.ctpsEmissao != null ? moment(servidor.ctpsEmissao) : null;
            servidor.orclDtaExp = servidor.orclDtaExp != null ? moment(servidor.orclDtaExp) : null;
            servidor.orclDtaValid = servidor.orclDtaValid != null ? moment(servidor.orclDtaValid) : null;
            servidor.datExpedRIC = servidor.datExpedRIC != null ? moment(servidor.datExpedRIC) : null;
            servidor.cnhDatValidade = servidor.cnhDatValidade != null ? moment(servidor.cnhDatValidade) : null;
            servidor.cnhDatExped = servidor.cnhDatExped != null ? moment(servidor.cnhDatExped) : null;
            servidor.cnhDatPrimHab = servidor.cnhDatPrimHab != null ? moment(servidor.cnhDatPrimHab) : null;
            servidor.datExpeRNE = servidor.datExpeRNE != null ? moment(servidor.datExpeRNE) : null;
            servidor.datChegada = servidor.datChegada != null ? moment(servidor.datChegada) : null;
        });
        return res;
    }
}
