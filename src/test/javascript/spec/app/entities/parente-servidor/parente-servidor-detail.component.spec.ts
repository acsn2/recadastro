/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RecadastroTestModule } from '../../../test.module';
import { ParenteServidorDetailComponent } from 'app/entities/parente-servidor/parente-servidor-detail.component';
import { ParenteServidor } from 'app/shared/model/parente-servidor.model';

describe('Component Tests', () => {
    describe('ParenteServidor Management Detail Component', () => {
        let comp: ParenteServidorDetailComponent;
        let fixture: ComponentFixture<ParenteServidorDetailComponent>;
        const route = ({ data: of({ parenteServidor: new ParenteServidor(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RecadastroTestModule],
                declarations: [ParenteServidorDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ParenteServidorDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ParenteServidorDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.parenteServidor).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
