import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEscolaridade } from 'app/shared/model/escolaridade.model';
import { EscolaridadeService } from './escolaridade.service';

@Component({
    selector: '-escolaridade-delete-dialog',
    templateUrl: './escolaridade-delete-dialog.component.html'
})
export class EscolaridadeDeleteDialogComponent {
    escolaridade: IEscolaridade;

    constructor(
        private escolaridadeService: EscolaridadeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.escolaridadeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'escolaridadeListModification',
                content: 'Deleted an escolaridade'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: '-escolaridade-delete-popup',
    template: ''
})
export class EscolaridadeDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ escolaridade }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EscolaridadeDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.escolaridade = escolaridade;
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
