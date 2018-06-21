import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICategoriaESocial } from 'app/shared/model/categoria-e-social.model';
import { CategoriaESocialService } from './categoria-e-social.service';

@Component({
    selector: '-categoria-e-social-delete-dialog',
    templateUrl: './categoria-e-social-delete-dialog.component.html'
})
export class CategoriaESocialDeleteDialogComponent {
    categoriaESocial: ICategoriaESocial;

    constructor(
        private categoriaESocialService: CategoriaESocialService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.categoriaESocialService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'categoriaESocialListModification',
                content: 'Deleted an categoriaESocial'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: '-categoria-e-social-delete-popup',
    template: ''
})
export class CategoriaESocialDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ categoriaESocial }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CategoriaESocialDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.categoriaESocial = categoriaESocial;
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
