/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RecadastroTestModule } from '../../../test.module';
import { OrgaoDetailComponent } from 'app/entities/orgao/orgao-detail.component';
import { Orgao } from 'app/shared/model/orgao.model';

describe('Component Tests', () => {
    describe('Orgao Management Detail Component', () => {
        let comp: OrgaoDetailComponent;
        let fixture: ComponentFixture<OrgaoDetailComponent>;
        const route = ({ data: of({ orgao: new Orgao(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RecadastroTestModule],
                declarations: [OrgaoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(OrgaoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(OrgaoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.orgao).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
