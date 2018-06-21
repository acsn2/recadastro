/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RecadastroTestModule } from '../../../test.module';
import { EscolaridadeDetailComponent } from 'app/entities/escolaridade/escolaridade-detail.component';
import { Escolaridade } from 'app/shared/model/escolaridade.model';

describe('Component Tests', () => {
    describe('Escolaridade Management Detail Component', () => {
        let comp: EscolaridadeDetailComponent;
        let fixture: ComponentFixture<EscolaridadeDetailComponent>;
        const route = ({ data: of({ escolaridade: new Escolaridade(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RecadastroTestModule],
                declarations: [EscolaridadeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(EscolaridadeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EscolaridadeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.escolaridade).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
