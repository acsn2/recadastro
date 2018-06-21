import { IServidor } from 'app/shared/model//servidor.model';

export interface IPais {
    id?: number;
    codPais?: number;
    nomePais?: string;
    pais?: IServidor;
}

export class Pais implements IPais {
    constructor(public id?: number, public codPais?: number, public nomePais?: string, public pais?: IServidor) {}
}
