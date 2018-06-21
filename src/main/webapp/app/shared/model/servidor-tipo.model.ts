import { IServidor } from 'app/shared/model//servidor.model';

export interface IServidorTipo {
    id?: number;
    numero?: number;
    nome?: string;
    indicativo?: string;
    servidorTipo?: IServidor;
}

export class ServidorTipo implements IServidorTipo {
    constructor(
        public id?: number,
        public numero?: number,
        public nome?: string,
        public indicativo?: string,
        public servidorTipo?: IServidor
    ) {}
}
