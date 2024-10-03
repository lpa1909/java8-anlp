import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NZ_I18N } from 'ng-zorro-antd/i18n';
import { en_US } from 'ng-zorro-antd/i18n';
import {CommonModule, registerLocaleData} from '@angular/common';
import en from '@angular/common/locales/en';
import {AbstractControl, FormsModule, NonNullableFormBuilder, ReactiveFormsModule} from '@angular/forms';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import {HttpClientModule, provideHttpClient} from '@angular/common/http';
import { ListBlogComponentsComponent } from './components/list-blog-components/list-blog-components.component';
import { CategoryComponentComponent } from './components/category-component/category-component.component';
import { SearchBlogComponentsComponent } from './components/search-blog-components/search-blog-components.component';
import { EditBlogComponentsComponent } from './components/edit-blog-components/edit-blog-components.component';
import {NzTableModule} from "ng-zorro-antd/table";
import {NzGridModule} from "ng-zorro-antd/grid";
import {NzDividerModule} from "ng-zorro-antd/divider";
import {NzTagModule} from "ng-zorro-antd/tag";
import {NzButtonModule} from "ng-zorro-antd/button";
import {NzInputGroupSlotComponent, NzInputModule} from "ng-zorro-antd/input";
import {NzIconModule} from "ng-zorro-antd/icon";
import {NzUploadModule} from "ng-zorro-antd/upload";
import {NzSelectModule} from "ng-zorro-antd/select";
import {NzCheckboxModule} from "ng-zorro-antd/checkbox";
import {NzSwitchModule} from "ng-zorro-antd/switch";
import {NzMenuModule} from "ng-zorro-antd/menu";
import {NzTooltipDirective} from "ng-zorro-antd/tooltip";
import { BlogDetailComponent } from './components/blog-detail/blog-detail.component';
import { NzModalModule } from 'ng-zorro-antd/modal';
import { NzCardModule } from 'ng-zorro-antd/card';
import { NzListModule } from 'ng-zorro-antd/list';
import {NzAlertModule} from "ng-zorro-antd/alert";
import { NzMessageService } from 'ng-zorro-antd/message';
import {NzFormModule} from "ng-zorro-antd/form";
import {NzRadioModule} from "ng-zorro-antd/radio";
import {NzDatePickerModule} from "ng-zorro-antd/date-picker";
import { NzUploadChangeParam } from 'ng-zorro-antd/upload';
registerLocaleData(en);

@NgModule({
  declarations: [
    AppComponent,
    ListBlogComponentsComponent,
    CategoryComponentComponent,
    SearchBlogComponentsComponent,
    EditBlogComponentsComponent,
    BlogDetailComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule ,
    NzTableModule,
    NzGridModule,
    NzDividerModule,
    NzTagModule,
    NzButtonModule,
    NzInputModule,
    NzIconModule,
    NzUploadModule,
    NzSelectModule,
    NzCheckboxModule,
    HttpClientModule,
    NzInputGroupSlotComponent,
    NzSwitchModule,
    NzMenuModule,
    NzTooltipDirective,
    NzModalModule,
    NzListModule,
    NzCardModule,
    NzAlertModule,
    CommonModule,
    NzFormModule,
    NzRadioModule,
    NzDatePickerModule
  ],
  providers: [
    { provide: NZ_I18N, useValue: en_US },
    provideAnimationsAsync(),
    provideHttpClient()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
