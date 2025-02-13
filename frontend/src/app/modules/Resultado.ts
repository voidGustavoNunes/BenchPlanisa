import { BenchMark } from './BenchMark';
import { ID } from "@datorama/akita";

export class Resultado {
  id: ID;
  data: Date;
  pais: string;
  casosConfirmados: number;
  mortes: number;
  taxaLetalidade: number;
  benchMark: BenchMark | undefined;

  constructor(
    id: ID,
    data: Date = new Date(),
    pais: string = '',
    casosConfirmados: number = 0,
    mortes: number = 0,
    taxaLetalidade: number = 0,
    benchMark: BenchMark,
  ) {
    this.id = id;
    this.data = data;
    this.pais = pais;
    this.casosConfirmados = casosConfirmados;
    this.mortes = mortes;
    this.taxaLetalidade = taxaLetalidade;
    this.benchMark = benchMark;
  }

}
