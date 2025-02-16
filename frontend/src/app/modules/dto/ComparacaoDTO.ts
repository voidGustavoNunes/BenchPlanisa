import { ID } from "@datorama/akita";
import { BenchMark } from "../BenchMark";
import { Comparacao } from "../Comparacao";


export class ComparacaoDTO{
  id : ID;
  benchMark: BenchMark;
  dadosComparacao: Comparacao[];

  constructor(
    id: ID,
    benchMark: BenchMark,
    dadosComparacao: Comparacao[] = []
  ){
    this.id = id;
    this.benchMark = benchMark;
    this.dadosComparacao = dadosComparacao;
  }

}
