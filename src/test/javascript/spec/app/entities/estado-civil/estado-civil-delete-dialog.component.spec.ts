/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { RecadastroTestModule } from '../../../test.module';
import { EstadoCivilDeleteDialogComponent } from 'app/entities/estado-civil/estado-civil-delete-dialog.component';
import { EstadoCivilService } from 'app/entities/estado-civil/estado-civil.service';

describe('Component Tests', () => {
    describe('EstadoCivil Management Delete Component', () => {
        let comp: EstadoCivilDeleteDialogComponent;
        let fixture: ComponentFixture<EstadoCivilDeleteDialogComponent>;
        let service: EstadoCivilService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RecadastroTestModule],
                declarations: [EstadoCivilDeleteDialogComponent]
            })
                .overrideTemplate(EstadoCivilDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EstadoCivilDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EstadoCivilService);
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
