import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IServidorTipo } from 'app/shared/model/servidor-tipo.model';
import { ServidorTipoService } from './servidor-tipo.service';

@Component({
    selector: '-servidor-tipo-delete-dialog',
    templateUrl: './servidor-tipo-delete-dialog.component.html'
})
export class ServidorTipoDeleteDialogComponent {
    servidorTipo: IServidorTipo;

    constructor(
        private servidorTipoService: ServidorTipoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.servidorTipoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'servidorTipoListModification',
                content: 'Deleted an servidorTipo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: '-servidor-tipo-delete-popup',
    template: ''
})
export class ServidorTipoDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ servidorTipo }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ServidorTipoDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.servidorTipo = servidorTipo;
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
