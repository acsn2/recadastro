/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { RecadastroTestModule } from '../../../test.module';
import { ParenteServidorDeleteDialogComponent } from 'app/entities/parente-servidor/parente-servidor-delete-dialog.component';
import { ParenteServidorService } from 'app/entities/parente-servidor/parente-servidor.service';

describe('Component Tests', () => {
    describe('ParenteServidor Management Delete Component', () => {
        let comp: ParenteServidorDeleteDialogComponent;
        let fixture: ComponentFixture<ParenteServidorDeleteDialogComponent>;
        let service: ParenteServidorService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RecadastroTestModule],
                declarations: [ParenteServidorDeleteDialogComponent]
            })
                .overrideTemplate(ParenteServidorDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ParenteServidorDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ParenteServidorService);
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
