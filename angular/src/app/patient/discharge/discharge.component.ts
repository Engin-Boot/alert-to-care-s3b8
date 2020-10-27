import { Component, Inject,OnInit } from '@angular/core';
import {ClientService} from '../../service/app.service';
import { Client_model } from "../../model/Client-model";
import {Patient} from "../../model/patient-model"

@Component({
  selector: 'app-discharge',
  templateUrl: './discharge.component.html',
  styleUrls: ['./discharge.component.css']
})
export class DischargeComponent implements OnInit {

  client_id:string;
  patient_id:string;
  client_list:Client_model[];
  admitted_patient_list:Patient[];

  clientServiceRef:ClientService;
  constructor(@Inject("clientService") clientServiceRef:ClientService) {
    this.clientServiceRef=clientServiceRef;
   }

  ngOnInit(): void {
    this.getClient();
  }

  DischargePatient(){
    let observableStream=this.clientServiceRef.dischargePatient(this.client_id,this.patient_id);
    observableStream.subscribe(
      (data:any)=>{
        console.log(data.message);
      },
      (error)=>{
        console.log(error);
      },
      ()=>{
        console.log("Request Completed");
      });
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
}
