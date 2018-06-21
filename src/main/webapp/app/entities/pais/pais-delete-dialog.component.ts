import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPais } from 'app/shared/model/pais.model';
import { PaisService } from './pais.service';

@Component({
    selector: '-pais-delete-dialog',
    templateUrl: './pais-delete-dialog.component.html'
})
export class PaisDeleteDialogComponent {
    pais: IPais;

    constructor(private paisService: PaisService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.paisService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'paisListModification',
                content: 'Deleted an pais'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: '-pais-delete-popup',
    template: ''
})
export class PaisDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ pais }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PaisDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.pais = pais;
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
