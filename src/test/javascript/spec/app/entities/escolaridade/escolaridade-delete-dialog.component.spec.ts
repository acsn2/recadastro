/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { RecadastroTestModule } from '../../../test.module';
import { EscolaridadeDeleteDialogComponent } from 'app/entities/escolaridade/escolaridade-delete-dialog.component';
import { EscolaridadeService } from 'app/entities/escolaridade/escolaridade.service';

describe('Component Tests', () => {
    describe('Escolaridade Management Delete Component', () => {
        let comp: EscolaridadeDeleteDialogComponent;
        let fixture: ComponentFixture<EscolaridadeDeleteDialogComponent>;
        let service: EscolaridadeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RecadastroTestModule],
                declarations: [EscolaridadeDeleteDialogComponent]
            })
                .overrideTemplate(EscolaridadeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EscolaridadeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EscolaridadeService);
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
