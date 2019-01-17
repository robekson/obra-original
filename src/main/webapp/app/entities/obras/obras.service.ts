import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IObras } from 'app/shared/model/obras.model';

type EntityResponseType = HttpResponse<IObras>;
type EntityArrayResponseType = HttpResponse<IObras[]>;

@Injectable({ providedIn: 'root' })
export class ObrasService {
    public resourceUrl = SERVER_API_URL + 'api/obras';

    constructor(protected http: HttpClient) {}

    create(obras: IObras): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(obras);
        return this.http
            .post<IObras>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(obras: IObras): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(obras);
        return this.http
            .put<IObras>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IObras>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IObras[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(obras: IObras): IObras {
        const copy: IObras = Object.assign({}, obras, {
            dataInicio: obras.dataInicio != null && obras.dataInicio.isValid() ? obras.dataInicio.format(DATE_FORMAT) : null,
            dataFim: obras.dataFim != null && obras.dataFim.isValid() ? obras.dataFim.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dataInicio = res.body.dataInicio != null ? moment(res.body.dataInicio) : null;
            res.body.dataFim = res.body.dataFim != null ? moment(res.body.dataFim) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((obras: IObras) => {
                obras.dataInicio = obras.dataInicio != null ? moment(obras.dataInicio) : null;
                obras.dataFim = obras.dataFim != null ? moment(obras.dataFim) : null;
            });
        }
        return res;
    }
}
