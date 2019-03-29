import { Moment } from 'moment';
import { IGasto } from 'app/shared/model//gasto.model';

export const enum TipoCorretagem {
    Tipo1 = 'Tipo1',
    Tipo2 = 'Tipo2',
    Tipo3 = 'Tipo3'
}

export const enum StatusObra {
    ANDAMENTO = 'ANDAMENTO',
    FINALIZADA = 'FINALIZADA',
    PARADA = 'PARADA'
}

export interface IObra {
    id?: number;
    nome?: string;
    endereco?: string;
    metragem?: number;
    valorTerreno?: number;
    valorEscritura?: number;
    valorEscrituraCompra?: number;
    valorCorretagemVenda?: number;
    valorFiscal?: number;
    porcentagemCorretagem?: number;
    tipoCorretagem?: TipoCorretagem;
    status?: StatusObra;
    dataInicio?: Moment;
    dataFim?: Moment;
    resumoGastoId?: number;
    gastos?: IGasto[];
}

export class Obra implements IObra {
    constructor(
        public id?: number,
        public nome?: string,
        public endereco?: string,
        public metragem?: number,
        public valorTerreno?: number,
        public valorEscritura?: number,
        public valorEscrituraCompra?: number,
        public valorCorretagemVenda?: number,
        public valorFiscal?: number,
        public porcentagemCorretagem?: number,
        public tipoCorretagem?: TipoCorretagem,
        public status?: StatusObra,
        public dataInicio?: Moment,
        public dataFim?: Moment,
        public resumoGastoId?: number,
        public gastos?: IGasto[]
    ) {}
}
