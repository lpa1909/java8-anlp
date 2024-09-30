import {Component, Input} from '@angular/core';
import {Movie} from "../../model/movie";

@Component({
  selector: 'app-movie-detail',
  templateUrl: './movie-detail.component.html',
  styleUrl: './movie-detail.component.css'
})
export class MovieDetailComponent {
  @Input() movie: Movie = new Movie();
  constructor() { }

  ngOnInit() {
  }
}
