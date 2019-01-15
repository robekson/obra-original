import { Moment } from 'moment';
import { IPeriodo } from 'app/shared/model//periodo.model';

export interface IObras {
    id?: number;
    nome?: string;
    local?: string;
    metragem?: number;
    status?: string;
    dataInicio?: Moment;
    dataFim?: Moment;
    obraId?: number;
    obras?: IPeriodo[];
}

export class Obras implements IObras {
    constructor(
        public id?: number,
        public nome?: string,
        public local?: string,
        public metragem?: number,
        public status?: string,
        public dataInicio?: Moment,
        public dataFim?: Moment,
        public obraId?: number,
        public obras?: IPeriodo[]
    ) {}
}
