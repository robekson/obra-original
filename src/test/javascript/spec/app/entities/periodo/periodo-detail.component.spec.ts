/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ObrasTestModule } from '../../../test.module';
import { PeriodoDetailComponent } from 'app/entities/periodo/periodo-detail.component';
import { Periodo } from 'app/shared/model/periodo.model';

describe('Component Tests', () => {
    describe('Periodo Management Detail Component', () => {
        let comp: PeriodoDetailComponent;
        let fixture: ComponentFixture<PeriodoDetailComponent>;
        const route = ({ data: of({ periodo: new Periodo(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ObrasTestModule],
                declarations: [PeriodoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PeriodoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PeriodoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.periodo).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
