import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRegimeTrabalho } from 'app/shared/model/regime-trabalho.model';

@Component({
    selector: '-regime-trabalho-detail',
    templateUrl: './regime-trabalho-detail.component.html'
})
export class RegimeTrabalhoDetailComponent implements OnInit {
    regimeTrabalho: IRegimeTrabalho;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ regimeTrabalho }) => {
            this.regimeTrabalho = regimeTrabalho;
        });
    }

    previousState() {
        window.history.back();
    }
}
