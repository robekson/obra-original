/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ObrasTestModule } from '../../../test.module';
import { ObrasDetailComponent } from 'app/entities/obras/obras-detail.component';
import { Obras } from 'app/shared/model/obras.model';

describe('Component Tests', () => {
    describe('Obras Management Detail Component', () => {
        let comp: ObrasDetailComponent;
        let fixture: ComponentFixture<ObrasDetailComponent>;
        const route = ({ data: of({ obras: new Obras(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ObrasTestModule],
                declarations: [ObrasDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ObrasDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ObrasDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.obras).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
