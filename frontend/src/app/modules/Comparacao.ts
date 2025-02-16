import { ID } from "@datorama/akita";
import { ComparacaoDTO } from "./dto/ComparacaoDTO";

export class Comparacao{
  id: ID;
  data: Date;
  identificadorLocalidade1: string;
  confirmados1: number;
  mortes1: number;
  taxaLetalidade1: number;
  populacaoEstimada1: number;
  casosPor100kHab1: number;
  identificadorLocalidade2: number;
  confirmados2: number;
  mortes2: number;
  taxaLetalidade2: number;
  populacaoEstimada2: number;
  casosPor100kHab2: number;
  diferencaConfirmados: number;
  diferencaMortes: number;
  diferencaTaxaLetalidade: number;
  diferencaCasosPor100kHab: number;

  comparacao: ComparacaoDTO[];

  constructor(
    id: ID,
    data: Date,
    identificadorLocalidade1: string,
    confirmados1: number,
    mortes1: number,
    taxaLetalidade1: number,
    populacaoEstimada1: number,
    casosPor100kHab1: number,
    identificadorLocalidade2: number,
    confirmados2: number,
    mortes2: number,
    taxaLetalidade2: number,
    populacaoEstimada2: number,
    casosPor100kHab2: number,
    diferencaConfirmados: number,
    diferencaMortes: number,
    diferencaTaxaLetalidade: number,
    diferencaCasosPor100kHab: number,
    comparacao: ComparacaoDTO[]= []
  ) {
    this.id = id;
    this.data = data;
    this.identificadorLocalidade1 = identificadorLocalidade1;
    this.confirmados1 = confirmados1;
    this.mortes1 = mortes1;
    this.taxaLetalidade1 = taxaLetalidade1;
    this.populacaoEstimada1 = populacaoEstimada1;
    this.casosPor100kHab1 = casosPor100kHab1;
    this.identificadorLocalidade2 = identificadorLocalidade2;
    this.confirmados2 = confirmados2;
    this.mortes2 = mortes2;
    this.taxaLetalidade2 = taxaLetalidade2;
    this.populacaoEstimada2 = populacaoEstimada2;
    this.casosPor100kHab2 = casosPor100kHab2;
    this.diferencaConfirmados = diferencaConfirmados;
    this.diferencaMortes = diferencaMortes;
    this.diferencaTaxaLetalidade = diferencaTaxaLetalidade;
    this.diferencaCasosPor100kHab = diferencaCasosPor100kHab;
    this.comparacao = comparacao;

  }

}
