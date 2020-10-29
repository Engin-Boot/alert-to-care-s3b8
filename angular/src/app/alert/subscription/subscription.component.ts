import { Patient } from './../../model/patient-model';
import { Component, Inject,OnInit } from '@angular/core';
import {ClientService} from '../../service/app.service';
import { AlertModel } from 'src/app/model/alert-model';

import { Client_model } from "../../model/Client-model";

@Component({
  selector: 'subscription-comp',
  templateUrl: './subscription.component.html',
  styleUrls: ['./subscription.component.css']
})
export class SubscriptionComponent implements OnInit {

  clientServiceRef:ClientService;
  constructor(@Inject("clientService") clientServiceRef:ClientService) {
    this.clientServiceRef=clientServiceRef;
   }

   
   alarmstatus:boolean=true;
   errorMessage:string;
   client_id:string;
   patient_id:string;
   client_list:Client_model[];
   admitted_patient_list:Patient[];
   
  ngOnInit(): void {
    this.getClient();
    
  }
  
  onSetUp(){
    this.errorMessage=`${this.client_id} ${this.patient_id} ${this.alarmstatus}`;
    let AlarmDTO={isAlarmActive:this.alarmstatus};
    let observableStream=this.clientServiceRef.changeAlertStatus(this.client_id,this.patient_id,AlarmDTO);

    observableStream.subscribe(
      (data:any)=>{
        console.log(data.message);
      },
      (error)=>{
        console.log(error);
        alert("Alert subscription change procedure failed");
      },
      ()=>{
        console.log("Alert Subscription change successful");
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
