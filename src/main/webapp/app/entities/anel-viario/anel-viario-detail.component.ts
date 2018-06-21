import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAnelViario } from 'app/shared/model/anel-viario.model';

@Component({
    selector: '-anel-viario-detail',
    templateUrl: './anel-viario-detail.component.html'
})
export class AnelViarioDetailComponent implements OnInit {
    anelViario: IAnelViario;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ anelViario }) => {
            this.anelViario = anelViario;
        });
    }

    previousState() {
        window.history.back();
    }
}
