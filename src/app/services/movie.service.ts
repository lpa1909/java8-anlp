import { Injectable } from '@angular/core';
import {fakeMovies} from "../fake-movie";
import {Movie} from "../model/movie";
import {Observable, of} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class MovieService {

  getMovies(): Observable<Movie[]> {
    return of(fakeMovies);
  }
  constructor() { }
}
