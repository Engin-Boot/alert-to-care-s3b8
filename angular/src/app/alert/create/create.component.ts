import { Vitals_model } from './../../model/Vitals_model';
import { Component, Inject,OnInit } from '@angular/core';
import {ClientService} from '../../service/app.service';
import { Client_model } from "../../model/Client-model";


@Component({
  selector: 'create-comp',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.css']
})
export class CreateComponent implements OnInit {

  //alertList:Vitals_model[];

  clientServiceRef:ClientService;
  constructor(@Inject("clientService") clientServiceRef:ClientService) {
    this.clientServiceRef=clientServiceRef;
   }


   client_id:string;
   device_id:string;
   spo2:string;
   bpm:string;
   resprate:string;
   client_list:Client_model[];
   free_device_list:string[];
   //errorMessage:string;



  ngOnInit(): void {
    this.getClient();
  }

  onSetup(){
    //this.errorMessage=`${this.measurement}:${this.spo2} ${this.bpm} ${this.resprate}`;
    let VitalsDTO={measurement:{spo2:this.spo2,bpm:this.bpm,resprate:this.resprate}};
    let observableStream=this.clientServiceRef.createAlert(this.device_id,VitalsDTO);
    //observe
    observableStream.subscribe(
      (data:any)=>{
        console.log(data.message);
      },
      (error)=>{
        console.log(error);
        alert("Alert Creation Failed!");
      },
      ()=>{
        console.log("Request Completed");
        alert("Alert Created Successfully!");
      });

  }
  onReset(){

    this.device_id="";
    this.spo2="";
    this.bpm="";
    this.resprate="";
    
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

  onClientChange(){
     this.getFreeDevices();
  }

  getFreeDevices(){
    let observableStream=this.clientServiceRef.getFreeDevice(this.client_id);
    observableStream.subscribe(
      (data:any)=>
      {
        console.log(data);
        this.free_device_list=data;
      },
      (error)=>{
        console.log(error);
      },
      ()=>{
        console.log("Request Completed");
      }
    );
  }

}
