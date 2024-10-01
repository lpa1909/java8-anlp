import {Component, inject, OnInit} from '@angular/core';
import { Observable } from "rxjs";;
import { Subject } from 'rxjs';
import { of } from 'rxjs';

import {
  debounceTime, distinctUntilChanged, switchMap
} from 'rxjs/operators';
import {Blog} from "../../model/blog";
import {BlogServiceService} from "../../services/blog-service.service";
import {CategoryServiceService} from "../../services/category-service.service";
import {PositionServiceService} from "../../services/position-service.service";
import {Category} from "../../model/category";
import {Position} from "../../model/position";
@Component({
  selector: 'app-search-blog-components',
  templateUrl: './search-blog-components.component.html',
  styleUrl: './search-blog-components.component.css'
})
export class SearchBlogComponentsComponent implements OnInit{
  blog$: Observable<Blog[]> = of([]);
  categoryService = inject(CategoryServiceService)
  positionService = inject(PositionServiceService)
  listCategorys: Category[] = [];
  listPositions: Position[] = [];
  private searchedSubject = new Subject<string>();

  constructor(private blogService: BlogServiceService) {

  }
  search(searchedString: string): void {
    console.log(`searchedString = ${searchedString}`);
    this.searchedSubject.next(searchedString);
  }

  ngOnInit() {
    this.blog$ = this.searchedSubject.pipe(
      debounceTime(100), // wait 300ms after each keystroke before considering the searched string
      distinctUntilChanged(),// ignore new string if same as previous string
      switchMap((searchedString: string) => this.blogService.searchMovies(searchedString))
    );
  }


}
