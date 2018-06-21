/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { RecadastroTestModule } from '../../../test.module';
import { DependenteUpdateComponent } from 'app/entities/dependente/dependente-update.component';
import { DependenteService } from 'app/entities/dependente/dependente.service';
import { Dependente } from 'app/shared/model/dependente.model';

describe('Component Tests', () => {
    describe('Dependente Management Update Component', () => {
        let comp: DependenteUpdateComponent;
        let fixture: ComponentFixture<DependenteUpdateComponent>;
        let service: DependenteService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RecadastroTestModule],
                declarations: [DependenteUpdateComponent]
            })
                .overrideTemplate(DependenteUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DependenteUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DependenteService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Dependente(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.dependente = entity;
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
                    const entity = new Dependente();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.dependente = entity;
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
