/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ObrasTestModule } from '../../../test.module';
import { ResumoGastoDeleteDialogComponent } from 'app/entities/resumo-gasto/resumo-gasto-delete-dialog.component';
import { ResumoGastoService } from 'app/entities/resumo-gasto/resumo-gasto.service';

describe('Component Tests', () => {
    describe('ResumoGasto Management Delete Component', () => {
        let comp: ResumoGastoDeleteDialogComponent;
        let fixture: ComponentFixture<ResumoGastoDeleteDialogComponent>;
        let service: ResumoGastoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ObrasTestModule],
                declarations: [ResumoGastoDeleteDialogComponent]
            })
                .overrideTemplate(ResumoGastoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ResumoGastoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ResumoGastoService);
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
