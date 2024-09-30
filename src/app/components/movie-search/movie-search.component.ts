import { Component } from '@angular/core';

import { Observable } from "rxjs";;
import { Subject } from 'rxjs';
import { of } from 'rxjs';

import {
  debounceTime, distinctUntilChanged, switchMap
} from 'rxjs/operators';
import {Movie} from "../../model/movie";
import {MovieService} from "../../services/movie.service";


@Component({
  selector: 'app-movie-search',
  templateUrl: './movie-search.component.html',
  styleUrl: './movie-search.component.css'
})
export class MovieSearchComponent {
  movies$: Observable<Movie[]> = of([]);
  private searchedSubject = new Subject<string>();

  constructor(private movieService: MovieService) { }
  search(searchedString: string): void {
    console.log(`searchedString = ${searchedString}`);
    this.searchedSubject.next(searchedString);
  }

  ngOnInit() {
    this.movies$ = this.searchedSubject.pipe(
      debounceTime(300), // wait 300ms after each keystroke before considering the searched string
      distinctUntilChanged(),// ignore new string if same as previous string
      switchMap((searchedString: string) => this.movieService.searchMovies(searchedString))
    );
  }
}
