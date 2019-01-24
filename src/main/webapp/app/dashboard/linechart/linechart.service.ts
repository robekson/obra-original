import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { SERVER_API_URL } from 'app/app.constants';
import { Observable } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { createRequestOption } from 'app/shared';
import { IResumoGasto } from 'app/shared/model/gasto.model';

type EntityResponseTypeResumo = HttpResponse<IResumoGasto[]>;

@Injectable({
    providedIn: 'root'
})
export class LinechartService {
    public resourceUrl = SERVER_API_URL + 'api/graficoGastoObra';

    constructor(protected http: HttpClient) {}

    query(req?: any): Observable<EntityResponseTypeResumo> {
        const options = createRequestOption(req);
        return this.http
            .get<IResumoGasto[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityResponseTypeResumo) => this.convertDateArrayFromServer2(res)));
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
