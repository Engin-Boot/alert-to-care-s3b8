import { Component, Inject,OnInit } from '@angular/core';
import {ClientService} from '../service/app.service';
import { Router } from '@angular/router';

@Component({
  selector: 'setup-comp',
  templateUrl: './setup-component.component.html',
  styleUrls: ['./setup-component.component.css']
})
export class SetupComponent implements OnInit {

  clientServiceRef:ClientService;
  constructor(@Inject("clientService") clientServiceRef:ClientService) {
    this.clientServiceRef=clientServiceRef;
   }
   router:Router;
  // constructor(){}
  
   client_type:string;
   client_layout:string;
   no_of_beds:number;
   errorMessage:string;

  ngOnInit(): void {
  }

  onSetup(){
    this.errorMessage=`${this.client_type} ${this.client_layout} ${this.no_of_beds}`;
    let clientDTO={client_type:this.client_type,layout:this.client_layout,no_of_beds:this.no_of_beds};
    let observableStream=this.clientServiceRef.setup(clientDTO);
    //observe
    observableStream.subscribe(
      (data:any)=>{
        console.log(data.message);
      },
      (error)=>{
        console.log(error);
        alert("ICU setup failed! Please try again.");
      },
      ()=>{
        console.log("Request Completed");
        this.generateAlert();
      });
      
  }

  onReset(){

    this.client_type="";
    this.client_layout="";
    this.no_of_beds=0;
    this.errorMessage="";
  }
  generateAlert(){
    alert("ICU setup completed");
  }
}
