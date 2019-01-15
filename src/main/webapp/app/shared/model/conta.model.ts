import { Moment } from 'moment';

export interface IConta {
    id?: number;
    nome?: string;
    valor?: number;
    dataVencimento?: Moment;
    pagamento?: string;
    nota?: string;
    tipo?: string;
}

export class Conta implements IConta {
    constructor(
        public id?: number,
        public nome?: string,
        public valor?: number,
        public dataVencimento?: Moment,
        public pagamento?: string,
        public nota?: string,
        public tipo?: string
    ) {}
}
