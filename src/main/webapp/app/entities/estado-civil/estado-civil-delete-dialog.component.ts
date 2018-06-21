import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEstadoCivil } from 'app/shared/model/estado-civil.model';
import { EstadoCivilService } from './estado-civil.service';

@Component({
    selector: '-estado-civil-delete-dialog',
    templateUrl: './estado-civil-delete-dialog.component.html'
})
export class EstadoCivilDeleteDialogComponent {
    estadoCivil: IEstadoCivil;

    constructor(
        private estadoCivilService: EstadoCivilService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.estadoCivilService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'estadoCivilListModification',
                content: 'Deleted an estadoCivil'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: '-estado-civil-delete-popup',
    template: ''
})
export class EstadoCivilDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ estadoCivil }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EstadoCivilDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.estadoCivil = estadoCivil;
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
