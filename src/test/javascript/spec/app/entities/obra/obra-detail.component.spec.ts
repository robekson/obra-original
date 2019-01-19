/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ObrasTestModule } from '../../../test.module';
import { ObraDetailComponent } from 'app/entities/obra/obra-detail.component';
import { Obra } from 'app/shared/model/obra.model';

describe('Component Tests', () => {
    describe('Obra Management Detail Component', () => {
        let comp: ObraDetailComponent;
        let fixture: ComponentFixture<ObraDetailComponent>;
        const route = ({ data: of({ obra: new Obra(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ObrasTestModule],
                declarations: [ObraDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ObraDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ObraDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.obra).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
