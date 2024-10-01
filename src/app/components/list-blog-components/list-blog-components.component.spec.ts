import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListBlogComponentsComponent } from './list-blog-components.component';

describe('ListBlogComponentsComponent', () => {
  let component: ListBlogComponentsComponent;
  let fixture: ComponentFixture<ListBlogComponentsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ListBlogComponentsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListBlogComponentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
