/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { RecadastroTestModule } from '../../../test.module';
import { OrgaoUpdateComponent } from 'app/entities/orgao/orgao-update.component';
import { OrgaoService } from 'app/entities/orgao/orgao.service';
import { Orgao } from 'app/shared/model/orgao.model';

describe('Component Tests', () => {
    describe('Orgao Management Update Component', () => {
        let comp: OrgaoUpdateComponent;
        let fixture: ComponentFixture<OrgaoUpdateComponent>;
        let service: OrgaoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RecadastroTestModule],
                declarations: [OrgaoUpdateComponent]
            })
                .overrideTemplate(OrgaoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(OrgaoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrgaoService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Orgao(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.orgao = entity;
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
                    const entity = new Orgao();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.orgao = entity;
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
