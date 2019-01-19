/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ObrasTestModule } from '../../../test.module';
import { GastoUpdateComponent } from 'app/entities/gasto/gasto-update.component';
import { GastoService } from 'app/entities/gasto/gasto.service';
import { Gasto } from 'app/shared/model/gasto.model';

describe('Component Tests', () => {
    describe('Gasto Management Update Component', () => {
        let comp: GastoUpdateComponent;
        let fixture: ComponentFixture<GastoUpdateComponent>;
        let service: GastoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ObrasTestModule],
                declarations: [GastoUpdateComponent]
            })
                .overrideTemplate(GastoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(GastoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GastoService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Gasto(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.gasto = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Gasto();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.gasto = entity;
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
