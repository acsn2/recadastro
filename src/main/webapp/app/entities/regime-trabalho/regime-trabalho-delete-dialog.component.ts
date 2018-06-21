import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRegimeTrabalho } from 'app/shared/model/regime-trabalho.model';
import { RegimeTrabalhoService } from './regime-trabalho.service';

@Component({
    selector: '-regime-trabalho-delete-dialog',
    templateUrl: './regime-trabalho-delete-dialog.component.html'
})
export class RegimeTrabalhoDeleteDialogComponent {
    regimeTrabalho: IRegimeTrabalho;

    constructor(
        private regimeTrabalhoService: RegimeTrabalhoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.regimeTrabalhoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'regimeTrabalhoListModification',
                content: 'Deleted an regimeTrabalho'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: '-regime-trabalho-delete-popup',
    template: ''
})
export class RegimeTrabalhoDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ regimeTrabalho }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RegimeTrabalhoDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.regimeTrabalho = regimeTrabalho;
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
