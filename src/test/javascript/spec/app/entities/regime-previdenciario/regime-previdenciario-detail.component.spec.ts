/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RecadastroTestModule } from '../../../test.module';
import { RegimePrevidenciarioDetailComponent } from 'app/entities/regime-previdenciario/regime-previdenciario-detail.component';
import { RegimePrevidenciario } from 'app/shared/model/regime-previdenciario.model';

describe('Component Tests', () => {
    describe('RegimePrevidenciario Management Detail Component', () => {
        let comp: RegimePrevidenciarioDetailComponent;
        let fixture: ComponentFixture<RegimePrevidenciarioDetailComponent>;
        const route = ({ data: of({ regimePrevidenciario: new RegimePrevidenciario(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RecadastroTestModule],
                declarations: [RegimePrevidenciarioDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RegimePrevidenciarioDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RegimePrevidenciarioDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.regimePrevidenciario).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
