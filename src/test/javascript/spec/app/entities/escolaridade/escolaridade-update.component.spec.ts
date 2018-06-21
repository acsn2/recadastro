/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { RecadastroTestModule } from '../../../test.module';
import { EscolaridadeUpdateComponent } from 'app/entities/escolaridade/escolaridade-update.component';
import { EscolaridadeService } from 'app/entities/escolaridade/escolaridade.service';
import { Escolaridade } from 'app/shared/model/escolaridade.model';

describe('Component Tests', () => {
    describe('Escolaridade Management Update Component', () => {
        let comp: EscolaridadeUpdateComponent;
        let fixture: ComponentFixture<EscolaridadeUpdateComponent>;
        let service: EscolaridadeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RecadastroTestModule],
                declarations: [EscolaridadeUpdateComponent]
            })
                .overrideTemplate(EscolaridadeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EscolaridadeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EscolaridadeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Escolaridade(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.escolaridade = entity;
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
                    const entity = new Escolaridade();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.escolaridade = entity;
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
