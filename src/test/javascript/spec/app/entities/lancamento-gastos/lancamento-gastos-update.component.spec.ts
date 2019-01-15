/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ObrasTestModule } from '../../../test.module';
import { LancamentoGastosUpdateComponent } from 'app/entities/lancamento-gastos/lancamento-gastos-update.component';
import { LancamentoGastosService } from 'app/entities/lancamento-gastos/lancamento-gastos.service';
import { LancamentoGastos } from 'app/shared/model/lancamento-gastos.model';

describe('Component Tests', () => {
    describe('LancamentoGastos Management Update Component', () => {
        let comp: LancamentoGastosUpdateComponent;
        let fixture: ComponentFixture<LancamentoGastosUpdateComponent>;
        let service: LancamentoGastosService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ObrasTestModule],
                declarations: [LancamentoGastosUpdateComponent]
            })
                .overrideTemplate(LancamentoGastosUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LancamentoGastosUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LancamentoGastosService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new LancamentoGastos(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.lancamentoGastos = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new LancamentoGastos();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.lancamentoGastos = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
