import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IObra } from 'app/shared/model/obra.model';

@Component({
    selector: 'jhi-obra-detail',
    templateUrl: './obra-detail.component.html'
})
export class ObraDetailComponent implements OnInit {
    obra: IObra;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ obra }) => {
            this.obra = obra;
        });
    }

    previousState() {
        window.history.back();
    }
}
