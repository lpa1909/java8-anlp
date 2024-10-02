import {Component, Input} from '@angular/core';
import {Blog} from "../../model/blog";

@Component({
  selector: 'app-blog-detail',
  templateUrl: './blog-detail.component.html',
  styleUrl: './blog-detail.component.css'
})
export class BlogDetailComponent {
  @Input() blogObj: Blog = {
    id: 0,
    title: '',
    des: '',
    detail: '',
    category: 0,
    public: false,
    data_pubblic: '',
    position: [],
    thumbs: ''
  };
}
