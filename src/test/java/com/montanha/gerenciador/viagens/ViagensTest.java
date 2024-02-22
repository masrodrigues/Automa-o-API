package com.montanha.gerenciador.viagens;

import ch.qos.logback.core.net.SyslogOutputStream;
import io.restassured.http.ContentType;
import org.junit.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
public class ViagensTest {
    @Test
    public void testDadoUmAdministradorQuandoCadastroViagensEntaoObtenhoStatosCode201(){
        //configurar o caminho comum de acesso a minha API Rest
        baseURI = "http://localhost";
        port = 8089;
        basePath = "/api";

        //Login na API com administrador
        String token = given()
                .body("{\n" +
                        "\"email\":\"admin@email.com\", \n" +
                        " \"senha\":\"654321\"\n" +

                        "}")
                .contentType(ContentType.JSON)
                .when()
                    .post("/v1/auth")
                .then()
                .extract()
                .path("data.token");

        System.out.println(token);

        //Cadastrar a viagem
        given()
                .header("Authorization", token)
                .body("{\n" +
                        "  \"acompanhante\": \"string\",\n" +
                        "  \"dataPartida\": \"2021-02-07\",\n" +
                        "  \"dataRetorno\": \"2021-03-07\",\n" +
                        "  \"localDeDestino\": \"Salvador\",\n" +
                        "  \"regiao\": \"Nordeste\"\n" +
                        "}")
                .contentType(ContentType.JSON)
        .when()
                .post("/v1/viagens")
        .then()
                .log().all()
                .assertThat()
                .statusCode(201);

    }
}
