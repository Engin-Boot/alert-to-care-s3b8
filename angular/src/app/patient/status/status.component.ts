import { Component, Inject,OnInit } from '@angular/core';
import {ClientService} from '../../service/app.service';
import { Client_model } from "../../model/Client-model";
import {Bed_status} from "../../model/bed-status";

@Component({
  selector: 'app-status',
  templateUrl: './status.component.html',
  styleUrls: ['./status.component.css']
})
export class StatusComponent implements OnInit {
  clientServiceRef:ClientService;
  constructor(@Inject("clientService") clientServiceRef:ClientService) {
    this.clientServiceRef=clientServiceRef;
   }
  //constructor() { }

  client_list:Client_model[];
  //bed_status_list:Bed_status[];
  bed_status_list:Map<string,string>=new Map<string,string>();
  bed_status:Bed_status[]=[];

  client_id:string;
  ngOnInit(): void {
    this.getClient();
  }

  BedStatus(){
    let observableStream=this.clientServiceRef.getAllbedStatus(this.client_id);

    observableStream.subscribe(
      (data:any)=>{ 
        this.bed_status_list=data;
        console.log(this.bed_status_list);
        for(let bid of Object.keys(this.bed_status_list)){
          let stat=this.bed_status_list[bid];
          let bstatus:Bed_status={"bed_id":bid,"bedStatus":stat};
          //console.log(bstatus);
          this.bed_status.push(bstatus);
        }
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
}
