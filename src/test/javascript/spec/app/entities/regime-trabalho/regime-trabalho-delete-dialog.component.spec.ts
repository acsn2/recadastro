/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { RecadastroTestModule } from '../../../test.module';
import { RegimeTrabalhoDeleteDialogComponent } from 'app/entities/regime-trabalho/regime-trabalho-delete-dialog.component';
import { RegimeTrabalhoService } from 'app/entities/regime-trabalho/regime-trabalho.service';

describe('Component Tests', () => {
    describe('RegimeTrabalho Management Delete Component', () => {
        let comp: RegimeTrabalhoDeleteDialogComponent;
        let fixture: ComponentFixture<RegimeTrabalhoDeleteDialogComponent>;
        let service: RegimeTrabalhoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RecadastroTestModule],
                declarations: [RegimeTrabalhoDeleteDialogComponent]
            })
                .overrideTemplate(RegimeTrabalhoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RegimeTrabalhoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RegimeTrabalhoService);
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
