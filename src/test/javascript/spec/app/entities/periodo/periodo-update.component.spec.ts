/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ObrasTestModule } from '../../../test.module';
import { PeriodoUpdateComponent } from 'app/entities/periodo/periodo-update.component';
import { PeriodoService } from 'app/entities/periodo/periodo.service';
import { Periodo } from 'app/shared/model/periodo.model';

describe('Component Tests', () => {
    describe('Periodo Management Update Component', () => {
        let comp: PeriodoUpdateComponent;
        let fixture: ComponentFixture<PeriodoUpdateComponent>;
        let service: PeriodoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ObrasTestModule],
                declarations: [PeriodoUpdateComponent]
            })
                .overrideTemplate(PeriodoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PeriodoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PeriodoService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Periodo(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.periodo = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Periodo();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.periodo = entity;
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
