import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEstadoCivil } from 'app/shared/model/estado-civil.model';

@Component({
    selector: '-estado-civil-detail',
    templateUrl: './estado-civil-detail.component.html'
})
export class EstadoCivilDetailComponent implements OnInit {
    estadoCivil: IEstadoCivil;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ estadoCivil }) => {
            this.estadoCivil = estadoCivil;
        });
    }

    previousState() {
        window.history.back();
    }
}
