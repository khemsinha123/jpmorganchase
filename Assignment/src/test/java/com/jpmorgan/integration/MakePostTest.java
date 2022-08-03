package com.jpmorgan.integration;


import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;
import org.testng.Assert;
import io.restassured.RestAssured;
import org.json.JSONObject;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import sun.font.TrueTypeFont;

import static io.restassured.RestAssured.given;


public class MakePostTest  {


    @BeforeClass
    public void setup() {
        // Setting BaseURI once
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
        // Setting BasePath once
        RestAssured.basePath ="/posts";
    }


    @Test
    public void testGetPostDetails() throws Exception {
        RequestSpecification request = RestAssured.given();
        request.get().then().assertThat().statusCode(200);

    }


    //Test to add a new post
    @Test
    public void testAddNewPost() throws Exception {

        int userid=99;
        String title = "testAddNewPost title" ;
        String comment = "testAddNewPost post";

        Response res = createPost(userid , title , comment);
        if (res.statusCode()==201)
            Assert.assertTrue(res.asString().contains(comment));
        else
            Assert.fail("Creation was failed");
    }


    //Test to delete a post using unique id param
    @Test
    public void testDeletePost() throws Exception {

        int uid = 99;
        String title = "Test Title for deletion of post testDeletePost " ;
        String comment = "Test Comment for deletion post testDeletePost";

        Response res = createPost(uid , title , comment);
        if (res.statusCode()==201) {
            JsonPath jsnPath = res.jsonPath();
            String s = jsnPath.get("id").toString();
            Assert.assertEquals(given().when().queryParam("id", s).delete().getStatusCode(), 200);
        }

    }

 //   @Test
 /*   public void delPost() throws Exception {

        JSONObject obj = new JSONObject();
        obj.put("userId" , 10);
        obj.put("title" , "Test Title") ;
        obj.put("body" , "This is test body") ;

        RequestSpecification request = RestAssured.given();
        given().header("Content-Type", "application/json").body(obj.toString()).when().post().then().assertThat().statusCode(201);
        Response res = given().header("Content-Type", "application/json").body(obj.toString()).when().post();
        System.out.println(res.asString());
        System.out.println(res);
    }*/

    private Response createPost(int userid , String title , String body) throws Exception {

        JSONObject obj = new JSONObject();
        obj.put("userId" , userid);
        obj.put("title" , title) ;
        obj.put("body" , body) ;
        RequestSpecification request = RestAssured.given();
        Response resp = given().header("Content-Type", "application/json").body(obj.toString()).post();
        return resp;
    }

}