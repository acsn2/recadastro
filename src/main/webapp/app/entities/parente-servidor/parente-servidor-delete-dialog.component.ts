import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IParenteServidor } from 'app/shared/model/parente-servidor.model';
import { ParenteServidorService } from './parente-servidor.service';

@Component({
    selector: '-parente-servidor-delete-dialog',
    templateUrl: './parente-servidor-delete-dialog.component.html'
})
export class ParenteServidorDeleteDialogComponent {
    parenteServidor: IParenteServidor;

    constructor(
        private parenteServidorService: ParenteServidorService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.parenteServidorService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'parenteServidorListModification',
                content: 'Deleted an parenteServidor'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: '-parente-servidor-delete-popup',
    template: ''
})
export class ParenteServidorDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ parenteServidor }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ParenteServidorDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.parenteServidor = parenteServidor;
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
