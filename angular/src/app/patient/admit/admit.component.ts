import { Component, Inject,OnInit } from '@angular/core';
import {ClientService} from '../../service/app.service';
import { Client_model } from "../../model/Client-model";
@Component({
  selector: 'admit-comp',
  templateUrl: './admit.component.html',
  styleUrls: ['./admit.component.css']
})
export class AdmitComponent implements OnInit {

  client_list:Client_model[];
  empty_bed_list:string[];

  clientServiceRef:ClientService;
  constructor(@Inject("clientService") clientServiceRef:ClientService) {
    this.clientServiceRef=clientServiceRef;
   }
  //constructor() { }

  client_id:string;
  patient_name:string;
  dob:string;
  bed_id:string;
  alarmstatus:boolean=true;
  errorMessage:string;

  ngOnInit(): void {
    this.getClient();
  }

  onSetup(){
    this.errorMessage=`${this.client_id} ${this.patient_name} ${this.dob} ${this.bed_id} ${this.alarmstatus}`;
    let patientDTO={name:this.patient_name,dob:this.dob,bed_id:this.bed_id,isAlarmActive:this.alarmstatus};
    let observableStream=this.clientServiceRef.admit(patientDTO,this.client_id);
    //observe
    observableStream.subscribe(
      (data:any)=>{
        console.log(data.message);
      },
      (error)=>{
        console.log(error);
        alert("Patient Admission Failed! Please Try again.");
      },
      ()=>{
        console.log("Request Completed");
        alert("Patient Admitted Successfully!");
      });
  }

  onReset(){

    this.client_id="";
    this.patient_name="";
    this.dob="";
    this.bed_id="";
    this.errorMessage="";
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
  getEmptyBeds(){
    let observableStream=this.clientServiceRef.getEmptyBeds(this.client_id);
    observableStream.subscribe(
      (data:any)=>
      {
        this.empty_bed_list=data;
        // console.log(data);
        // console.log(this.empty_bed_list);
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
    this.getEmptyBeds();
  }
}
