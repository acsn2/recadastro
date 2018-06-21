/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { RecadastroTestModule } from '../../../test.module';
import { CategoriaESocialUpdateComponent } from 'app/entities/categoria-e-social/categoria-e-social-update.component';
import { CategoriaESocialService } from 'app/entities/categoria-e-social/categoria-e-social.service';
import { CategoriaESocial } from 'app/shared/model/categoria-e-social.model';

describe('Component Tests', () => {
    describe('CategoriaESocial Management Update Component', () => {
        let comp: CategoriaESocialUpdateComponent;
        let fixture: ComponentFixture<CategoriaESocialUpdateComponent>;
        let service: CategoriaESocialService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RecadastroTestModule],
                declarations: [CategoriaESocialUpdateComponent]
            })
                .overrideTemplate(CategoriaESocialUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CategoriaESocialUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CategoriaESocialService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new CategoriaESocial(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.categoriaESocial = entity;
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
                    const entity = new CategoriaESocial();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.categoriaESocial = entity;
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
