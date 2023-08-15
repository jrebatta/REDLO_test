package tests.servicios.actors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.junit.Assert;
import utils.DataExcel;
import utils.MetodosFeature;

import java.io.IOException;

import static io.restassured.RestAssured.rootPath;
import static org.hamcrest.Matchers.equalTo;

import static io.restassured.RestAssured.given;
import static utils.Base.generateRandomNumber;
import static utils.Base.generateRandomText;

public class Default extends DataExcel {

   private final String baseURI = "https://dev-server.redlo.com.pe/api";

   public RequestSpecification request;
   private Response response;
   public JSONObject jsonObject = new JSONObject();
   public String token;


   public void postRegistro(){
      String generatedEmail = generateRandomText() + "@test" + ".com";
      jsonObject.put("email", generatedEmail);
      jsonObject.put("password",retornarValor("password", MetodosFeature.getScenario()));
      jsonObject.put("passwordConfirm",retornarValor("passwordConfirm", MetodosFeature.getScenario()));
      jsonObject.put("enterpriseName",retornarValor("enterpriseName", MetodosFeature.getScenario()));
      jsonObject.put("enterpriseLocation",retornarValor("enterpriseLocation", MetodosFeature.getScenario()));
      jsonObject.put("enterpriseAddress",retornarValor("enterpriseAddress", MetodosFeature.getScenario()));
      jsonObject.put("name",retornarValor("name", MetodosFeature.getScenario()));
      jsonObject.put("phone",retornarValor("phone", MetodosFeature.getScenario()));
      jsonObject.put("position",retornarValor("position", MetodosFeature.getScenario()));
      jsonObject.put("enterpriseID",String.valueOf(generateRandomNumber()));
      registrarValor("email",generatedEmail);
      RestAssured.baseURI = baseURI;

      request = given().header("Content-Type","application/json").contentType("application/json")
              .accept("application/json").body(jsonObject.toJSONString()).log().all();
    }

    public void validarCodigo(String codigo){
       response = request.when().post(retornarValor("URL", MetodosFeature.getScenario()));
       int responseCode = response.then().log().all().extract().statusCode();
       Assert.assertEquals(codigo,responseCode+"");

    }

    public void validarCreacionUsuario(){
      String body = response.getBody().asString();
      ObjectMapper mapper = new ObjectMapper();
      try {
         JsonNode json = mapper.readTree(body);
         String nameJson = json.get("me").get("enterprise").get("name").asText();
         Assert.assertEquals(nameJson,retornarValor("name",MetodosFeature.getScenario()));
      } catch (IOException e) {
         e.printStackTrace();
      }
    }

    public void validarLoginDeUsuario(){
       jsonObject.put("username",retornarValor("email", MetodosFeature.getScenario()));
       jsonObject.put("password",retornarValor("password", MetodosFeature.getScenario()));

       RestAssured.baseURI = baseURI;

       request = given().header("Content-Type","application/json").contentType("application/json")
               .accept("application/json").body(jsonObject.toJSONString()).log().all();

    }

    public void validarYGuardarToken(){
       token = response.then().extract().body().jsonPath().getString("token");
       registrarValor("TOKEN",token);
    }

    public void getCerrarSesion() throws IOException {
       request = given().header("Content-Type","application/json").
               header("Authorization","Token "+ getCellValue("Hoja1",2,12)).contentType("application/json")
               .accept("application/json").body(jsonObject.toJSONString()).log().all();
    }

    public void validarBodyVacio(){
       String body = response.getBody().asString();
       Assert.assertEquals(body,"");
    }

    public void validarErrorEmailExiste(String mensaje){
       String emailError = response.then().extract().body().jsonPath().getString("email").replace("[","").replace("]","").trim();
       Assert.assertEquals(mensaje,emailError);
    }

    public void validarErrorEnterpriseIDExiste(String mensaje){
       String enterpriseIDError = response.then().extract().body().jsonPath().getString("enterpriseID").replace("[","").replace("]","").trim();
       Assert.assertEquals(mensaje,enterpriseIDError);
    }

    public void postRegistroConErrorEmail() {
        jsonObject.put("email",retornarValor("email", MetodosFeature.getScenario()));
        jsonObject.put("password",retornarValor("password", MetodosFeature.getScenario()));
        jsonObject.put("passwordConfirm",retornarValor("passwordConfirm", MetodosFeature.getScenario()));
        jsonObject.put("enterpriseName",retornarValor("enterpriseName", MetodosFeature.getScenario()));
        jsonObject.put("enterpriseLocation",retornarValor("enterpriseLocation", MetodosFeature.getScenario()));
        jsonObject.put("enterpriseAddress",retornarValor("enterpriseAddress", MetodosFeature.getScenario()));
        jsonObject.put("name",retornarValor("name", MetodosFeature.getScenario()));
        jsonObject.put("phone",retornarValor("phone", MetodosFeature.getScenario()));
        jsonObject.put("position",retornarValor("position", MetodosFeature.getScenario()));
        jsonObject.put("enterpriseID",retornarValor("enterpriseID", MetodosFeature.getScenario()));

        RestAssured.baseURI = baseURI;

        request = given().header("Content-Type","application/json").contentType("application/json")
                .accept("application/json").body(jsonObject.toJSONString()).log().all();
    }

    public void postRegistroConErrorEnterpriseID() {
        jsonObject.put("email",retornarValor("email", MetodosFeature.getScenario()));
        jsonObject.put("password",retornarValor("password", MetodosFeature.getScenario()));
        jsonObject.put("passwordConfirm",retornarValor("passwordConfirm", MetodosFeature.getScenario()));
        jsonObject.put("enterpriseName",retornarValor("enterpriseName", MetodosFeature.getScenario()));
        jsonObject.put("enterpriseLocation",retornarValor("enterpriseLocation", MetodosFeature.getScenario()));
        jsonObject.put("enterpriseAddress",retornarValor("enterpriseAddress", MetodosFeature.getScenario()));
        jsonObject.put("name",retornarValor("name", MetodosFeature.getScenario()));
        jsonObject.put("phone",retornarValor("phone", MetodosFeature.getScenario()));
        jsonObject.put("position",retornarValor("position", MetodosFeature.getScenario()));
        jsonObject.put("enterpriseID",retornarValor("enterpriseID", MetodosFeature.getScenario()));

        RestAssured.baseURI = baseURI;

        request = given().header("Content-Type","application/json").contentType("application/json")
                .accept("application/json").body(jsonObject.toJSONString()).log().all();
    }
}
