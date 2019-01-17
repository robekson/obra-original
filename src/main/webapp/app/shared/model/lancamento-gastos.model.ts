import { IObras } from 'app/shared/model//obras.model';

export interface ILancamentoGastos {
    id?: number;
    nomeObra?: string;
    valorDeposito?: number;
    valorDespesa?: number;
    valorSaldo?: number;
    lancamentos?: IObras[];
}

export class LancamentoGastos implements ILancamentoGastos {
    constructor(
        public id?: number,
        public nomeObra?: string,
        public valorDeposito?: number,
        public valorDespesa?: number,
        public valorSaldo?: number,
        public lancamentos?: IObras[]
    ) {}
}
