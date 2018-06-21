/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { RecadastroTestModule } from '../../../test.module';
import { ServidorTipoUpdateComponent } from 'app/entities/servidor-tipo/servidor-tipo-update.component';
import { ServidorTipoService } from 'app/entities/servidor-tipo/servidor-tipo.service';
import { ServidorTipo } from 'app/shared/model/servidor-tipo.model';

describe('Component Tests', () => {
    describe('ServidorTipo Management Update Component', () => {
        let comp: ServidorTipoUpdateComponent;
        let fixture: ComponentFixture<ServidorTipoUpdateComponent>;
        let service: ServidorTipoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RecadastroTestModule],
                declarations: [ServidorTipoUpdateComponent]
            })
                .overrideTemplate(ServidorTipoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ServidorTipoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ServidorTipoService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ServidorTipo(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.servidorTipo = entity;
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
                    const entity = new ServidorTipo();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.servidorTipo = entity;
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
