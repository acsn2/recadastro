/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { RecadastroTestModule } from '../../../test.module';
import { CategoriaESocialDeleteDialogComponent } from 'app/entities/categoria-e-social/categoria-e-social-delete-dialog.component';
import { CategoriaESocialService } from 'app/entities/categoria-e-social/categoria-e-social.service';

describe('Component Tests', () => {
    describe('CategoriaESocial Management Delete Component', () => {
        let comp: CategoriaESocialDeleteDialogComponent;
        let fixture: ComponentFixture<CategoriaESocialDeleteDialogComponent>;
        let service: CategoriaESocialService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RecadastroTestModule],
                declarations: [CategoriaESocialDeleteDialogComponent]
            })
                .overrideTemplate(CategoriaESocialDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CategoriaESocialDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CategoriaESocialService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});
