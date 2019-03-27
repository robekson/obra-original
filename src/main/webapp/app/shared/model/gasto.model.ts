import { Moment } from 'moment';
import { IObra } from 'app/shared/model/obra.model';

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
    DOCUMENTACAO = 'DOCUMENTACAO',
    INVESTIMENTO_DEPOSITO = 'INVESTIMENTO_DEPOSITO'
}


export interface ITipoContaDto {
        id?: number;
        descricao?: string;
        valorDespesa?: number;
}

export class TipoContaDto  implements ITipoContaDto {
    constructor(
        public id?: number,
        public descricao?: string,
        public valorDespesa?: number
    ) {}
}
    
    
export interface MesAno {
    data?: string;
}

export interface IGasto {
    id?: number;
    nome?: string;
    valor?: number;
    dataVencimento?: Moment;
    pagamento?: Pago;
    nota?: NotaFiscal;
    tipo?: TipoConta;
    parcelado?: number;
    mesAno?: Moment;
    obraId?: number;
}

export interface IResumoGasto {
    id?: number;
    honorarioAdministracao?: number;
    valorDeposito?: number;
    valorCaixa?: number;
    valorMetroQuadrado?: number;
    quantidadeComNota?: number;
    quantidadeSemNota?: number;
    mesAnoFormatado?: string;
    mesAnoFormatadoExtenso?: string;
    mesAno?: Moment;
    despesaComNota?: number;
    despesaSemNota?: number;
    despesaGeralSubTotal?: number;
    totalDespesas?: number;
    obraDTO?: IObra;
}

export class ResumoGasto implements IResumoGasto {
    constructor(
        public id?: number,
        public honorarioAdministracao?: number,
        public valorDeposito?: number,
        public valorCaixa?: number,
        public valorMetroQuadrado?: number,
        public quantidadeComNota?: number,
        public quantidadeSemNota?: number,
        public mesAnoFormatado?: string,
        public mesAnoFormatadoExtenso?: string,
        public mesAno?: Moment,
        public despesaComNota?: number,
        public despesaSemNota?: number,
        public despesaGeralSubTotal?: number,
        public totalDespesas?: number,
        public obraDTO?: IObra
    ) {}
}

export class Gasto implements IGasto {
    constructor(
        public id?: number,
        public nome?: string,
        public valor?: number,
        public dataVencimento?: Moment,
        public pagamento?: Pago,
        public nota?: NotaFiscal,
        public tipo?: TipoConta,
        public parcelado?: number,
        public mesAno?: Moment,
        public obraId?: number
    ) {}
}
