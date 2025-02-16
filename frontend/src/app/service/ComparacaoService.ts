import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { ComparacaoDTO } from "../modules/dto/ComparacaoDTO";
import { catchError, map, Observable, throwError } from "rxjs";
import { Comparacao } from "../modules/Comparacao";
import { ID } from "@datorama/akita";


@Injectable({
  providedIn: 'root'
})
export class ComparacaoService{

  protected baseUrl = 'http://localhost:8080/api/comparacao';

  constructor(protected httpClient: HttpClient) {
  }

  carregarDadosComparacao(benchmarkId: ID): Observable<Comparacao[]> {
    return this.httpClient.get<ComparacaoDTO>(`${this.baseUrl}/buscar/${benchmarkId}`).pipe(
      map(response => {
        if (response && response.dadosComparacao) {
          return response.dadosComparacao.sort((a, b) =>
            new Date(a.data).getTime() - new Date(b.data).getTime()
          );
        }
        return [];
      }),
      catchError(error => {
        console.error('Erro ao carregar dados de comparação:', error);
        return throwError(() => error);
      })
    );
  }

  public carregarBenchMarks(): Observable<ComparacaoDTO[]> {
    return this.httpClient.get<ComparacaoDTO[]>(this.baseUrl);
  }

}
