/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { RecadastroTestModule } from '../../../test.module';
import { RacaCorComponent } from 'app/entities/raca-cor/raca-cor.component';
import { RacaCorService } from 'app/entities/raca-cor/raca-cor.service';
import { RacaCor } from 'app/shared/model/raca-cor.model';

describe('Component Tests', () => {
    describe('RacaCor Management Component', () => {
        let comp: RacaCorComponent;
        let fixture: ComponentFixture<RacaCorComponent>;
        let service: RacaCorService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RecadastroTestModule],
                declarations: [RacaCorComponent],
                providers: []
            })
                .overrideTemplate(RacaCorComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RacaCorComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RacaCorService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new RacaCor(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.racaCors[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
