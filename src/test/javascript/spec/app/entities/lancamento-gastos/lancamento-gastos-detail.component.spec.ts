/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ObrasTestModule } from '../../../test.module';
import { LancamentoGastosDetailComponent } from 'app/entities/lancamento-gastos/lancamento-gastos-detail.component';
import { LancamentoGastos } from 'app/shared/model/lancamento-gastos.model';

describe('Component Tests', () => {
    describe('LancamentoGastos Management Detail Component', () => {
        let comp: LancamentoGastosDetailComponent;
        let fixture: ComponentFixture<LancamentoGastosDetailComponent>;
        const route = ({ data: of({ lancamentoGastos: new LancamentoGastos(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ObrasTestModule],
                declarations: [LancamentoGastosDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(LancamentoGastosDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LancamentoGastosDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.lancamentoGastos).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
