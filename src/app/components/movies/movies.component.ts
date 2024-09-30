import {Component, OnInit} from '@angular/core';
import {Movie} from "../../model/movie";
import {fakeMovies} from "../../fake-movie";
import {MovieService} from "../../services/movie.service";

@Component({
  selector: 'app-movies',
  templateUrl: './movies.component.html',
  styleUrl: './movies.component.css'
})
export class MoviesComponent implements OnInit {
  movie: Movie = {
    id: 1,
    name: "Star Wars",
    releaseYear: 1977
  }
  // movies = fakeMovies;
  movies: Movie[] = [];
  constructor(private movieService: MovieService) {

  }
  getMoviesFromServices(): void {
    this.movieService.getMovies().subscribe((res) => {
      this.movies = res;
    });
  }
  ngOnInit() {
    this.getMoviesFromServices();
  }
  //Action when select a Movie in List item
  selectedMovie: Movie = new Movie();
  onSelect(movie: Movie): void {
    this.selectedMovie = movie;
    console.log(`selectedMovie = ${JSON.stringify(this.selectedMovie)}`);
    // alert(`selectedMovie = ${JSON.stringify(this.selectedMovie)}`);
  }
}
