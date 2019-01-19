/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { GastoService } from 'app/entities/gasto/gasto.service';
import { IGasto, Gasto, Pago, NotaFiscal, TipoConta } from 'app/shared/model/gasto.model';

describe('Service Tests', () => {
    describe('Gasto Service', () => {
        let injector: TestBed;
        let service: GastoService;
        let httpMock: HttpTestingController;
        let elemDefault: IGasto;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(GastoService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Gasto(0, 'AAAAAAA', 0, currentDate, Pago.SIM, NotaFiscal.SIM, TipoConta.MAO_DE_OBRA, 0, currentDate);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        dataVencimento: currentDate.format(DATE_FORMAT),
                        mesAno: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Gasto', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        dataVencimento: currentDate.format(DATE_FORMAT),
                        mesAno: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dataVencimento: currentDate,
                        mesAno: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Gasto(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Gasto', async () => {
                const returnedFromService = Object.assign(
                    {
                        nome: 'BBBBBB',
                        valor: 1,
                        dataVencimento: currentDate.format(DATE_FORMAT),
                        pagamento: 'BBBBBB',
                        nota: 'BBBBBB',
                        tipo: 'BBBBBB',
                        parcelado: 1,
                        mesAno: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        dataVencimento: currentDate,
                        mesAno: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Gasto', async () => {
                const returnedFromService = Object.assign(
                    {
                        nome: 'BBBBBB',
                        valor: 1,
                        dataVencimento: currentDate.format(DATE_FORMAT),
                        pagamento: 'BBBBBB',
                        nota: 'BBBBBB',
                        tipo: 'BBBBBB',
                        parcelado: 1,
                        mesAno: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dataVencimento: currentDate,
                        mesAno: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a Gasto', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
