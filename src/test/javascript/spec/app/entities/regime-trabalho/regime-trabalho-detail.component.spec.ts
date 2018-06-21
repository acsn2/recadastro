/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RecadastroTestModule } from '../../../test.module';
import { RegimeTrabalhoDetailComponent } from 'app/entities/regime-trabalho/regime-trabalho-detail.component';
import { RegimeTrabalho } from 'app/shared/model/regime-trabalho.model';

describe('Component Tests', () => {
    describe('RegimeTrabalho Management Detail Component', () => {
        let comp: RegimeTrabalhoDetailComponent;
        let fixture: ComponentFixture<RegimeTrabalhoDetailComponent>;
        const route = ({ data: of({ regimeTrabalho: new RegimeTrabalho(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RecadastroTestModule],
                declarations: [RegimeTrabalhoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RegimeTrabalhoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RegimeTrabalhoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.regimeTrabalho).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
