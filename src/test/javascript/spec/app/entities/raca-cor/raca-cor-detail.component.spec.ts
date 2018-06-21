/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RecadastroTestModule } from '../../../test.module';
import { RacaCorDetailComponent } from 'app/entities/raca-cor/raca-cor-detail.component';
import { RacaCor } from 'app/shared/model/raca-cor.model';

describe('Component Tests', () => {
    describe('RacaCor Management Detail Component', () => {
        let comp: RacaCorDetailComponent;
        let fixture: ComponentFixture<RacaCorDetailComponent>;
        const route = ({ data: of({ racaCor: new RacaCor(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RecadastroTestModule],
                declarations: [RacaCorDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RacaCorDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RacaCorDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.racaCor).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
