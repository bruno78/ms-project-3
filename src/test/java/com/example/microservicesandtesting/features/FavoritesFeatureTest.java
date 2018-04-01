package com.example.microservicesandtesting.features;


import com.example.microservicesandtesting.models.Favorite;
import com.example.microservicesandtesting.models.User;
import com.example.microservicesandtesting.repostitories.FavoriteRepository;
import com.example.microservicesandtesting.repostitories.UserRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.http.ContentType.JSON;
import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.Is.is;


import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class FavoritesFeatureTest {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Before
    public void setUp() {
        favoriteRepository.deleteAll();
    }

    @After
    public void tearDown() {
        favoriteRepository.deleteAll();
    }

    @Test
    public void shouldAllowFullCrudForAFavorite() throws Exception {
        Favorite firstFavorite = new Favorite(
                "first_location",
                "boro_one",
                "kiosk",
                "linkcity",
                "linkcity_wifi",
                "Free_for_the_first_hour",
                "some_notes",
                "1"
        );

        Favorite secondFavorite = new Favorite(
                "second_location",
                "boro_two",
                "kiosk",
                "linkcity",
                "linkcity_wifi",
                "Free_for_the_first_hour",
                "",
                "2"
        );

        Stream.of(firstFavorite, secondFavorite)
                .forEach(user -> {
                    favoriteRepository.save(user);
                });

        when()
                .get("http://localhost:8080/users/favorites/")
                .then()
                .statusCode(is(200))
                .and().body(containsString("first_location"))
                .and().body(containsString("second_location"));

        // Test creating a User
        Favorite favoriteNotYetInDb = new Favorite(
                "new_location",
                "boro_three",
                "park",
                "linkcity",
                "linkcity_wifi",
                "Free_for_three_hours",
                "more_notes",
                "1"
        );

        given()
                .contentType(JSON)
                .and().body(favoriteNotYetInDb)
                .when()
                .post("http://localhost:8080/users/favorites/")
                .then()
                .statusCode(is(200))
                .and().body(containsString("new_location"));

        // Test get all Users
        when()
                .get("http://localhost:8080/users/favorites/")
                .then()
                .statusCode(is(200))
                .and().body(containsString("first_location"))
                .and().body(containsString("second_location"))
                .and().body(containsString("new_location"));

        // Test finding one user by ID
        when()
                .get("http://localhost:8080/users/favorites/" + secondFavorite.getId())
                .then()
                .statusCode(is(200))
                .and().body(containsString("boro_two"))
                .and().body(containsString("linkcity"));

        // Test updating a user
        secondFavorite.setNotes("favorite_place");

        given()
                .contentType(JSON)
                .and().body(secondFavorite)
                .when()
                .patch("http://localhost:8080/users/favorites/" + secondFavorite.getId())
                .then()
                .statusCode(is(200))
                .and().body(containsString("favorite_place"));

        // Test deleting a user
        when()
                .delete("http://localhost:8080/users/favorites/" + secondFavorite.getId())
                .then()
                .statusCode(is(200));
    }
}
