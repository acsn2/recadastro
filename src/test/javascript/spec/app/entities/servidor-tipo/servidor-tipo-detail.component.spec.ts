/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RecadastroTestModule } from '../../../test.module';
import { ServidorTipoDetailComponent } from 'app/entities/servidor-tipo/servidor-tipo-detail.component';
import { ServidorTipo } from 'app/shared/model/servidor-tipo.model';

describe('Component Tests', () => {
    describe('ServidorTipo Management Detail Component', () => {
        let comp: ServidorTipoDetailComponent;
        let fixture: ComponentFixture<ServidorTipoDetailComponent>;
        const route = ({ data: of({ servidorTipo: new ServidorTipo(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RecadastroTestModule],
                declarations: [ServidorTipoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ServidorTipoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ServidorTipoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.servidorTipo).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
