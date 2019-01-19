import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IResumoGasto } from 'app/shared/model/resumo-gasto.model';

type EntityResponseType = HttpResponse<IResumoGasto>;
type EntityArrayResponseType = HttpResponse<IResumoGasto[]>;

@Injectable({ providedIn: 'root' })
export class ResumoGastoService {
    public resourceUrl = SERVER_API_URL + 'api/resumo-gastos';

    constructor(protected http: HttpClient) {}

    create(resumoGasto: IResumoGasto): Observable<EntityResponseType> {
        return this.http.post<IResumoGasto>(this.resourceUrl, resumoGasto, { observe: 'response' });
    }

    update(resumoGasto: IResumoGasto): Observable<EntityResponseType> {
        return this.http.put<IResumoGasto>(this.resourceUrl, resumoGasto, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IResumoGasto>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IResumoGasto[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
