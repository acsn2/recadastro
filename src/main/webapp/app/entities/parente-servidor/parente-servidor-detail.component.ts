import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IParenteServidor } from 'app/shared/model/parente-servidor.model';

@Component({
    selector: '-parente-servidor-detail',
    templateUrl: './parente-servidor-detail.component.html'
})
export class ParenteServidorDetailComponent implements OnInit {
    parenteServidor: IParenteServidor;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ parenteServidor }) => {
            this.parenteServidor = parenteServidor;
        });
    }

    previousState() {
        window.history.back();
    }
}
