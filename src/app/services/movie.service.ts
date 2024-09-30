import { Injectable } from '@angular/core';
import {fakeMovies} from "../fake-movie";
import {Movie} from "../model/movie";
import {Observable, of} from "rxjs";
import {MessageService} from "./message.service";

@Injectable({
  providedIn: 'root'
})
export class MovieService {

  getMovies(): Observable<Movie[]> {
    this.messageService.add(`${ new Date().toLocaleString()}. Get movie list`);
    return of(fakeMovies);
  }
  getMovieFromId(id: number): Observable<Movie> {
    const movie = fakeMovies.find(movie => movie.id === id) ?? { id: 0, name: '', releaseYear: 0 }; // Giá trị mặc định kiểu Movies
    return of(movie);
  }
  constructor(public messageService: MessageService) { }
}
