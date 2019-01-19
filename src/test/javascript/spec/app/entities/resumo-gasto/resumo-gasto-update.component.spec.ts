/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ObrasTestModule } from '../../../test.module';
import { ResumoGastoUpdateComponent } from 'app/entities/resumo-gasto/resumo-gasto-update.component';
import { ResumoGastoService } from 'app/entities/resumo-gasto/resumo-gasto.service';
import { ResumoGasto } from 'app/shared/model/resumo-gasto.model';

describe('Component Tests', () => {
    describe('ResumoGasto Management Update Component', () => {
        let comp: ResumoGastoUpdateComponent;
        let fixture: ComponentFixture<ResumoGastoUpdateComponent>;
        let service: ResumoGastoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ObrasTestModule],
                declarations: [ResumoGastoUpdateComponent]
            })
                .overrideTemplate(ResumoGastoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ResumoGastoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ResumoGastoService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ResumoGasto(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.resumoGasto = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ResumoGasto();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.resumoGasto = entity;
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
