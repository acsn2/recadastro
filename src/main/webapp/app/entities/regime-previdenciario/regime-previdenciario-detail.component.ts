import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRegimePrevidenciario } from 'app/shared/model/regime-previdenciario.model';

@Component({
    selector: '-regime-previdenciario-detail',
    templateUrl: './regime-previdenciario-detail.component.html'
})
export class RegimePrevidenciarioDetailComponent implements OnInit {
    regimePrevidenciario: IRegimePrevidenciario;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ regimePrevidenciario }) => {
            this.regimePrevidenciario = regimePrevidenciario;
        });
    }

    previousState() {
        window.history.back();
    }
}
