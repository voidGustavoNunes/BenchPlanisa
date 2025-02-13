import { ID } from "@datorama/akita";
import { Municipio } from "./Municipio";

export class Estado{
  id: ID;
  sigla: string;
  municipio: Municipio[];


  constructor(
    id: ID,
    sigla: string = '',
    municipio: Municipio[] = [],
  ) {
    this.id = id;
    this.sigla = sigla;
    this.municipio = municipio;
  }
}
