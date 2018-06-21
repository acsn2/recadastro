/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { RecadastroTestModule } from '../../../test.module';
import { ParenteServidorUpdateComponent } from 'app/entities/parente-servidor/parente-servidor-update.component';
import { ParenteServidorService } from 'app/entities/parente-servidor/parente-servidor.service';
import { ParenteServidor } from 'app/shared/model/parente-servidor.model';

describe('Component Tests', () => {
    describe('ParenteServidor Management Update Component', () => {
        let comp: ParenteServidorUpdateComponent;
        let fixture: ComponentFixture<ParenteServidorUpdateComponent>;
        let service: ParenteServidorService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RecadastroTestModule],
                declarations: [ParenteServidorUpdateComponent]
            })
                .overrideTemplate(ParenteServidorUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ParenteServidorUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ParenteServidorService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ParenteServidor(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.parenteServidor = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ParenteServidor();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.parenteServidor = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
