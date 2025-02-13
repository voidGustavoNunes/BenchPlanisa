import { Injectable } from "@angular/core";
import { GenericService } from "../components/base/genericService";
import { BenchMark } from "../modules/BenchMark";
import { ID } from "@datorama/akita";
import { HttpClient } from "@angular/common/http";
import { TipoLocalidade } from "../modules/enum/TipoLocalidade";
import { Resultado } from "../modules/Resultado";

@Injectable({
  providedIn: 'root'
})
export class BenchMarkService extends GenericService<BenchMark, ID>{

  protected override baseUrl = 'http://localhost:8080/api/benchmarks';

  constructor(override httpClient: HttpClient) {
    super(httpClient);
  }

  compararLocalidades(localidade1: string, localidade2: string, tipoLocalidade: TipoLocalidade, dataInicial: Date, dataFinal: Date) {
    return this.httpClient.get<Resultado[]>(`${this.baseUrl}/?localidade1=${localidade1}/?localidade2=${localidade2}/?tipoLocalidade=${tipoLocalidade}/?dataInicial=${dataInicial}/?dataFinal=${dataFinal}`);
  }

}
