import { HttpClient } from '@angular/common/http';
import { Inject, Injectable } from '@angular/core';
import { observable } from 'rxjs';

@Injectable()
export class ClientService{

  httpClient:HttpClient;
  baseUrl:string;
  constructor(httpClient:HttpClient,@Inject('apiBaseAddress')baseUrl:string){
    this.httpClient=httpClient;
    this.baseUrl=baseUrl;
  }

  setup(clientDTO){
    let observableStream=this.httpClient.post(`${this.baseUrl}/pms/client/config`,clientDTO);
    return observableStream;
  }

  admit(patientDTO,client_id){
    let observableStream=this.httpClient.post(`${this.baseUrl}/pms/client/${client_id}/patient`,patientDTO);
    return observableStream;
  }

  getClient(){
    let observableStream=this.httpClient.get(`${this.baseUrl}/pms/client/all`);
    return observableStream;
  }

  getAllbedStatus(client_id){
    let observableStream=this.httpClient.get(`${this.baseUrl}/pms/client/${client_id}/bed`);
    return observableStream;
  }

  dischargePatient(client_id, patient_id){
    let observableStream=this.httpClient.put(`${this.baseUrl}/pms/client/${client_id}/patient/${patient_id}/discharge`,client_id,patient_id);
    return observableStream;

  }

  createAlert(device_id, VitalsDTO){
    let observableStream=this.httpClient.post(`${this.baseUrl}/pms/device/${device_id}/alert`,VitalsDTO);
    return observableStream;
  }
  getAlert(patient_id){
    let observableStream=this.httpClient.get(`${this.baseUrl}/pms/patient/${patient_id}/alert`);
    return observableStream;
  }

  getEmptyBeds(client_id){
    let observableStream=this.httpClient.get(`${this.baseUrl}/pms/client/${client_id}/emptybeds`);
    return observableStream;
  }

  getAdmittedPatients(client_id){
    let observableStream=this.httpClient.get(`${this.baseUrl}/pms/client/${client_id}/admittedpatients`);
    return observableStream;
  }

  getFreeDevice(client_id){
    let observableStream=this.httpClient.get(`${this.baseUrl}/pms/client/${client_id}/device`);
    return observableStream;
  }

  changeAlertStatus(client_id, patient_id, AlarmDTO){
    let observableStream=this.httpClient.put(`${this.baseUrl}/pms/client/${client_id}/patient/${patient_id}/alarmStatus`,AlarmDTO,{responseType:'text'});
    return observableStream;
  }

}