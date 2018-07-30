import { Component, OnInit } from '@angular/core';
import { Treino } from '../../../../generated/entities';

@Component({
  selector: 'app-treinos-form',
  templateUrl: './treinos-form.component.html',
  styleUrls: ['./treinos-form.component.scss']
})
export class TreinosFormComponent implements OnInit {

  treino: Treino;

  // formStep

  constructor() { }

  ngOnInit() {
  }

}
