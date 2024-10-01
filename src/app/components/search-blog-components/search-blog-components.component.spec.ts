import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchBlogComponentsComponent } from './search-blog-components.component';

describe('SearchBlogComponentsComponent', () => {
  let component: SearchBlogComponentsComponent;
  let fixture: ComponentFixture<SearchBlogComponentsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SearchBlogComponentsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SearchBlogComponentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
