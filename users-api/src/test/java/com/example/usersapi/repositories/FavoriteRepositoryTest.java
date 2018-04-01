package com.example.usersapi.repositories;

import com.example.usersapi.models.Favorite;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FavoriteRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private FavoriteRepository favoriteRepository;

    @Before
    public void setUp() {
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

        entityManager.persist(firstFavorite);
        entityManager.persist(secondFavorite);
        entityManager.flush();
    }

    @Test
    public void findAll_returnsAllFavorites() {
        List<Favorite> favoritesFromDb = favoriteRepository.findAll();

        assertThat(favoritesFromDb.size(), is(2));
    }

    @Test
    public void findAll_returnsLabel() {
        List<Favorite> favoritesFromDb = favoriteRepository.findAll();
        String secondFavoritesLabel = favoritesFromDb.get(1).getBoro_name();

        assertThat(secondFavoritesLabel, is("boro_two"));
    }

    @Test
    public void findAll_returnsImage_Url() {
        List<Favorite> favoritesFromDb = favoriteRepository.findAll();
        String secondFavoritesImage_Url = favoritesFromDb.get(1).getLocation_name();

        assertThat(secondFavoritesImage_Url, is("second_location"));
    }

    @Test
    public void findAll_returnsApi_Url() {
        List<Favorite> favoritesFromDb = favoriteRepository.findAll();
        String secondFavoritesApi_Url = favoritesFromDb.get(1).getRemarks();

        assertThat(secondFavoritesApi_Url, is("Free_for_the_first_hour"));
    }

}