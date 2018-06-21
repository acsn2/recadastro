/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RecadastroTestModule } from '../../../test.module';
import { DependenteDetailComponent } from 'app/entities/dependente/dependente-detail.component';
import { Dependente } from 'app/shared/model/dependente.model';

describe('Component Tests', () => {
    describe('Dependente Management Detail Component', () => {
        let comp: DependenteDetailComponent;
        let fixture: ComponentFixture<DependenteDetailComponent>;
        const route = ({ data: of({ dependente: new Dependente(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RecadastroTestModule],
                declarations: [DependenteDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DependenteDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DependenteDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.dependente).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
