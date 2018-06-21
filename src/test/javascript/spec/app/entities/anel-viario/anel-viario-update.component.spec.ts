/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { RecadastroTestModule } from '../../../test.module';
import { AnelViarioUpdateComponent } from 'app/entities/anel-viario/anel-viario-update.component';
import { AnelViarioService } from 'app/entities/anel-viario/anel-viario.service';
import { AnelViario } from 'app/shared/model/anel-viario.model';

describe('Component Tests', () => {
    describe('AnelViario Management Update Component', () => {
        let comp: AnelViarioUpdateComponent;
        let fixture: ComponentFixture<AnelViarioUpdateComponent>;
        let service: AnelViarioService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RecadastroTestModule],
                declarations: [AnelViarioUpdateComponent]
            })
                .overrideTemplate(AnelViarioUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AnelViarioUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AnelViarioService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new AnelViario(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.anelViario = entity;
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
                    const entity = new AnelViario();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.anelViario = entity;
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
