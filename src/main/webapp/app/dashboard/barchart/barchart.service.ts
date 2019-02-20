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
export class BarchartService {
    public resourceUrl = SERVER_API_URL + 'api/graficoGastoObraIntervaloMensal';

    constructor(protected http: HttpClient) {}

    query(req?: any): Observable<EntityResponseTypeResumo> {
        const options = createRequestOption(req);
        return this.http
            .get<IResumoGasto[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityResponseTypeResumo) => this.convertDateArrayFromServer2(res)));
    }

    protected convertDateArrayFromServer2(res: EntityResponseTypeResumo): EntityResponseTypeResumo {
        if (res.body) {
        }
        return res;
    }
}
