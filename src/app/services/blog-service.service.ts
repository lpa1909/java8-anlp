import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {catchError, map, Observable, of, tap, throwError} from "rxjs";
import {Blog} from "../model/blog";

@Injectable({
  providedIn: 'root'
})
export class BlogServiceService {

  private url_blog = 'http://localhost:3000/blogs';


  getBlogs():Observable<Blog[]> {
    return this.http.get<Blog[]>(this.url_blog);
  }


  deleteBlogs(id: number): Observable<Blog> {
    return this.http.delete<Blog>(`${this.url_blog}/${id}`);
  }

  searchMovies(typedString: string): Observable<Blog[]> {
    if (!typedString.trim()) {
      return of([]);
    }
    return this.http.get<Blog[]>(this.url_blog).pipe(
      map(blog => blog.filter(blog => blog.title.toLowerCase().startsWith(typedString.toLowerCase())))
    );
  }

  // private updateBlogIds() {
  //   this.getBlogs().subscribe(blogs => {
  //     blogs.forEach((blog, index) => {
  //       blog.id = index + 1;
  //     });
  //     this.http.put(this.url_blog, blogs).subscribe();
  //   });
  // }



  constructor(private http: HttpClient) { }
}
