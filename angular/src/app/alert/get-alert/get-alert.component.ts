import { Vitals_model } from './../../model/Vitals_model';
import { Component, Inject,OnInit } from '@angular/core';
import {ClientService} from '../../service/app.service';
import { AlertModel } from 'src/app/model/alert-model';
import {Patient} from '../../model/patient-model';
import { Client_model } from "../../model/Client-model";

@Component({
  selector: 'getAlert-comp',
  templateUrl: './get-alert.component.html',
  styleUrls: ['./get-alert.component.css']
})
export class GetAlertComponent implements OnInit {


  clientServiceRef:ClientService;
  constructor(@Inject("clientService") clientServiceRef:ClientService) {
    this.clientServiceRef=clientServiceRef;
   }

   client_id:string;
   client_list:Client_model[];
   patient_id:string;
   alerts:AlertModel[]=[];
   admitted_patient_list:Patient[];
   isShow=true;

  ngOnInit(): void {
    this.getClient();
  }

  onSetup(){
    let observableStream=this.clientServiceRef.getAlert(this.patient_id);
    //observe
    observableStream.subscribe(
      (data:any)=>{
        this.alerts=data;
        console.log(this.alerts);
      },
      (error)=>{
        console.log(error);
      },
      ()=>{
        console.log("Request Completed");
      });
      this.displayTableDiv();
  }

  onReset(){

    this.patient_id="";    
  }

  getClient(){
    let observableStream=this.clientServiceRef.getClient();
    observableStream.subscribe(
      (data:any)=>
      {
        this.client_list=data;
      },
      (error)=>{
        console.log(error);
      },
      ()=>{
        console.log("Request Completed");
      }
    );
  }

  getAdmittedPatients(){
    let observableStream=this.clientServiceRef.getAdmittedPatients(this.client_id);
    observableStream.subscribe(
      (data:any)=>
      {
        this.admitted_patient_list=data;
      },
      (error)=>{
        console.log(error);
      },
      ()=>{
        console.log("Request Completed");
      }
    );    
  }

  onClientChange(){
    this.getAdmittedPatients();
  }

  displayTableDiv(){
    this.isShow=!this.isShow;
  }
}
