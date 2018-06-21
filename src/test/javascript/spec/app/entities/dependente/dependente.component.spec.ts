/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { RecadastroTestModule } from '../../../test.module';
import { DependenteComponent } from 'app/entities/dependente/dependente.component';
import { DependenteService } from 'app/entities/dependente/dependente.service';
import { Dependente } from 'app/shared/model/dependente.model';

describe('Component Tests', () => {
    describe('Dependente Management Component', () => {
        let comp: DependenteComponent;
        let fixture: ComponentFixture<DependenteComponent>;
        let service: DependenteService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RecadastroTestModule],
                declarations: [DependenteComponent],
                providers: []
            })
                .overrideTemplate(DependenteComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DependenteComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DependenteService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Dependente(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.dependentes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
