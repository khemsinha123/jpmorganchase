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

public class PostCommentTest {

    @BeforeClass
    public void setup() {
        // Setting BaseURI once
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
        // Setting BasePath once
        RestAssured.basePath ="/comments";
    }

    @Test
    public void testComments() throws Exception {
        RequestSpecification request = RestAssured.given();
        request.get().then().assertThat().statusCode(200);

    }

    @Test
    public void testAddNewComment() throws Exception {

        int postid=99;
        String name = "testAddNewComment name" ;
        String email = "testAddNewPost@gmail.com";
        String body=" This is a testAddNewComment body";

        Response res = createComment(postid , name , email , body);
        if (res.statusCode()==201)
            Assert.assertTrue(res.asString().contains(name));
        else
            Assert.fail("Creation was failed");
    }

    @Test
    public void testUpdateComment() throws Exception {

        int postid=99;
        String name = "testAddNewComment name" ;
        String email = "updatedtestAddNewPost@gmail.com";
        String body=" This is a testAddNewComment body";
        String updatedemail ="testAddNewPost@gmail.com";
        String updatedbody ="Updated testAddNewComment body";

        Response res = createComment(postid , name , email , body);
        if (res.statusCode()==201) {
            //Get the comment id from respose object
            JsonPath jsnPath = res.jsonPath();
            String commentid = jsnPath.get("id").toString();

            //Update the json object values and pass it for update
            JSONObject obj = new JSONObject();
            obj.put("postId" , postid);
            obj.put("name" , name) ;
            obj.put("email" , email);
            obj.put("body", updatedbody);

            Assert.assertEquals(given().when().queryParam("id", commentid ).body(obj).patch().getStatusCode(), 201);
        }
        else
            Assert.fail("Creation was failed");
    }

    @Test
    public void testDeleteComment() throws Exception {

        int postid=85;
        String name = "testAddNewComment name" ;
        String email = "deleteNewPost@gmail.com";
        String body=" delete testAddNewComment body";

        Response res = createComment(postid , name , email , body);

        if (res.statusCode()==201) {
            JsonPath jsnPath = res.jsonPath();
            String commentid = jsnPath.get("id").toString();
            Assert.assertEquals(given().when().queryParam("id", commentid).delete().getStatusCode(), 200);
        }
        else
            Assert.fail("Creation of comment is failed");
    }


    @Test
    public void testAddNewCommentPostIDWithNull() throws Exception {
        Integer intval = null;
        int postid= intval.intValue() ;
        String name = "testAddNewComment name" ;
        String email = "testAddNewPost@gmail.com";
        String body=" This is a testAddNewComment body";

        Response res = createComment(postid , name , email , body);
        Assert.assertEquals(res.statusCode() , 500);

    }

    public Response createComment(int postid , String name , String email , String body) throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("postId" , postid);
        obj.put("name" , name) ;
        obj.put("email" , email);
        obj.put("body", body);

        RequestSpecification request = RestAssured.given();
        Response resp = given().header("Content-Type", "application/json").body(obj.toString()).post();
        return resp;
    }




}
