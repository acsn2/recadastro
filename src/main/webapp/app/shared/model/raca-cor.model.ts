import { IServidor } from 'app/shared/model//servidor.model';

export interface IRacaCor {
    id?: number;
    codRacaCor?: number;
    descRacaCor?: string;
    racaCor?: IServidor;
}

export class RacaCor implements IRacaCor {
    constructor(public id?: number, public codRacaCor?: number, public descRacaCor?: string, public racaCor?: IServidor) {}
}
