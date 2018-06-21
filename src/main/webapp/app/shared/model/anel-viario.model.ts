import { IServidor } from 'app/shared/model//servidor.model';

export interface IAnelViario {
    id?: number;
    codAnel?: number;
    descAnel?: string;
    task?: IServidor;
}

export class AnelViario implements IAnelViario {
    constructor(public id?: number, public codAnel?: number, public descAnel?: string, public task?: IServidor) {}
}
