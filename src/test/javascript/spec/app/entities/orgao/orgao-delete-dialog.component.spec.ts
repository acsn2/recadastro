/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { RecadastroTestModule } from '../../../test.module';
import { OrgaoDeleteDialogComponent } from 'app/entities/orgao/orgao-delete-dialog.component';
import { OrgaoService } from 'app/entities/orgao/orgao.service';

describe('Component Tests', () => {
    describe('Orgao Management Delete Component', () => {
        let comp: OrgaoDeleteDialogComponent;
        let fixture: ComponentFixture<OrgaoDeleteDialogComponent>;
        let service: OrgaoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RecadastroTestModule],
                declarations: [OrgaoDeleteDialogComponent]
            })
                .overrideTemplate(OrgaoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(OrgaoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrgaoService);
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
