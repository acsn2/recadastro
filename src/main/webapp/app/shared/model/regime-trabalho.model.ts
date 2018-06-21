import { IServidor } from 'app/shared/model//servidor.model';

export interface IRegimeTrabalho {
    id?: number;
    codRegTrab?: number;
    descRegTrab?: string;
    regimeTrabalho?: IServidor;
}

export class RegimeTrabalho implements IRegimeTrabalho {
    constructor(public id?: number, public codRegTrab?: number, public descRegTrab?: string, public regimeTrabalho?: IServidor) {}
}
