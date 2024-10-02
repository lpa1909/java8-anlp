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

  getBlogById(id: number):Observable<Blog>{
    return this.http.get<Blog>(`${this.url_blog}/${id}`);
  }


  deleteBlogs(id: number): Observable<Blog> {
    return this.http.delete<Blog>(`${this.url_blog}/${id}`);
  }

  searchMovies(typedString: string): Observable<Blog[]> {
    if (!typedString.trim()) {
      return of([]);
    }
    return this.http.get<Blog[]>(this.url_blog).pipe(
      map(blogs => blogs.filter(blog => blog.title.toLowerCase().includes(typedString.toLowerCase())))
    );
  }

  updateDataPublic(id: number, newStatus: boolean): Observable<Blog> {
    return this.http.patch<Blog>(`${this.url_blog}/${id.toString()}`, { public: newStatus });
  }

  updateBlog(id: number, newBlog: Blog): Observable<Blog> {
    return this.http.patch<Blog>(`${this.url_blog}/${id}`, newBlog);
  }

  addNewBlog(newBlog: Blog): Observable<Blog>{
    return this.http.post<Blog>(this.url_blog, newBlog);
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
