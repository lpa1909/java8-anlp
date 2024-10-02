import {Component, inject, Input, OnInit} from '@angular/core';
import {CategoryServiceService} from "../../services/category-service.service";
import {PositionServiceService} from "../../services/position-service.service";
import {Blog} from "../../model/blog";
import {Category} from "../../model/category";
import {Position} from "../../model/position";
import {FormArray, FormBuilder, FormControl, FormGroup, NgForm, Validators} from "@angular/forms";
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
  fb = inject(FormBuilder);
  blogForm!: FormGroup;
  selectedFile: File | null = null;
  isEdit = false;
  @Input() id: number = 0;
  blogObj: Blog | undefined;

  constructor(private route: ActivatedRoute, private location: Location) {
    this.blogForm = this.fb.group({
      title: ['', Validators.required],
      des: ['', Validators.required],
      detail: ['', Validators.required],
      category: ['', Validators.required],
      position: [[]], // Sử dụng FormArray cho position
      public: [false, Validators.required],
      data_pubblic: ['', Validators.required],
      thumbs: [''],
    });
  }

  ngOnInit(): void {

    this.categoryService.getCategories().subscribe((res) => {
      this.listCategorys = res;
      console.log(this.listCategorys)
    })



    this.route.paramMap.subscribe(params => {
      this.id = Number(params.get('id'));
    });

    this.positionService.getPositions().subscribe((res) => {
      this.listPositions = res;
      this.blogForm.patchValue({...this.blogObj, position: this.listPositions.map(i => ({...i, id: i.id, label: i.name}))});
      this.getBlogById(this.id);
    });
  }

  onFileSelected(event: any): void {
    this.selectedFile = event.target.files[0] ?? null;
  }

  onSubmit(): void {
    if (this.blogForm.valid) {
      if (this.isEdit) {
        const formValue = this.blogForm.value;
        const selectedIds = this.getSelectedIds();
        const editBlog: Blog = {
          ...formValue,
          position: selectedIds,
          category: Number(formValue.category),
          thumbs: ''
        };
        this.updateBlog(this.id, editBlog);
      } else {
        const formValue = this.blogForm.value;
        const selectedIds = this.getSelectedIds();
        const newBlog: Blog = {
          ...formValue,
          position: selectedIds,
          category: Number(formValue.category),
          thumbs: ''
        };
        this.addBlog(newBlog);
      }
    }
  }

  getSelectedIds(): number[] {
    const selectedIds: number[] = [];
    const form = this.blogForm.value;

    form.position.forEach((checkbox: any) => {
      if (checkbox.checked) {
        selectedIds.push(Number(checkbox.id));
      }
    });

    return selectedIds;
  }

  addBlog(blog: Blog): void {
    const confirmBlog = confirm('Bạn chắc chắn muốn thêm bài báo ?');
    if (confirmBlog) {
      this.blogService.addNewBlog(blog).subscribe(() => {
        alert('Thêm blog thành công!');
        this.blogForm.reset();
      });
    }
  }

  updateBlog(id: number, blog: Blog): void {
    const confirmEdit = confirm(' có chắc chắn muốn thay đổi bài báo ?');
    if (confirmEdit) {
      this.blogService.updateBlog(id, blog).subscribe(() => {
        alert('Thay đổi bài báo thành công !');
        this.blogForm.reset();
      });
    }
  }

  getBlogById(id: number): void {
    this.blogService.getBlogById(id).subscribe((res) => {
      this.isEdit = true;
      this.blogForm.patchValue({
        ...res,
        position: this.listPositions.map(i => ({
          ...i,
          label: i.name,
          checked: res.position.includes(+i.id) ? true : false
        }))
      });
    })
  }

  goBack(): void {
    this.location.back();
  }

}
