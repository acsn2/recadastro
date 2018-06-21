import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IServidorTipo } from 'app/shared/model/servidor-tipo.model';

@Component({
    selector: '-servidor-tipo-detail',
    templateUrl: './servidor-tipo-detail.component.html'
})
export class ServidorTipoDetailComponent implements OnInit {
    servidorTipo: IServidorTipo;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ servidorTipo }) => {
            this.servidorTipo = servidorTipo;
        });
    }

    previousState() {
        window.history.back();
    }
}
