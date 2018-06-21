import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICategoriaESocial } from 'app/shared/model/categoria-e-social.model';

@Component({
    selector: '-categoria-e-social-detail',
    templateUrl: './categoria-e-social-detail.component.html'
})
export class CategoriaESocialDetailComponent implements OnInit {
    categoriaESocial: ICategoriaESocial;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ categoriaESocial }) => {
            this.categoriaESocial = categoriaESocial;
        });
    }

    previousState() {
        window.history.back();
    }
}
