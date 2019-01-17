import { Moment } from 'moment';

export const enum Pago {
    SIM = 'SIM',
    NAO = 'NAO'
}

export const enum NotaFiscal {
    SIM = 'SIM',
    NAO = 'NAO'
}

export const enum TipoConta {
    MAO_DE_OBRA = 'MAO_DE_OBRA',
    MATERIAIS = 'MATERIAIS',
    DECORACAO = 'DECORACAO',
    DOCUMENTACAO = 'DOCUMENTACAO'
}

export interface IConta {
    id?: number;
    nome?: string;
    valor?: number;
    dataVencimento?: Moment;
    pagamento?: Pago;
    nota?: NotaFiscal;
    tipo?: TipoConta;
    parcelado?: number;
}

export class Conta implements IConta {
    constructor(
        public id?: number,
        public nome?: string,
        public valor?: number,
        public dataVencimento?: Moment,
        public pagamento?: Pago,
        public nota?: NotaFiscal,
        public tipo?: TipoConta,
        public parcelado?: number
    ) {}
}
