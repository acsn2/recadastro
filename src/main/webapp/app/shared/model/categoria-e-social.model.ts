import { IServidor } from 'app/shared/model//servidor.model';

export interface ICategoriaESocial {
    id?: number;
    codCategoria?: number;
    descCategoria?: string;
    grupoCat?: string;
    categoriaESocial?: IServidor;
}

export class CategoriaESocial implements ICategoriaESocial {
    constructor(
        public id?: number,
        public codCategoria?: number,
        public descCategoria?: string,
        public grupoCat?: string,
        public categoriaESocial?: IServidor
    ) {}
}
