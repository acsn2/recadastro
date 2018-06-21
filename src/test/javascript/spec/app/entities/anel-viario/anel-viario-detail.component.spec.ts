/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RecadastroTestModule } from '../../../test.module';
import { AnelViarioDetailComponent } from 'app/entities/anel-viario/anel-viario-detail.component';
import { AnelViario } from 'app/shared/model/anel-viario.model';

describe('Component Tests', () => {
    describe('AnelViario Management Detail Component', () => {
        let comp: AnelViarioDetailComponent;
        let fixture: ComponentFixture<AnelViarioDetailComponent>;
        const route = ({ data: of({ anelViario: new AnelViario(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RecadastroTestModule],
                declarations: [AnelViarioDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AnelViarioDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AnelViarioDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.anelViario).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
