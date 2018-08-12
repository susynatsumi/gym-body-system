import { MatPaginatorIntl } from '@angular/material';

const labelRange = (page: number, pageSize: number, length: number) => {
  if (length == 0 || pageSize == 0) { return `0 de ${length}`; }
  
  length = Math.max(length, 0);

  const startIndex = page * pageSize;

  // If the start index exceeds the list length, do not try and fix the end index to the end.
  const endIndex = startIndex < length ?
      Math.min(startIndex + pageSize, length) :
      startIndex + pageSize;

  return `Item ${startIndex + 1} até ${endIndex}. Total de itens: ${length}`;
}


export function getLabelsPaginatorIntl() {
  const paginatorIntl = new MatPaginatorIntl();
  
  paginatorIntl.itemsPerPageLabel = 'Items por página:';
  paginatorIntl.nextPageLabel = 'Próxima';
  paginatorIntl.previousPageLabel = 'Anterior';
  paginatorIntl.getRangeLabel = labelRange;
  
  return paginatorIntl;
}