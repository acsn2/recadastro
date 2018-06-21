import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRegimePrevidenciario } from 'app/shared/model/regime-previdenciario.model';
import { RegimePrevidenciarioService } from './regime-previdenciario.service';

@Component({
    selector: '-regime-previdenciario-delete-dialog',
    templateUrl: './regime-previdenciario-delete-dialog.component.html'
})
export class RegimePrevidenciarioDeleteDialogComponent {
    regimePrevidenciario: IRegimePrevidenciario;

    constructor(
        private regimePrevidenciarioService: RegimePrevidenciarioService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.regimePrevidenciarioService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'regimePrevidenciarioListModification',
                content: 'Deleted an regimePrevidenciario'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: '-regime-previdenciario-delete-popup',
    template: ''
})
export class RegimePrevidenciarioDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ regimePrevidenciario }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RegimePrevidenciarioDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.regimePrevidenciario = regimePrevidenciario;
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
