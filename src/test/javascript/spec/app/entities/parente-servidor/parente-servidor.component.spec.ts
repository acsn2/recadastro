/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { RecadastroTestModule } from '../../../test.module';
import { ParenteServidorComponent } from 'app/entities/parente-servidor/parente-servidor.component';
import { ParenteServidorService } from 'app/entities/parente-servidor/parente-servidor.service';
import { ParenteServidor } from 'app/shared/model/parente-servidor.model';

describe('Component Tests', () => {
    describe('ParenteServidor Management Component', () => {
        let comp: ParenteServidorComponent;
        let fixture: ComponentFixture<ParenteServidorComponent>;
        let service: ParenteServidorService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RecadastroTestModule],
                declarations: [ParenteServidorComponent],
                providers: []
            })
                .overrideTemplate(ParenteServidorComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ParenteServidorComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ParenteServidorService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ParenteServidor(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.parenteServidors[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
