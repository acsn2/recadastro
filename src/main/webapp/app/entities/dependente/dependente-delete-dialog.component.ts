import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDependente } from 'app/shared/model/dependente.model';
import { DependenteService } from './dependente.service';

@Component({
    selector: '-dependente-delete-dialog',
    templateUrl: './dependente-delete-dialog.component.html'
})
export class DependenteDeleteDialogComponent {
    dependente: IDependente;

    constructor(private dependenteService: DependenteService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.dependenteService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'dependenteListModification',
                content: 'Deleted an dependente'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: '-dependente-delete-popup',
    template: ''
})
export class DependenteDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ dependente }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DependenteDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.dependente = dependente;
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
