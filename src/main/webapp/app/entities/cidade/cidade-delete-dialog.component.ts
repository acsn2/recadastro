import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICidade } from 'app/shared/model/cidade.model';
import { CidadeService } from './cidade.service';

@Component({
    selector: '-cidade-delete-dialog',
    templateUrl: './cidade-delete-dialog.component.html'
})
export class CidadeDeleteDialogComponent {
    cidade: ICidade;

    constructor(private cidadeService: CidadeService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.cidadeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'cidadeListModification',
                content: 'Deleted an cidade'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: '-cidade-delete-popup',
    template: ''
})
export class CidadeDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ cidade }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CidadeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.cidade = cidade;
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
