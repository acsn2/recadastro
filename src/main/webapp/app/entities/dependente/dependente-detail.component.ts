import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDependente } from 'app/shared/model/dependente.model';

@Component({
    selector: '-dependente-detail',
    templateUrl: './dependente-detail.component.html'
})
export class DependenteDetailComponent implements OnInit {
    dependente: IDependente;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ dependente }) => {
            this.dependente = dependente;
        });
    }

    previousState() {
        window.history.back();
    }
}
