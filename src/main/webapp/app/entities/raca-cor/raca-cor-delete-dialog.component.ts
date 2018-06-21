import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRacaCor } from 'app/shared/model/raca-cor.model';
import { RacaCorService } from './raca-cor.service';

@Component({
    selector: '-raca-cor-delete-dialog',
    templateUrl: './raca-cor-delete-dialog.component.html'
})
export class RacaCorDeleteDialogComponent {
    racaCor: IRacaCor;

    constructor(private racaCorService: RacaCorService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.racaCorService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'racaCorListModification',
                content: 'Deleted an racaCor'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: '-raca-cor-delete-popup',
    template: ''
})
export class RacaCorDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ racaCor }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RacaCorDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.racaCor = racaCor;
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
