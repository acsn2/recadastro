import { IServidor } from 'app/shared/model//servidor.model';

export interface IEscolaridade {
    id?: number;
    codEscolaridade?: number;
    descEscolaridade?: string;
    escolaridade?: IServidor;
}

export class Escolaridade implements IEscolaridade {
    constructor(public id?: number, public codEscolaridade?: number, public descEscolaridade?: string, public escolaridade?: IServidor) {}
}
