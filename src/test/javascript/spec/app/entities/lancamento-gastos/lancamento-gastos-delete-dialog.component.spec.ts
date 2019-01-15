/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ObrasTestModule } from '../../../test.module';
import { LancamentoGastosDeleteDialogComponent } from 'app/entities/lancamento-gastos/lancamento-gastos-delete-dialog.component';
import { LancamentoGastosService } from 'app/entities/lancamento-gastos/lancamento-gastos.service';

describe('Component Tests', () => {
    describe('LancamentoGastos Management Delete Component', () => {
        let comp: LancamentoGastosDeleteDialogComponent;
        let fixture: ComponentFixture<LancamentoGastosDeleteDialogComponent>;
        let service: LancamentoGastosService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ObrasTestModule],
                declarations: [LancamentoGastosDeleteDialogComponent]
            })
                .overrideTemplate(LancamentoGastosDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LancamentoGastosDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LancamentoGastosService);
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
