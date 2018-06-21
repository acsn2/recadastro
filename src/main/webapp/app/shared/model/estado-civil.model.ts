import { IServidor } from 'app/shared/model//servidor.model';

export interface IEstadoCivil {
    id?: number;
    numEstCivil?: number;
    descEstCivil?: string;
    estadoCivil?: IServidor;
}

export class EstadoCivil implements IEstadoCivil {
    constructor(public id?: number, public numEstCivil?: number, public descEstCivil?: string, public estadoCivil?: IServidor) {}
}
