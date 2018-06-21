import { IServidor } from 'app/shared/model//servidor.model';

export interface ICidade {
    id?: number;
    codCidade?: number;
    nomeCidade?: string;
    cidade?: IServidor;
}

export class Cidade implements ICidade {
    constructor(public id?: number, public codCidade?: number, public nomeCidade?: string, public cidade?: IServidor) {}
}
