Feature: Validación de API sobre servicios CRUD para página de mascotas

  Scenario Outline: Realizar registro nuevo de mascota
    Given Declaro la base de URL https://petstore.swagger.io
    When Envio un Post al /v2/pet endpoint para crear nuevo registro con los siguientes valores:<idpet>, <idcate>, <namecate>, <name>, <photourl>, <idtag>,<nametag>, <status>
    Then Valido el estatus respuesta 200
    And Valido la respuesta en el esquema con los siguientes valores: <idpet>, <idcate>, <namecate>, <name>, <photourl>, <idtag>,<nametag>, <status>
  Examples:
  |idpet|idcate|  namecate|name | photourl|idtag|nametag|status   |
  |12345|12345 |ningunp   |Bozzy|nohayfoto|1    |  nohay|available|


  Scenario: Realizar consulta de una mascota
    Given Declaro la base de URL https://petstore.swagger.io
    When Envio un GET al /v2/pet/2 endpoint con el id de mascota
    And Valido el estatus respuesta 200

  @API
  Scenario Outline: Realizar update de mascota
    Given Declaro la base de URL https://petstore.swagger.io
    When Envio un PUT al /v2/pet endpoint con los valores a actualizar: <idpet>, <idcate>, <namecate>, <name>, <photourl>, <idtag>,<nametag>, <status>
    Then Valido el estatus respuesta 200
    And Valido la el update realizado: <idpet>, <idcate>, <namecate>, <name>, <photourl>, <idtag>,<nametag>, <status>
    Examples:
      |idpet|idcate|  namecate|name | photourl|idtag|nametag|status   |
      |2    |123   |Perrito   |Bozzy|nohayfoto|1    |  engre|available|

