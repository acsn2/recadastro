/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { RecadastroTestModule } from '../../../test.module';
import { EscolaridadeComponent } from 'app/entities/escolaridade/escolaridade.component';
import { EscolaridadeService } from 'app/entities/escolaridade/escolaridade.service';
import { Escolaridade } from 'app/shared/model/escolaridade.model';

describe('Component Tests', () => {
    describe('Escolaridade Management Component', () => {
        let comp: EscolaridadeComponent;
        let fixture: ComponentFixture<EscolaridadeComponent>;
        let service: EscolaridadeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RecadastroTestModule],
                declarations: [EscolaridadeComponent],
                providers: []
            })
                .overrideTemplate(EscolaridadeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EscolaridadeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EscolaridadeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Escolaridade(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.escolaridades[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
