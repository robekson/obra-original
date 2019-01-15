/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ObrasTestModule } from '../../../test.module';
import { ObrasUpdateComponent } from 'app/entities/obras/obras-update.component';
import { ObrasService } from 'app/entities/obras/obras.service';
import { Obras } from 'app/shared/model/obras.model';

describe('Component Tests', () => {
    describe('Obras Management Update Component', () => {
        let comp: ObrasUpdateComponent;
        let fixture: ComponentFixture<ObrasUpdateComponent>;
        let service: ObrasService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ObrasTestModule],
                declarations: [ObrasUpdateComponent]
            })
                .overrideTemplate(ObrasUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ObrasUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ObrasService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Obras(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.obras = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Obras();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.obras = entity;
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
