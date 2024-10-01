import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NZ_I18N } from 'ng-zorro-antd/i18n';
import { en_US } from 'ng-zorro-antd/i18n';
import { registerLocaleData } from '@angular/common';
import en from '@angular/common/locales/en';
import { FormsModule } from '@angular/forms';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { provideHttpClient } from '@angular/common/http';
import { ListBlogComponentsComponent } from './components/list-blog-components/list-blog-components.component';
import { CategoryComponentComponent } from './components/category-component/category-component.component';
import { SearchBlogComponentsComponent } from './components/search-blog-components/search-blog-components.component';
import { EditBlogComponentsComponent } from './components/edit-blog-components/edit-blog-components.component';
import {NzTableModule} from "ng-zorro-antd/table";
import {NzGridModule} from "ng-zorro-antd/grid";
import {NzDividerModule} from "ng-zorro-antd/divider";
import {NzTagModule} from "ng-zorro-antd/tag";
import {NzButtonModule} from "ng-zorro-antd/button";
import {NzInputModule} from "ng-zorro-antd/input";
import {NzIconModule} from "ng-zorro-antd/icon";

registerLocaleData(en);

@NgModule({
  declarations: [
    AppComponent,
    ListBlogComponentsComponent,
    CategoryComponentComponent,
    SearchBlogComponentsComponent,
    EditBlogComponentsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    NzTableModule,
    NzGridModule,
    NzDividerModule,
    NzTagModule,
    NzButtonModule,
    NzInputModule,
    NzIconModule
  ],
  providers: [
    { provide: NZ_I18N, useValue: en_US },
    provideAnimationsAsync(),
    provideHttpClient()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
