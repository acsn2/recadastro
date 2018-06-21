import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrgao } from 'app/shared/model/orgao.model';

@Component({
    selector: '-orgao-detail',
    templateUrl: './orgao-detail.component.html'
})
export class OrgaoDetailComponent implements OnInit {
    orgao: IOrgao;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ orgao }) => {
            this.orgao = orgao;
        });
    }

    previousState() {
        window.history.back();
    }
}
