/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { RecadastroTestModule } from '../../../test.module';
import { RegimePrevidenciarioDeleteDialogComponent } from 'app/entities/regime-previdenciario/regime-previdenciario-delete-dialog.component';
import { RegimePrevidenciarioService } from 'app/entities/regime-previdenciario/regime-previdenciario.service';

describe('Component Tests', () => {
    describe('RegimePrevidenciario Management Delete Component', () => {
        let comp: RegimePrevidenciarioDeleteDialogComponent;
        let fixture: ComponentFixture<RegimePrevidenciarioDeleteDialogComponent>;
        let service: RegimePrevidenciarioService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RecadastroTestModule],
                declarations: [RegimePrevidenciarioDeleteDialogComponent]
            })
                .overrideTemplate(RegimePrevidenciarioDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RegimePrevidenciarioDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RegimePrevidenciarioService);
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
