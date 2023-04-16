# ecommerse-service

This is an ecommerse service that allow you to create, get, update and remove an ECard

There are 4 endpoints for each action

Create ECard:
  Method: POST
  URL: http://localhost:8080/api/v1/ecommerse
  Response Body:
  {
    "id": 406,
    "products": []
  }
  
Get ECard:
  Method: GET
  URL: http://localhost:8080/api/v1/ecommerse/{eCardId}
  Response Body:
  {
    "id": 406,
    "products": [{"id": , "amount": , "descriprion":}]
  }
  
Remove ECard:
  Method: DELETE
  URL: http://localhost:8080/api/v1/ecommerse/{eCardId}


Update ECard:
  Method: Get
  URL: http://localhost:8080/api/v1/ecommerse/{eCardId}
  Request Body:
  [{"id": , "amount": , "descriprion": }, {"id": , "amount": , "descriprion": }]
  Response Body:
  {
    "id": 406,
    "products": [{"id": , "amount": , "descriprion": }]
  }
