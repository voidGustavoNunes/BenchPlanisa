import { ID } from "@datorama/akita";
import { TipoLocalidade } from "./enum/TipoLocalidade";
import { ComparacaoDTO } from "./dto/ComparacaoDTO";

export class BenchMark {
  id: ID;
  nome: string;
  localidade1: string;
  localidade2: string;
  estadoLocalidade1: string;
  estadoLocalidade2: string;
  dataInicial: Date;
  dataFinal: Date;
  comparacao: ComparacaoDTO[] = [];
  tipoLocalidade: TipoLocalidade;

  constructor(
    id: ID,
    nome: string = '',
    localidade1: string = '',
    localidade2: string = '',
    estadoLocalidade1: string = '',
    estadoLocalidade2: string = '',
    dataInicial: Date = new Date(),
    dataFinal: Date = new Date(),
    comparacao: ComparacaoDTO[] = [],
    tipoLocalidade: TipoLocalidade,
  ) {
    this.id = id;
    this.nome = nome;
    this.estadoLocalidade1 = estadoLocalidade1;
    this.estadoLocalidade2 = estadoLocalidade2;
    this.localidade1 = localidade1;
    this.localidade2 = localidade2;
    this.dataInicial = dataInicial;
    this.dataFinal = dataFinal;
    this.comparacao = comparacao;
    this.tipoLocalidade = tipoLocalidade;
  }

}


