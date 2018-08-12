import { Component, OnInit, ViewChild, Input } from '@angular/core';
import { MatPaginator, MatTableDataSource } from '@angular/material';

@Component({
  selector: 'app-paginator-default',
  templateUrl: './paginator-default.component.html',
  styleUrls: ['./paginator-default.component.scss']
})
export class PaginatorDefaultComponent implements OnInit {

  @Input() dadosTable: MatTableDataSource<any>;

  constructor() { }

  ngOnInit() {
  }
  
  @ViewChild(MatPaginator) paginator: MatPaginator;

  /**
   * Set the paginator after the view init since this component will
   * be able to query its view for the initialized paginator.
   */
  ngAfterViewInit() {
    this.dadosTable.paginator = this.paginator;
  }

}
