/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ObraService } from 'app/entities/obra/obra.service';
import { IObra, Obra, TipoCorretagem, StatusObra } from 'app/shared/model/obra.model';

describe('Service Tests', () => {
    describe('Obra Service', () => {
        let injector: TestBed;
        let service: ObraService;
        let httpMock: HttpTestingController;
        let elemDefault: IObra;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(ObraService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Obra(
                0,
                'AAAAAAA',
                'AAAAAAA',
                0,
                0,
                0,
                0,
                TipoCorretagem.Tipo1,
                StatusObra.ANDAMENTO,
                currentDate,
                currentDate
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        dataInicio: currentDate.format(DATE_FORMAT),
                        dataFim: currentDate.format(DATE_FORMAT)
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

            it('should create a Obra', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        dataInicio: currentDate.format(DATE_FORMAT),
                        dataFim: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dataInicio: currentDate,
                        dataFim: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Obra(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Obra', async () => {
                const returnedFromService = Object.assign(
                    {
                        nome: 'BBBBBB',
                        endereco: 'BBBBBB',
                        metragem: 1,
                        valorTerreno: 1,
                        valorEscritura: 1,
                        porcentagemCorretagem: 1,
                        tipoCorretagem: 'BBBBBB',
                        status: 'BBBBBB',
                        dataInicio: currentDate.format(DATE_FORMAT),
                        dataFim: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        dataInicio: currentDate,
                        dataFim: currentDate
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

            it('should return a list of Obra', async () => {
                const returnedFromService = Object.assign(
                    {
                        nome: 'BBBBBB',
                        endereco: 'BBBBBB',
                        metragem: 1,
                        valorTerreno: 1,
                        valorEscritura: 1,
                        porcentagemCorretagem: 1,
                        tipoCorretagem: 'BBBBBB',
                        status: 'BBBBBB',
                        dataInicio: currentDate.format(DATE_FORMAT),
                        dataFim: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dataInicio: currentDate,
                        dataFim: currentDate
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

            it('should delete a Obra', async () => {
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
