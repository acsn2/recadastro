/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RecadastroTestModule } from '../../../test.module';
import { CategoriaESocialDetailComponent } from 'app/entities/categoria-e-social/categoria-e-social-detail.component';
import { CategoriaESocial } from 'app/shared/model/categoria-e-social.model';

describe('Component Tests', () => {
    describe('CategoriaESocial Management Detail Component', () => {
        let comp: CategoriaESocialDetailComponent;
        let fixture: ComponentFixture<CategoriaESocialDetailComponent>;
        const route = ({ data: of({ categoriaESocial: new CategoriaESocial(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RecadastroTestModule],
                declarations: [CategoriaESocialDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CategoriaESocialDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CategoriaESocialDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.categoriaESocial).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
