import { ID } from "@datorama/akita";
import { Estado } from "./Estado";

export class Municipio{
  id: ID;
  nome: string;
  estado?: Estado;

  constructor(
    id: ID,
    estado: Estado,
    nome: string = '',
  ) {
    this.id = id;
    this.nome = nome;
    this.estado = estado;
  }

}
