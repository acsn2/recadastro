/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { RecadastroTestModule } from '../../../test.module';
import { CategoriaESocialComponent } from 'app/entities/categoria-e-social/categoria-e-social.component';
import { CategoriaESocialService } from 'app/entities/categoria-e-social/categoria-e-social.service';
import { CategoriaESocial } from 'app/shared/model/categoria-e-social.model';

describe('Component Tests', () => {
    describe('CategoriaESocial Management Component', () => {
        let comp: CategoriaESocialComponent;
        let fixture: ComponentFixture<CategoriaESocialComponent>;
        let service: CategoriaESocialService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RecadastroTestModule],
                declarations: [CategoriaESocialComponent],
                providers: []
            })
                .overrideTemplate(CategoriaESocialComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CategoriaESocialComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CategoriaESocialService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new CategoriaESocial(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.categoriaESocials[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
