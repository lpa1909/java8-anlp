import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {ListBlogComponentsComponent} from "./components/list-blog-components/list-blog-components.component";
import {SearchBlogComponentsComponent} from "./components/search-blog-components/search-blog-components.component";
import {EditBlogComponentsComponent} from "./components/edit-blog-components/edit-blog-components.component";

const routes: Routes = [
  {
    path: 'listBlogs',
    component: ListBlogComponentsComponent
  },
  {
    path: 'searchListBlogs',
    component: SearchBlogComponentsComponent
  },
  {
    path: 'edit/:id',
    component: EditBlogComponentsComponent
  },
  {
    path: '**',
    redirectTo: '/listBlogs'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
