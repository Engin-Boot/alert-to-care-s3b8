# REST API's

## I.) Confiuration API's

    1. Create Client
      
      Url: http://localhost:8080/pms/client/config
      Request body: 
      {
        "client_type": "type1",
        "no_of_beds": "5",
        "layout": "DEFAULT"
      }
      
      
    2. Update Client
    
      Url: http://localhost:8080/pms/client/{client_id}/config
      Request body:
      {
        "client_type": "type2",
        "layout": "L_Layout",
        "no_of_beds": "3"
      }
