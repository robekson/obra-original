export interface IResumoGasto {
    id?: number;
    nomeObra?: string;
    valorDeposito?: number;
    valorDespesa?: number;
    valorSaldo?: number;
}

export class ResumoGasto implements IResumoGasto {
    constructor(
        public id?: number,
        public nomeObra?: string,
        public valorDeposito?: number,
        public valorDespesa?: number,
        public valorSaldo?: number
    ) {}
}
