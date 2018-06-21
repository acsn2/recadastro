import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRacaCor } from 'app/shared/model/raca-cor.model';

@Component({
    selector: '-raca-cor-detail',
    templateUrl: './raca-cor-detail.component.html'
})
export class RacaCorDetailComponent implements OnInit {
    racaCor: IRacaCor;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ racaCor }) => {
            this.racaCor = racaCor;
        });
    }

    previousState() {
        window.history.back();
    }
}
