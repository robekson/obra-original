import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IResumoGasto } from 'app/shared/model/resumo-gasto.model';
import { ResumoGastoService } from './resumo-gasto.service';

@Component({
    selector: 'jhi-resumo-gasto-delete-dialog',
    templateUrl: './resumo-gasto-delete-dialog.component.html'
})
export class ResumoGastoDeleteDialogComponent {
    resumoGasto: IResumoGasto;

    constructor(
        protected resumoGastoService: ResumoGastoService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.resumoGastoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'resumoGastoListModification',
                content: 'Deleted an resumoGasto'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-resumo-gasto-delete-popup',
    template: ''
})
export class ResumoGastoDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ resumoGasto }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ResumoGastoDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.resumoGasto = resumoGasto;
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
