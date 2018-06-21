/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { RecadastroTestModule } from '../../../test.module';
import { RegimePrevidenciarioUpdateComponent } from 'app/entities/regime-previdenciario/regime-previdenciario-update.component';
import { RegimePrevidenciarioService } from 'app/entities/regime-previdenciario/regime-previdenciario.service';
import { RegimePrevidenciario } from 'app/shared/model/regime-previdenciario.model';

describe('Component Tests', () => {
    describe('RegimePrevidenciario Management Update Component', () => {
        let comp: RegimePrevidenciarioUpdateComponent;
        let fixture: ComponentFixture<RegimePrevidenciarioUpdateComponent>;
        let service: RegimePrevidenciarioService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RecadastroTestModule],
                declarations: [RegimePrevidenciarioUpdateComponent]
            })
                .overrideTemplate(RegimePrevidenciarioUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RegimePrevidenciarioUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RegimePrevidenciarioService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new RegimePrevidenciario(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.regimePrevidenciario = entity;
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
                    const entity = new RegimePrevidenciario();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.regimePrevidenciario = entity;
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
