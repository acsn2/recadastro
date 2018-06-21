/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { RecadastroTestModule } from '../../../test.module';
import { EstadoCivilComponent } from 'app/entities/estado-civil/estado-civil.component';
import { EstadoCivilService } from 'app/entities/estado-civil/estado-civil.service';
import { EstadoCivil } from 'app/shared/model/estado-civil.model';

describe('Component Tests', () => {
    describe('EstadoCivil Management Component', () => {
        let comp: EstadoCivilComponent;
        let fixture: ComponentFixture<EstadoCivilComponent>;
        let service: EstadoCivilService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RecadastroTestModule],
                declarations: [EstadoCivilComponent],
                providers: []
            })
                .overrideTemplate(EstadoCivilComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EstadoCivilComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EstadoCivilService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new EstadoCivil(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.estadoCivils[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
