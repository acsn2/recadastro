/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { RecadastroTestModule } from '../../../test.module';
import { EstadoCivilUpdateComponent } from 'app/entities/estado-civil/estado-civil-update.component';
import { EstadoCivilService } from 'app/entities/estado-civil/estado-civil.service';
import { EstadoCivil } from 'app/shared/model/estado-civil.model';

describe('Component Tests', () => {
    describe('EstadoCivil Management Update Component', () => {
        let comp: EstadoCivilUpdateComponent;
        let fixture: ComponentFixture<EstadoCivilUpdateComponent>;
        let service: EstadoCivilService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RecadastroTestModule],
                declarations: [EstadoCivilUpdateComponent]
            })
                .overrideTemplate(EstadoCivilUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EstadoCivilUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EstadoCivilService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new EstadoCivil(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.estadoCivil = entity;
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
                    const entity = new EstadoCivil();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.estadoCivil = entity;
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
