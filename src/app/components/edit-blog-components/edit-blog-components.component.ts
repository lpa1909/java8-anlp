import {Component, inject, OnInit} from '@angular/core';
import {CategoryServiceService} from "../../services/category-service.service";
import {PositionServiceService} from "../../services/position-service.service";
import {Blog} from "../../model/blog";
import {Category} from "../../model/category";
import {Position} from "../../model/position";
import {FormBuilder, FormControl, FormGroup, NgForm, Validators} from "@angular/forms";
import {BlogServiceService} from "../../services/blog-service.service";
import {ActivatedRoute} from "@angular/router";
import {Location} from "@angular/common";

@Component({
  selector: 'app-edit-blog-components',
  templateUrl: './edit-blog-components.component.html',
  styleUrl: './edit-blog-components.component.css'
})
export class EditBlogComponentsComponent implements OnInit {
  categoryService = inject(CategoryServiceService)
  positionService = inject(PositionServiceService)
  blogService = inject(BlogServiceService)
  listCategorys: Category[] = [];
  listPositions: Position[] = [];
  myForm!: FormGroup;
  fb = inject(FormBuilder);
  blogForm!: FormGroup;
  selectedFile: File | null = null;


  ngOnInit(): void {

    this.positionService.getPositions().subscribe((res) => {
      this.listPositions = res;
      this.myForm.setControl('position', this.fb.array(res.map(() => new FormControl(false))));
    })

    this.categoryService.getCategories().subscribe((res) => {
      this.listCategorys = res;
      console.log(this.listCategorys)
    })

    this.blogForm = this.fb.group({
      title: ['', Validators.required],
      des: ['', Validators.required],
      detail: ['', Validators.required],
      category: ['', Validators.required],
      position: this.fb.array([]), // Sử dụng FormArray cho position
      public: [false, Validators.required],
      data_pubblic: ['', Validators.required],
      thumbs: ['']
    });

  }

  onFileSelected(event: any): void {
    this.selectedFile = event.target.files[0] ?? null;
  }

  onSubmit(): void {
    if (this.blogForm.valid) {
      const formValue = this.blogForm.value;

      // Chuyển đổi position thành mảng các ID
      const selectedPositionIds = formValue.position
        .map((id: number | null, i: number) => id ? this.listPositions[i].id : null)
        .filter((id: number | null) => id !== null);

      // Tạo object blog mới
      const newBlog: Blog = {
        ...formValue,
        position: selectedPositionIds,
        category: Number(formValue.category), // Giả sử category đã là ID
        thumbs: '' // Xử lý upload hình ảnh và cập nhật thumbs ở đây
      };

      // Xử lý upload hình ảnh (nếu có) và thêm blog
      this.addBlog(newBlog);
    }
  }

  addBlog(blog: Blog): void {
    this.blogService.addNewBlog(blog).subscribe(() => {
      alert('Thêm blog thành công!');
      this.blogForm.reset();
    });
  }

  goBack(): void {
    this.location.back();
  }

  constructor(private route: ActivatedRoute, private location: Location) {
    this.myForm = this.fb.group({
      title: ['', Validators.required],
      des: ['', Validators.required],
      detail: ['', Validators.required],
      thumbs: [''],
      category: ['', Validators.required],
      position: ['', Validators.required],
      public: [false, Validators.required],
      data_pubblic: ['', Validators.required]
    });
    this.myForm.get("position")?.valueChanges.subscribe((value) => {
      console.log(value)
    });
  }


}
