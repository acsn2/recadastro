/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { RecadastroTestModule } from '../../../test.module';
import { RacaCorUpdateComponent } from 'app/entities/raca-cor/raca-cor-update.component';
import { RacaCorService } from 'app/entities/raca-cor/raca-cor.service';
import { RacaCor } from 'app/shared/model/raca-cor.model';

describe('Component Tests', () => {
    describe('RacaCor Management Update Component', () => {
        let comp: RacaCorUpdateComponent;
        let fixture: ComponentFixture<RacaCorUpdateComponent>;
        let service: RacaCorService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RecadastroTestModule],
                declarations: [RacaCorUpdateComponent]
            })
                .overrideTemplate(RacaCorUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RacaCorUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RacaCorService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new RacaCor(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.racaCor = entity;
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
                    const entity = new RacaCor();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.racaCor = entity;
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
