/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { RecadastroTestModule } from '../../../test.module';
import { ServidorTipoComponent } from 'app/entities/servidor-tipo/servidor-tipo.component';
import { ServidorTipoService } from 'app/entities/servidor-tipo/servidor-tipo.service';
import { ServidorTipo } from 'app/shared/model/servidor-tipo.model';

describe('Component Tests', () => {
    describe('ServidorTipo Management Component', () => {
        let comp: ServidorTipoComponent;
        let fixture: ComponentFixture<ServidorTipoComponent>;
        let service: ServidorTipoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RecadastroTestModule],
                declarations: [ServidorTipoComponent],
                providers: []
            })
                .overrideTemplate(ServidorTipoComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ServidorTipoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ServidorTipoService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ServidorTipo(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.servidorTipos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
