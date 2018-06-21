/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { RecadastroTestModule } from '../../../test.module';
import { AnelViarioDeleteDialogComponent } from 'app/entities/anel-viario/anel-viario-delete-dialog.component';
import { AnelViarioService } from 'app/entities/anel-viario/anel-viario.service';

describe('Component Tests', () => {
    describe('AnelViario Management Delete Component', () => {
        let comp: AnelViarioDeleteDialogComponent;
        let fixture: ComponentFixture<AnelViarioDeleteDialogComponent>;
        let service: AnelViarioService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RecadastroTestModule],
                declarations: [AnelViarioDeleteDialogComponent]
            })
                .overrideTemplate(AnelViarioDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AnelViarioDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AnelViarioService);
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
