/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ObrasTestModule } from '../../../test.module';
import { PeriodoDeleteDialogComponent } from 'app/entities/periodo/periodo-delete-dialog.component';
import { PeriodoService } from 'app/entities/periodo/periodo.service';

describe('Component Tests', () => {
    describe('Periodo Management Delete Component', () => {
        let comp: PeriodoDeleteDialogComponent;
        let fixture: ComponentFixture<PeriodoDeleteDialogComponent>;
        let service: PeriodoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ObrasTestModule],
                declarations: [PeriodoDeleteDialogComponent]
            })
                .overrideTemplate(PeriodoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PeriodoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PeriodoService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
