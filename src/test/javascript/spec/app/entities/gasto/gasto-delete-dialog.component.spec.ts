/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ObrasTestModule } from '../../../test.module';
import { GastoDeleteDialogComponent } from 'app/entities/gasto/gasto-delete-dialog.component';
import { GastoService } from 'app/entities/gasto/gasto.service';

describe('Component Tests', () => {
    describe('Gasto Management Delete Component', () => {
        let comp: GastoDeleteDialogComponent;
        let fixture: ComponentFixture<GastoDeleteDialogComponent>;
        let service: GastoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ObrasTestModule],
                declarations: [GastoDeleteDialogComponent]
            })
                .overrideTemplate(GastoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GastoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GastoService);
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
