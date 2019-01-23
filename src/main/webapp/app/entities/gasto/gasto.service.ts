import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IGasto, IResumoGasto } from 'app/shared/model/gasto.model';

type EntityResponseType = HttpResponse<IGasto>;
type EntityArrayResponseType = HttpResponse<IGasto[]>;

type EntityResponseTypeResumo = HttpResponse<IResumoGasto>;

@Injectable({ providedIn: 'root' })
export class GastoService {
    public resourceUrl = SERVER_API_URL + 'api/gastos';
    public resourceUrlResumo = SERVER_API_URL + 'api/resumoConta';

    constructor(protected http: HttpClient) {}

    create(gasto: IGasto): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(gasto);
        return this.http
            .post<IGasto>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(gasto: IGasto): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(gasto);
        return this.http
            .put<IGasto>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IGasto>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IGasto[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

   /* resumo(req?: any): Observable<any> {
        const options = createRequestOption(req);      
        return this.http.get<any>(this.resourceUrlResumo, { params: options, observe: 'response' });
    }*/
    
    
    
    resumo(req?: any): Observable<any> {
        return this.http.get(this.resourceUrlResumo)
                        .pipe(map(this.extractData),this.handleError);
    }
    
    
    private extractData(res: Response) {
        let body = res.json();
        return body || { };
   }
    
    private handleError (error: Response | any) {
        // In a real world app, you might use a remote logging infrastructure
        let errMsg: string;
        if (error instanceof Response) {
          const body = error.json() || '';
          const err =  JSON.stringify(body);
          errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
        } else {
          errMsg = error.message ? error.message : error.toString();
        }
        console.error(errMsg);
        return Observable.throw(errMsg);
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(gasto: IGasto): IGasto {
        const copy: IGasto = Object.assign({}, gasto, {
            dataVencimento:
                gasto.dataVencimento != null && gasto.dataVencimento.isValid() ? gasto.dataVencimento.format(DATE_FORMAT) : null,
            mesAno: gasto.mesAno != null && gasto.mesAno.isValid() ? gasto.mesAno.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dataVencimento = res.body.dataVencimento != null ? moment(res.body.dataVencimento) : null;
            res.body.mesAno = res.body.mesAno != null ? moment(res.body.mesAno) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((gasto: IGasto) => {
                gasto.dataVencimento = gasto.dataVencimento != null ? moment(gasto.dataVencimento) : null;
                gasto.mesAno = gasto.mesAno != null ? moment(gasto.mesAno) : null;
            });
        }
        return res;
    }

    protected convertDateArrayFromServer2(res: EntityResponseTypeResumo): EntityResponseTypeResumo {
        if (res.body) {
            // res.body.forEach((resumo: IResumoGasto) => {
            // gasto.dataVencimento = gasto.dataVencimento != null ? moment(gasto.dataVencimento) : null;
            // gasto.mesAno = gasto.mesAno != null ? moment(gasto.mesAno) : null;
            //  });
        }
        return res;
    }
}
