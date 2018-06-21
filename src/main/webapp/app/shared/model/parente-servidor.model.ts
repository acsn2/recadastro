import { IServidor } from 'app/shared/model//servidor.model';

export interface IParenteServidor {
    id?: number;
    fkParenteServidor?: number;
    matriculaParente?: number;
    fkGrauParentesco?: string;
    servidor?: IServidor;
}

export class ParenteServidor implements IParenteServidor {
    constructor(
        public id?: number,
        public fkParenteServidor?: number,
        public matriculaParente?: number,
        public fkGrauParentesco?: string,
        public servidor?: IServidor
    ) {}
}
