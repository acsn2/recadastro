/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { RecadastroTestModule } from '../../../test.module';
import { RegimePrevidenciarioComponent } from 'app/entities/regime-previdenciario/regime-previdenciario.component';
import { RegimePrevidenciarioService } from 'app/entities/regime-previdenciario/regime-previdenciario.service';
import { RegimePrevidenciario } from 'app/shared/model/regime-previdenciario.model';

describe('Component Tests', () => {
    describe('RegimePrevidenciario Management Component', () => {
        let comp: RegimePrevidenciarioComponent;
        let fixture: ComponentFixture<RegimePrevidenciarioComponent>;
        let service: RegimePrevidenciarioService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RecadastroTestModule],
                declarations: [RegimePrevidenciarioComponent],
                providers: []
            })
                .overrideTemplate(RegimePrevidenciarioComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RegimePrevidenciarioComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RegimePrevidenciarioService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new RegimePrevidenciario(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.regimePrevidenciarios[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
