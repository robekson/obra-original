/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ObrasTestModule } from '../../../test.module';
import { GastoDetailComponent } from 'app/entities/gasto/gasto-detail.component';
import { Gasto } from 'app/shared/model/gasto.model';

describe('Component Tests', () => {
    describe('Gasto Management Detail Component', () => {
        let comp: GastoDetailComponent;
        let fixture: ComponentFixture<GastoDetailComponent>;
        const route = ({ data: of({ gasto: new Gasto(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ObrasTestModule],
                declarations: [GastoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(GastoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GastoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.gasto).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
