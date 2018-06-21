import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEscolaridade } from 'app/shared/model/escolaridade.model';

@Component({
    selector: '-escolaridade-detail',
    templateUrl: './escolaridade-detail.component.html'
})
export class EscolaridadeDetailComponent implements OnInit {
    escolaridade: IEscolaridade;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ escolaridade }) => {
            this.escolaridade = escolaridade;
        });
    }

    previousState() {
        window.history.back();
    }
}
