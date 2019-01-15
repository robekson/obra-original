import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILancamentoGastos } from 'app/shared/model/lancamento-gastos.model';

type EntityResponseType = HttpResponse<ILancamentoGastos>;
type EntityArrayResponseType = HttpResponse<ILancamentoGastos[]>;

@Injectable({ providedIn: 'root' })
export class LancamentoGastosService {
    public resourceUrl = SERVER_API_URL + 'api/lancamento-gastos';

    constructor(protected http: HttpClient) {}

    create(lancamentoGastos: ILancamentoGastos): Observable<EntityResponseType> {
        return this.http.post<ILancamentoGastos>(this.resourceUrl, lancamentoGastos, { observe: 'response' });
    }

    update(lancamentoGastos: ILancamentoGastos): Observable<EntityResponseType> {
        return this.http.put<ILancamentoGastos>(this.resourceUrl, lancamentoGastos, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ILancamentoGastos>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ILancamentoGastos[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
