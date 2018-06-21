import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAnelViario } from 'app/shared/model/anel-viario.model';
import { AnelViarioService } from './anel-viario.service';

@Component({
    selector: '-anel-viario-delete-dialog',
    templateUrl: './anel-viario-delete-dialog.component.html'
})
export class AnelViarioDeleteDialogComponent {
    anelViario: IAnelViario;

    constructor(private anelViarioService: AnelViarioService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.anelViarioService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'anelViarioListModification',
                content: 'Deleted an anelViario'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: '-anel-viario-delete-popup',
    template: ''
})
export class AnelViarioDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ anelViario }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AnelViarioDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.anelViario = anelViario;
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
