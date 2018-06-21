/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { RecadastroTestModule } from '../../../test.module';
import { AnelViarioComponent } from 'app/entities/anel-viario/anel-viario.component';
import { AnelViarioService } from 'app/entities/anel-viario/anel-viario.service';
import { AnelViario } from 'app/shared/model/anel-viario.model';

describe('Component Tests', () => {
    describe('AnelViario Management Component', () => {
        let comp: AnelViarioComponent;
        let fixture: ComponentFixture<AnelViarioComponent>;
        let service: AnelViarioService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RecadastroTestModule],
                declarations: [AnelViarioComponent],
                providers: []
            })
                .overrideTemplate(AnelViarioComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AnelViarioComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AnelViarioService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new AnelViario(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.anelViarios[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
