import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditBlogComponentsComponent } from './edit-blog-components.component';

describe('EditBlogComponentsComponent', () => {
  let component: EditBlogComponentsComponent;
  let fixture: ComponentFixture<EditBlogComponentsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EditBlogComponentsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditBlogComponentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
