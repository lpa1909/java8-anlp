import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Position} from "../model/position";

@Injectable({
  providedIn: 'root'
})
export class PositionServiceService {
  private url_position = 'http://localhost:3000/position'

  getPositions(): Observable<Position[]> {
    return this.http.get<Position[]>(this.url_position);
  }

  constructor(private http: HttpClient) { }
}
