# REST API's

## I.) Configuration API's

    1. Create Client
      
      Url: http://localhost:8080/pms/client/config
      Http Request Type: POST
      Request body: 
      {
        "client_type": "type1",
        "no_of_beds": "5",
        "layout": "DEFAULT"
      }
      
      
    2. Update Client
    
      Url: http://localhost:8080/pms/client/{client_id}/config
      Http Request Type: PUT
      Request body:
      {
        "client_type": "type2",
        "layout": "L_Layout",
        "no_of_beds": "3"
      }
      
      
## II.) Occupancy API's
     
     1. Create Patient
     
       Url: http://localhost:8080/pms/client/{client_id}/patient
       Http Request Type: POST
       Request body:
       {
        "name": "name1",
        "patientStatus": "ADMITTED",
        "dob": "1998-01-01",
        "bed_id": "dec7066f-b174-4207-ad2a-6a9fd2da5da9",
        "isAlarmActive": "true"
       }
     
     2. Get All Bed Status For client_id
     
       Url: http://localhost:8080/pms/client/{client_id}/bed
       Http Request Type: GET
       Request body: None
     
     3. Discharge Patient
     
       Url: http://localhost:8080/pms/client/{client_id}/patient/{patient_id}/discharge
       Http Request Type: PUT
       Request body: None
     
     
## III.) Monitoring API's

     1. Create Alert
     
       Url: http://localhost:8080/pms/device/{device_id}/alert
       Http Request Type: POST
       Request body: 
       {
         "measurement":
          {
           "spo2": 20,
           "bpm": 10
          }
       }
       
       
     2. Get Alerts By patient_id
     
       Url: http://localhost:8080/pms/patient/{patient_id}/alert
       Http Request Type: GET
       Request body: None
       
       
     3. Change Alert Status for a particular patient
     
       Url: http://localhost:8080/pms/client/{client_id}/patient/{patient_id}/alarmStatus
       Http Request Type: PUT
       Request body: 
       {
        "isAlarmActive": "false"
       }
     
     
     
     
     
     
     
