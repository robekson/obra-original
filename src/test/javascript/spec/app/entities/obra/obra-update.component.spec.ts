/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ObrasTestModule } from '../../../test.module';
import { ObraUpdateComponent } from 'app/entities/obra/obra-update.component';
import { ObraService } from 'app/entities/obra/obra.service';
import { Obra } from 'app/shared/model/obra.model';

describe('Component Tests', () => {
    describe('Obra Management Update Component', () => {
        let comp: ObraUpdateComponent;
        let fixture: ComponentFixture<ObraUpdateComponent>;
        let service: ObraService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ObrasTestModule],
                declarations: [ObraUpdateComponent]
            })
                .overrideTemplate(ObraUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ObraUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ObraService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Obra(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.obra = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Obra();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.obra = entity;
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
