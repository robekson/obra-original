export interface ILancamentoGastos {
    id?: number;
    nomeObra?: string;
    valorDeposito?: number;
    valorDespesa?: number;
    valorSaldo?: number;
}

export class LancamentoGastos implements ILancamentoGastos {
    constructor(
        public id?: number,
        public nomeObra?: string,
        public valorDeposito?: number,
        public valorDespesa?: number,
        public valorSaldo?: number
    ) {}
}
