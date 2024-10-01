import {Component, inject, Input, OnInit} from '@angular/core';
import {BlogServiceService} from "../../services/blog-service.service";
import {Blog} from "../../model/blog";
import {CategoryServiceService} from "../../services/category-service.service";
import {PositionServiceService} from "../../services/position-service.service";
import {Category} from "../../model/category";
import {Position} from "../../model/position";

@Component({
  selector: 'app-list-blog-components',
  templateUrl: './list-blog-components.component.html',
  styleUrl: './list-blog-components.component.css'
})
export class ListBlogComponentsComponent implements OnInit {
  @Input() searchResults: Blog[] | null = null;
  blogService = inject(BlogServiceService)
  categoryService = inject(CategoryServiceService)
  positionService = inject(PositionServiceService)
  listBlogs: Blog[] = [];
  listCategorys: Category[] = [];
  listPositions: Position[] = [];
  blogsToDisplay: Blog[] = [];

  constructor() {
  }

  ngOnInit(): void {
    this.getAllBlog();
    this.getAllCategory();
    this.getALlPosition();
  }

  getALlPosition() {
    this.positionService.getPositions().subscribe((res) => {
      this.listPositions = res;
    })
  }

  getAllCategory() {
    this.categoryService.getCategories().subscribe((res) => {
      this.listCategorys = res;
      console.log(this.listCategorys)
    })
  }

  getAllBlog() {
    this.blogService.getBlogs().subscribe((res) => {
      this.listBlogs = res;
    })
  }

  getCategoryName(id: number): string {
    const category = this.listCategorys.find(category => +category.id === id);
    console.log(category)
    return category ? category.name : "Unknown";
  }

  getPositionNames(positionIds: number[]): string[] {
    const positionNames = [];
    for (const positionId of positionIds) {
      const position = this.listPositions.find(p => +p.id === positionId);
      positionNames.push(position ? position.name : 'Unknown');
    }
    return positionNames;
  }

  deleteBlog(id: number): void {
    const confirm_delete = confirm("Bạn chắc chắn muốn xóa tin tức này ?")
    if (confirm_delete) {
      this.blogService.deleteBlogs(id).subscribe(() => {
        this.listBlogs = this.listBlogs.filter(blog => blog.id !== id)
        alert("Xóa thành công !!!!!!");
        this.getAllBlog();
        this.getAllCategory();
        this.getALlPosition();
      })
    }
  }

  changeStatus(id: number, status: boolean): void {
    const confirm_change = confirm("Bạn có chắc chắn muốn thay đổi trạng thái của bài báo không?");
    if (confirm_change) {
      this.blogService.updateDataPublic(id, Boolean(status)).subscribe(() => {
        alert('Thay đổi trạng thái thành công')
        this.getAllBlog();
        this.getAllCategory();
        this.getALlPosition();
      })
    }
  }

  ngDoCheck(): void {
    this.blogsToDisplay = this.searchResults?.length ? this.searchResults : this.listBlogs;
  }
}
