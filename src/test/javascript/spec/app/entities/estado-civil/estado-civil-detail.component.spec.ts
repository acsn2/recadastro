/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RecadastroTestModule } from '../../../test.module';
import { EstadoCivilDetailComponent } from 'app/entities/estado-civil/estado-civil-detail.component';
import { EstadoCivil } from 'app/shared/model/estado-civil.model';

describe('Component Tests', () => {
    describe('EstadoCivil Management Detail Component', () => {
        let comp: EstadoCivilDetailComponent;
        let fixture: ComponentFixture<EstadoCivilDetailComponent>;
        const route = ({ data: of({ estadoCivil: new EstadoCivil(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RecadastroTestModule],
                declarations: [EstadoCivilDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(EstadoCivilDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EstadoCivilDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.estadoCivil).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
