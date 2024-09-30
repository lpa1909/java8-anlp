import { Injectable } from '@angular/core';
import {fakeMovies} from "../fake-movie";
import {Movie} from "../model/movie";
import {map, Observable, of} from "rxjs";
import {MessageService} from "./message.service";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class MovieService {
  private url = 'http://localhost:3000/movies'

  getMovies(): Observable<Movie[]> {
    // this.messageService.add(`${ new Date().toLocaleString()}. Get movie list`);
    // return of(fakeMovies);
    return this.http.get<Movie[]>(this.url);
  }
  getMovieFromId(id: number): Observable<Movie> {
    return this.http.get<Movie>(`${this.url}/${id}`)
  }

  /** PUT: update the movie on the server */
  updateMovie(movie: Movie): Observable<any> {
    return this.http.put(`${this.url}/${movie.id}`, movie);
  }
  /** POST: add a new movie to the server */
  addMovie(newMovie: Movie): Observable<Movie> {
    return this.http.post<Movie>(this.url, newMovie);
  }

  /** DELETE: delete the movie from the server */
  deleteMovie(movieId: number): Observable<Movie> {
    return this.http.delete<Movie>(`${this.url}/${movieId}`);
  }

  /* GET movies whose name contains searched string */
  searchMovies(typedString: string): Observable<Movie[]> {
    if (!typedString.trim()) {
      return of([]);
    }
    return this.http.get<Movie[]>(this.url).pipe(
      map(movies => movies.filter(movie => movie.name.toLowerCase().startsWith(typedString.toLowerCase())))
    );
  }
  constructor(public messageService: MessageService, private http : HttpClient) { }
}
