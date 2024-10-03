import {Component, inject, Input, OnInit, TemplateRef} from '@angular/core';
import {CategoryServiceService} from "../../services/category-service.service";
import {PositionServiceService} from "../../services/position-service.service";
import {Blog} from "../../model/blog";
import {Category} from "../../model/category";
import {Position} from "../../model/position";
import {FormArray, FormBuilder, FormControl, FormGroup, NgForm, Validators} from "@angular/forms";
import {BlogServiceService} from "../../services/blog-service.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Location} from "@angular/common";
import {NzModalService} from "ng-zorro-antd/modal";
import {NzNotificationService} from "ng-zorro-antd/notification";

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
  listBlogs: Blog[] = [];
  fb = inject(FormBuilder);
  blogForm!: FormGroup;
  isEdit = false;
  @Input() id: number = 0;
  blogObj: Blog | undefined;
  notificationTitle: String = '';

  constructor(private route: ActivatedRoute, private location: Location, private router: Router, private modal: NzModalService, private notification: NzNotificationService) {
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
    })
    this.positionService.getPositions().subscribe((res) => {
      this.listPositions = res;
      this.blogForm.patchValue({
        ...this.blogObj,
        position: this.listPositions.map(i => ({...i, id: i.id, label: i.name}))
      });
      this.getBlogById(this.id);
    });

    this.getAllBlog();
  }

  onFileSelected(event: any): void {
    if (event.file.status === 'done') {
      const selectedFile = event.file.originFileObj;
      const reader = new FileReader();
      reader.readAsDataURL(selectedFile);
      reader.onload = () => {
        const base64Image = reader.result as string;
        this.blogForm.patchValue({thumbs: base64Image});
      };
    }
  }

  onSubmit(template: TemplateRef<{}>): void {
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
        this.showEditConfirm(this.id, editBlog, template);
      } else {
        const formValue = this.blogForm.value;
        const selectedIds = this.getSelectedIds();
        const id = this.generateRandomNumber(8);
        const idString = id.toString();
        const newBlog: Blog = {
          ...formValue,
          id: idString,
          position: selectedIds,
          category: Number(formValue.category),
          thumbs: ''
        };
        this.showAddConfirm(newBlog, template)
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

  addBlog(blog: Blog, template: TemplateRef<{}>): void {
    this.blogService.addNewBlog(blog).subscribe(() => {
      this.notificationTitle = 'Thêm bài báo thành công!';
      this.blogForm.reset();
      this.goBack(template);
    });
  }

  updateBlog(id: number, blog: Blog, template: TemplateRef<{}>): void {
    this.blogService.updateBlog(id, blog).subscribe(() => {
      this.notificationTitle = 'Thay đổi bài báo thành công!';
      this.blogForm.reset();
      this.goBack(template);
    });
  }

  getBlogById(id: number): void {
    this.blogService.getBlogById(id).subscribe((res) => {
      this.isEdit = true;
      this.blogForm.patchValue({
        ...res,
        category: res.category.toString(),
        position: this.listPositions.map(i => ({
          ...i,
          label: i.name,
          checked: res.position.includes(+i.id) ? true : false
        }))
      });
    })
  }

  getAllBlog(): void {
    this.blogService.getBlogs().subscribe((res) => {
      this.listBlogs = res;
    })
  }

  goBack(template: TemplateRef<{}>): void {
    this.location.back();
    window.location.reload();
    this.createBasicNotification(template);
  }

  showAddConfirm(blog: Blog, template: TemplateRef<{}>): void {
    this.modal.confirm({
      nzTitle: '<b style="color: red;">Bạn chắc chắn muốn thêm mới blog này?</b>',
      nzOkText: 'Có',
      nzOkType: 'primary',
      nzOnOk: () => this.addBlog(blog, template),
      nzCancelText: 'Không',
      nzOnCancel: () => console.log('Cancel')
    });
  }


  showEditConfirm(id: number, blog: Blog, template: TemplateRef<{}>): void {
    this.modal.confirm({
      nzTitle: '<b style="color: red;">Bạn chắc chắn muốn cập nhật blog này?</b>',
      nzOkText: 'Có',
      nzOkType: 'primary',
      nzOnOk: () => this.updateBlog(id, blog, template),
      nzCancelText: 'Không',
      nzOnCancel: () => console.log('Cancel')
    });
  }

//random id
  generateRandomNumber(length: number = 8): string {
    let result = '';
    const characters = '0123456789';
    const charactersLength = characters.length;
    for (let i = 0; i < length; i++) {
      result += characters.charAt(Math.floor(Math.random() * charactersLength));
    }
    return result;
  }

  createBasicNotification(template: TemplateRef<{}>): void {
    this.notification.template(template);
  }

}
