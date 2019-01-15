import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILancamentoGastos } from 'app/shared/model/lancamento-gastos.model';
import { LancamentoGastosService } from './lancamento-gastos.service';

@Component({
    selector: 'jhi-lancamento-gastos-delete-dialog',
    templateUrl: './lancamento-gastos-delete-dialog.component.html'
})
export class LancamentoGastosDeleteDialogComponent {
    lancamentoGastos: ILancamentoGastos;

    constructor(
        protected lancamentoGastosService: LancamentoGastosService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.lancamentoGastosService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'lancamentoGastosListModification',
                content: 'Deleted an lancamentoGastos'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-lancamento-gastos-delete-popup',
    template: ''
})
export class LancamentoGastosDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ lancamentoGastos }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(LancamentoGastosDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.lancamentoGastos = lancamentoGastos;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
