import { IServidor } from 'app/shared/model//servidor.model';

export interface IOrgao {
    id?: number;
    numOrgao?: number;
    descOrgao?: string;
    orgao?: IServidor;
}

export class Orgao implements IOrgao {
    constructor(public id?: number, public numOrgao?: number, public descOrgao?: string, public orgao?: IServidor) {}
}
