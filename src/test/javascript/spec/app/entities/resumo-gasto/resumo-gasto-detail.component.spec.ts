/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ObrasTestModule } from '../../../test.module';
import { ResumoGastoDetailComponent } from 'app/entities/resumo-gasto/resumo-gasto-detail.component';
import { ResumoGasto } from 'app/shared/model/resumo-gasto.model';

describe('Component Tests', () => {
    describe('ResumoGasto Management Detail Component', () => {
        let comp: ResumoGastoDetailComponent;
        let fixture: ComponentFixture<ResumoGastoDetailComponent>;
        const route = ({ data: of({ resumoGasto: new ResumoGasto(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ObrasTestModule],
                declarations: [ResumoGastoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ResumoGastoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ResumoGastoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.resumoGasto).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
