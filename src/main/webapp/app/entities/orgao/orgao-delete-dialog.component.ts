import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOrgao } from 'app/shared/model/orgao.model';
import { OrgaoService } from './orgao.service';

@Component({
    selector: '-orgao-delete-dialog',
    templateUrl: './orgao-delete-dialog.component.html'
})
export class OrgaoDeleteDialogComponent {
    orgao: IOrgao;

    constructor(private orgaoService: OrgaoService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.orgaoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'orgaoListModification',
                content: 'Deleted an orgao'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: '-orgao-delete-popup',
    template: ''
})
export class OrgaoDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ orgao }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(OrgaoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.orgao = orgao;
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
