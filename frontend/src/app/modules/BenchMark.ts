import { ID } from "@datorama/akita";
import { Resultado } from "./Resultado";

export class BenchMark {
  id: ID | undefined;
  nome: string;
  pais1: string;
  pais2: string;
  dataInicial: Date;
  dataFinal: Date;
  resultados: Resultado[] = [];

  constructor(
    id: | undefined = undefined,
    nome: string = '',
    pais1: string = '',
    pais2: string = '',
    dataInicial: Date = new Date(),
    dataFinal: Date = new Date(),
    resultados: Resultado[] = [],
  ) {
    this.id = id;
    this.nome = nome;
    this.pais1 = pais1;
    this.pais2 = pais2;
    this.dataInicial = dataInicial;
    this.dataFinal = dataFinal;
    this.resultados = resultados;
  }

}


