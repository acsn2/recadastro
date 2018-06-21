/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { RecadastroTestModule } from '../../../test.module';
import { RacaCorDeleteDialogComponent } from 'app/entities/raca-cor/raca-cor-delete-dialog.component';
import { RacaCorService } from 'app/entities/raca-cor/raca-cor.service';

describe('Component Tests', () => {
    describe('RacaCor Management Delete Component', () => {
        let comp: RacaCorDeleteDialogComponent;
        let fixture: ComponentFixture<RacaCorDeleteDialogComponent>;
        let service: RacaCorService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RecadastroTestModule],
                declarations: [RacaCorDeleteDialogComponent]
            })
                .overrideTemplate(RacaCorDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RacaCorDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RacaCorService);
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
