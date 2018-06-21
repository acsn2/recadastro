/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { RecadastroTestModule } from '../../../test.module';
import { RegimeTrabalhoComponent } from 'app/entities/regime-trabalho/regime-trabalho.component';
import { RegimeTrabalhoService } from 'app/entities/regime-trabalho/regime-trabalho.service';
import { RegimeTrabalho } from 'app/shared/model/regime-trabalho.model';

describe('Component Tests', () => {
    describe('RegimeTrabalho Management Component', () => {
        let comp: RegimeTrabalhoComponent;
        let fixture: ComponentFixture<RegimeTrabalhoComponent>;
        let service: RegimeTrabalhoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RecadastroTestModule],
                declarations: [RegimeTrabalhoComponent],
                providers: []
            })
                .overrideTemplate(RegimeTrabalhoComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RegimeTrabalhoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RegimeTrabalhoService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new RegimeTrabalho(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.regimeTrabalhos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
