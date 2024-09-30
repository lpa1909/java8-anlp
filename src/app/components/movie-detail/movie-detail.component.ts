import {Component, Input} from '@angular/core';
import {Movie} from "../../model/movie";
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { MovieService } from '../../services/movie.service';

@Component({
  selector: 'app-movie-detail',
  templateUrl: './movie-detail.component.html',
  styleUrl: './movie-detail.component.css'
})
export class MovieDetailComponent {
  @Input() movie: Movie = new Movie();
  constructor(
    private route: ActivatedRoute,
    private movieService: MovieService,
    private location: Location
  ) { }

  ngOnInit() {
    this.getMovieFromRoute();
  }
  getMovieFromRoute(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.movieService.getMovieFromId(id).subscribe(movie => this.movie = movie);
  }

  goBack(): void {
    this.location.back();
  }

}
