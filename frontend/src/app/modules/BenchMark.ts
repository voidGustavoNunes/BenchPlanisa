import { ID } from "@datorama/akita";
import { Resultado } from "./Resultado";
import { TipoLocalidade } from "./enum/TipoLocalidade";

export class BenchMark {
  id: ID;
  nome: string;
  pais1: string;
  pais2: string;
  dataInicial: Date;
  dataFinal: Date;
  resultados: Resultado[] = [];
  tipoLocalidade: TipoLocalidade;

  constructor(
    id: ID,
    nome: string = '',
    pais1: string = '',
    pais2: string = '',
    dataInicial: Date = new Date(),
    dataFinal: Date = new Date(),
    resultados: Resultado[] = [],
    tipoLocalidade: TipoLocalidade,
  ) {
    this.id = id;
    this.nome = nome;
    this.pais1 = pais1;
    this.pais2 = pais2;
    this.dataInicial = dataInicial;
    this.dataFinal = dataFinal;
    this.resultados = resultados;
    this.tipoLocalidade = tipoLocalidade;
  }

}


