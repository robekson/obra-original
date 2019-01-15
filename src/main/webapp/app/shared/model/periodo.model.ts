import { Moment } from 'moment';

export interface IPeriodo {
    id?: number;
    idConta?: string;
    data?: Moment;
    obrasId?: number;
    periodoId?: number;
}

export class Periodo implements IPeriodo {
    constructor(public id?: number, public idConta?: string, public data?: Moment, public obrasId?: number, public periodoId?: number) {}
}
