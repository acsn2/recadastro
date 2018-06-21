/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { RecadastroTestModule } from '../../../test.module';
import { RegimeTrabalhoUpdateComponent } from 'app/entities/regime-trabalho/regime-trabalho-update.component';
import { RegimeTrabalhoService } from 'app/entities/regime-trabalho/regime-trabalho.service';
import { RegimeTrabalho } from 'app/shared/model/regime-trabalho.model';

describe('Component Tests', () => {
    describe('RegimeTrabalho Management Update Component', () => {
        let comp: RegimeTrabalhoUpdateComponent;
        let fixture: ComponentFixture<RegimeTrabalhoUpdateComponent>;
        let service: RegimeTrabalhoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RecadastroTestModule],
                declarations: [RegimeTrabalhoUpdateComponent]
            })
                .overrideTemplate(RegimeTrabalhoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RegimeTrabalhoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RegimeTrabalhoService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new RegimeTrabalho(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.regimeTrabalho = entity;
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
                    const entity = new RegimeTrabalho();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.regimeTrabalho = entity;
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
