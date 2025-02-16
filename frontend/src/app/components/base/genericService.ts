import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { MatDialog } from "@angular/material/dialog";
import { AlertaComponent } from "./alerta/alerta.component";

@Injectable({
  providedIn: 'root'
})
export class GenericService<T, ID> {

  protected baseUrl = "http://localhost:8080";

  constructor(protected httpClient: HttpClient) { }

  getList(): Observable<T[]> {
    return this.httpClient.get<T[]>(this.baseUrl);
  }

  getEntity(id: ID): Observable<T> {
    return this.httpClient.get<T>(`${this.baseUrl}/${id}`);
  }

  create(entity: T): Observable<T> {
    console.log('Enviando para o backend GENERIC SERVICE:', entity); // 🔍 Debug
    return this.httpClient.post<T>(this.baseUrl, entity);
  }

  update(id: ID, entity: T): Observable<T> {
    return this.httpClient.put<T>(`${this.baseUrl}/${id}`, entity);
  }

  delete(id: ID): Observable<void> {
    return this.httpClient.delete<void>(`${this.baseUrl}/${id}`);
  }
}
