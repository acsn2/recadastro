/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { RecadastroTestModule } from '../../../test.module';
import { OrgaoComponent } from 'app/entities/orgao/orgao.component';
import { OrgaoService } from 'app/entities/orgao/orgao.service';
import { Orgao } from 'app/shared/model/orgao.model';

describe('Component Tests', () => {
    describe('Orgao Management Component', () => {
        let comp: OrgaoComponent;
        let fixture: ComponentFixture<OrgaoComponent>;
        let service: OrgaoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RecadastroTestModule],
                declarations: [OrgaoComponent],
                providers: []
            })
                .overrideTemplate(OrgaoComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(OrgaoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrgaoService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Orgao(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.orgaos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
