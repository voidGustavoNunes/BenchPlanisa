import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Estado } from "../modules/Estado";
import { Municipio } from "../modules/Municipio";

@Injectable({
  providedIn: 'root'
})
export class LocalidadeService{

  protected baseUrl = 'http://localhost:8080/api/localidade';

  constructor(protected httpClient: HttpClient) {

  }

  popularEstadosEMunicipios(): Observable<void>{
    return this.httpClient.post<void>(`${this.baseUrl}/popularEstadosEMunicipios`, {});
  }

  getEstados(): Observable<Estado[]> {
    return this.httpClient.get<any[]>(`${this.baseUrl}/estados`);
  }

  getMunicipios(): Observable<Municipio[]> {
    return this.httpClient.get<any[]>(`${this.baseUrl}/municipios`);
  }
}
