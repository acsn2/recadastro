import { IServidor } from 'app/shared/model//servidor.model';

export interface IRegimePrevidenciario {
    id?: number;
    codRegPrev?: number;
    descRegPrev?: string;
    regimePrevidenciario?: IServidor;
}

export class RegimePrevidenciario implements IRegimePrevidenciario {
    constructor(public id?: number, public codRegPrev?: number, public descRegPrev?: string, public regimePrevidenciario?: IServidor) {}
}
