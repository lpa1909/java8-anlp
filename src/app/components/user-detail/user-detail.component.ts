import {Component, OnInit} from '@angular/core';
import {states} from "../../data-model";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrl: './user-detail.component.css'
})
export class UserDetailComponent implements OnInit{
  states = states;
  userFormGroup: FormGroup = new FormGroup({});

  constructor(private formBuilder: FormBuilder) {
    this.createForm();
  }
  createForm() {
    this.userFormGroup = this.formBuilder.group({
      name: ['Hoang', [Validators.required, Validators.minLength(4)]],
      email: ['', [Validators.required]],
      addresses: this.formBuilder.group({ //the child FormGroup
        street: ['', [Validators.required]],
        city: '',
        state: this.states[0],
      }),
    });
  }
  ngOnInit() {

  }
}
