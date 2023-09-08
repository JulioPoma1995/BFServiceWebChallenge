package steps;

import io.cucumber.java.en.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import io.restassured.module.jsv.JsonSchemaValidator;

import java.io.File;

import static io.restassured.RestAssured.given;


public class APISteps {

    private static RequestSpecification request;
    private Response response;
    private ValidatableResponse json;

@Given("^Declaro la base de URL (.*)$")
    public void DeclrarURLBase(String URL){

        request = given()
                .baseUri(URL)
                .contentType(ContentType.JSON);

}

@When("^Envio un Post al (.*) endpoint para crear nuevo registro con los siguientes valores:(\\d+), (\\d+), (.*), (.*), (.*), (\\d+),(.*), (.*)$")
    public void RegistrarMascota(String endpoint,int idpet, int idcate, String namecate, String name, String photourl, int idtag, String nametag, String status){

    String requestBody = "{\n" +
            "       \"id\" : " + idpet + ",\n" +
            "       \"category\" : {\n" +
            "            \"id\" : " + idcate + ",\n" +
            "            \"name\" : \"" + namecate + "\"\n" +
            "       },\n" +
            "       \"name\" : \"" + name + "\",\n" +
            "       \"photoUrls\" : [\n" +
            "       \"" + photourl + "\"\n" +
            "       ],\n" +
            "       \"tags\" : [\n" +
            "       {\n" +
            "       \"id\" : " + idtag + ",\n" +
            "       \"name\" : \"" + nametag + "\"\n" +
            "       }\n"+
            "       ],\n" +
            "       \"status\" : \"" + status + "\"\n" +
            "       }";
    response= request.when().header("Content-Type", "application/json")
            .header("Accept","application/json")
            .body(requestBody).post(endpoint).prettyPeek();

}

@Then("^Valido el estatus respuesta (\\d+)$")
    public void ValidarStatuscode(int codigoesperado){

    json = response.then().statusCode(codigoesperado);

}

@And("^Valido la respuesta en el esquema con los siguientes valores: (\\d+), (\\d+), (.*), (.*), (.*), (\\d+),(.*), (.*)$")
    public void ValidarRespuesta(int idpet, int idcate, String namecate, String name, String photourl, int idtag, String nametag, String status){
    Assert.assertEquals(idpet,response.jsonPath().getInt("id"));
    Assert.assertEquals(idcate,response.jsonPath().getInt("category.id"));
    Assert.assertEquals(namecate,response.jsonPath().getString("category.name"));
    Assert.assertEquals(name,response.jsonPath().getString("name"));
    Assert.assertEquals("["+photourl+"]", response.jsonPath().getString("photoUrls"));
    Assert.assertEquals("["+idtag+"]", response.jsonPath().getString("tags.id"));
    Assert.assertEquals("["+nametag+"]", response.jsonPath().getString("tags.name"));
    Assert.assertEquals(status, response.jsonPath().getString("status"));
}

@When("^Envio un GET al (.*) endpoint con el id de mascota$")
    public void ConsultarMascota(String endpoint){

   response = request.when().header("Accept","application/json")
            .get(endpoint);

}

@When("^Envio un PUT al (.*) endpoint con los valores a actualizar: (\\d+), (\\d+), (.*), (.*), (.*), (\\d+),(.*), (.*)$")
    public void ActualizarMascota(String endpoint, int idpet, int idcate, String namecate, String name, String photourl, int idtag, String nametag, String status ){

    String requestBody = "{\n" +
            "       \"id\" : " + idpet + ",\n" +
            "       \"category\" : {\n" +
            "            \"id\" : " + idcate + ",\n" +
            "            \"name\" : \"" + namecate + "\"\n" +
            "       },\n" +
            "       \"name\" : \"" + name + "\",\n" +
            "       \"photoUrls\" : [\n" +
            "       \"" + photourl + "\"\n" +
            "       ],\n" +
            "       \"tags\" : [\n" +
            "       {\n" +
            "       \"id\" : " + idtag + ",\n" +
            "       \"name\" : \"" + nametag + "\"\n" +
            "       }\n"+
            "       ],\n" +
            "       \"status\" : \"" + status + "\"\n" +
            "       }";
    response= request.when().header("Content-Type", "application/json")
            .header("Accept","application/json")
            .body(requestBody).put(endpoint).prettyPeek();

}

@And("^Valido la el update realizado: (\\d+), (\\d+), (.*), (.*), (.*), (\\d+),(.*), (.*)$")
    public void ValidarUpdate(int idpet, int idcate, String namecate, String name, String photourl, int idtag, String nametag, String status){
    Assert.assertEquals(idpet,response.jsonPath().getInt("id"));
    Assert.assertEquals(idcate,response.jsonPath().getInt("category.id"));
    Assert.assertEquals(namecate,response.jsonPath().getString("category.name"));
    Assert.assertEquals(name,response.jsonPath().getString("name"));
    Assert.assertEquals("["+photourl+"]", response.jsonPath().getString("photoUrls"));
    Assert.assertEquals("["+idtag+"]", response.jsonPath().getString("tags.id"));
    Assert.assertEquals("["+nametag+"]", response.jsonPath().getString("tags.name"));
    Assert.assertEquals(status, response.jsonPath().getString("status"));

}


}
