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
    //this.movies = this.movieService.getMovies();
    this.movieService.getMovies().subscribe(updatedMovies => this.movies = updatedMovies);
  }
  ngOnInit() {
    this.getMoviesFromServices();
  }

  add(name: string, releaseYear:string): void {
    name = name.trim();
    if (Number.isNaN(Number(releaseYear)) || !name || Number(releaseYear) === 0) {
      alert('Name must not be blank, Release year must be a number');
      return;
    }
    const newMovie: Movie = new Movie();
    newMovie.name = name;
    newMovie.releaseYear = Number(releaseYear);
    this.movieService.addMovie(newMovie)
      .subscribe(insertedMovie => {
        this.movies.push(insertedMovie);
      });
  }

  delete(movieId: number): void {
    this.movieService.deleteMovie(movieId).subscribe(() => {
      this.movies = this.movies.filter(eachMovie => eachMovie.id !== movieId);
      alert('Delete success!!!')
      this.getMoviesFromServices()
    });
  }



  //Action when select a Movie in List item
  // selectedMovie: Movie = new Movie();
  // onSelect(movie: Movie): void {
  //   this.selectedMovie = movie;
  //   console.log(`selectedMovie = ${JSON.stringify(this.selectedMovie)}`);
  //   // alert(`selectedMovie = ${JSON.stringify(this.selectedMovie)}`);
  // }
}
