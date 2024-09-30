import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { MessagesComponent } from './components/messages/messages.component';
import { MovieDetailComponent } from './components/movie-detail/movie-detail.component';
import { MovieSearchComponent } from './components/movie-search/movie-search.component';
import { MoviesComponent } from './components/movies/movies.component';

@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    MessagesComponent,
    MovieDetailComponent,
    MovieSearchComponent,
    MoviesComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
