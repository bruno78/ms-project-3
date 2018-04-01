package com.example.usersapi.controllers;

import com.example.usersapi.models.Favorite;
import com.example.usersapi.repositories.FavoriteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(FavoritesController.class)
public class FavoritesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FavoriteRepository mockFavoriteRepository;

    @Autowired
    private ObjectMapper jsonObjectMapper;

    private Favorite newFavorite;
    private Favorite updatedSecondFavorite;

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

        List<Favorite> mockFavorites =
                Stream.of(firstFavorite, secondFavorite).collect(Collectors.toList());

        given(mockFavoriteRepository.findAll()).willReturn(mockFavorites);

        given(mockFavoriteRepository.findOne(1L)).willReturn(firstFavorite);
        given(mockFavoriteRepository.findOne(4L)).willReturn(null);

        newFavorite = new Favorite(
                "third_location",
                "boro_three",
                "kiosk",
                "linkcity",
                "linkcity_wifi",
                "Free_for_the_first_hour",
                "",
                "1"
        );
        given(mockFavoriteRepository.save(newFavorite)).willReturn(newFavorite);

        updatedSecondFavorite = new Favorite(
                "second_location",
                "boro_two",
                "kiosk",
                "linkcity",
                "linkcity_wifi",
                "Free_for_the_first_hour",
                "Added_more_notes",
                "2"
        );
        given(mockFavoriteRepository.save(updatedSecondFavorite)).willReturn(updatedSecondFavorite);

        // Mock out Delete to return EmptyResultDataAccessException for missing user with ID of 4
        doAnswer(methodCall -> {
            throw new EmptyResultDataAccessException("ERROR MESSAGE FROM MOCK!!!", 1234);
        }).when(mockFavoriteRepository).delete(4L);
    }

    @Test
    public void findAllFavorites_success_returnsStatusOK() throws Exception {

        this.mockMvc
                .perform(get("/favorites"))
                .andExpect(status().isOk());
    }

    @Test
    public void findAllFavorites_success_returnAllFavoritesAsJSON() throws Exception {

        this.mockMvc
                .perform(get("/favorites"))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void findAllFavorites_success_returnLocationForEachFavorite() throws Exception {

        this.mockMvc
                .perform(get("/favorites"))
                .andExpect(jsonPath("$[0].location_name", is("first_location")));
    }

    @Test
    public void findAllFavorites_success_returnBoroForEachFavorite() throws Exception {

        this.mockMvc
                .perform(get("/favorites"))
                .andExpect(jsonPath("$[0].boro_name", is("boro_one")));
    }

    @Test
    public void findAllFavorites_success_returnNotesForEachFavorite() throws Exception {

        this.mockMvc
                .perform(get("/favorites"))
                .andExpect(jsonPath("$[0].notes", is("some_notes")));
    }

    @Test
    public void findFavoriteById_success_returnsStatusOK() throws Exception {

        this.mockMvc
                .perform(get("/favorites/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void findFavoriteById_success_returnLocation() throws Exception {

        this.mockMvc
                .perform(get("/favorites/1"))
                .andExpect(jsonPath("$.location_name", is("first_location")));
    }

    @Test
    public void findFavoriteById_success_returnProvider() throws Exception {

        this.mockMvc
                .perform(get("/favorites/1"))
                .andExpect(jsonPath("$.provider", is("linkcity")));
    }

    @Test
    public void findFavoriteById_success_returnRemarks() throws Exception {

        this.mockMvc
                .perform(get("/favorites/1"))
                .andExpect(jsonPath("$.remarks", is("Free_for_the_first_hour")));
    }

    @Test
    public void findFavoriteById_failure_userNotFoundReturns404() throws Exception {

        this.mockMvc
                .perform(get("/favorites/4"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findFavoriteById_failure_favoriteNotFoundReturnsNotFoundErrorMessage() throws Exception {

        this.mockMvc
                .perform(get("/favorites/4"))
                .andExpect(status().reason(containsString("Favorite Wifi Location with ID of 4 was not found!")));
    }

    @Test
    public void deleteFavoriteById_success_returnsStatusOk() throws Exception {

        this.mockMvc
                .perform(delete("/favorites/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteFavoriteById_success_deletesViaRepository() throws Exception {

        this.mockMvc.perform(delete("/favorites/1"));

        verify(mockFavoriteRepository, times(1)).delete(1L);
    }

    @Test
    public void deleteFavoriteById_failure_userNotFoundReturns404() throws Exception {

        this.mockMvc
                .perform(delete("/favorites/4"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createFavorite_success_returnsStatusOk() throws Exception {

        this.mockMvc
                .perform(
                        post("/favorites/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonObjectMapper.writeValueAsString(newFavorite))
                )
                .andExpect(status().isOk());
    }

    @Test
    public void createFavorite_success_returnsLocation() throws Exception {

        this.mockMvc
                .perform(
                        post("/favorites/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonObjectMapper.writeValueAsString(newFavorite))
                )
                .andExpect(jsonPath("$.location_name", is("third_location")));
    }

    @Test
    public void createFavorite_success_returnsSSID() throws Exception {

        this.mockMvc
                .perform(
                        post("/favorites/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonObjectMapper.writeValueAsString(newFavorite))
                )
                .andExpect(jsonPath("$.ssid", is("linkcity_wifi")));
    }

    @Test
    public void createFavorite_success_returnsRemarks() throws Exception {

        this.mockMvc
                .perform(
                        post("/favorites/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonObjectMapper.writeValueAsString(newFavorite))
                )
                .andExpect(jsonPath("$.remarks", is("Free_for_the_first_hour")));
    }

    @Test
    public void updateFavoriteById_success_returnsStatusOk() throws Exception {

        this.mockMvc
                .perform(
                        patch("/favorites/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonObjectMapper.writeValueAsString(updatedSecondFavorite))
                )
                .andExpect(status().isOk());
    }

    @Test
    public void updateFavoriteById_success_returnsUpdatedNotes() throws Exception {

        this.mockMvc
                .perform(
                        patch("/favorites/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonObjectMapper.writeValueAsString(updatedSecondFavorite))
                )
                .andExpect(jsonPath("$.notes", is("Added_more_notes")));
    }

    @Test
    public void updateFavoriteById_failure_favoriteNotFoundReturns404() throws Exception {

        this.mockMvc
                .perform(
                        patch("/favorites/4")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonObjectMapper.writeValueAsString(updatedSecondFavorite))
                )
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateFavoriteById_failure_favoriteNotFoundReturnsNotFoundErrorMessage() throws Exception {

        this.mockMvc
                .perform(
                        patch("/favorites/4")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonObjectMapper.writeValueAsString(updatedSecondFavorite))
                )
                .andExpect(status().reason(containsString("Favorite Wifi Location with ID of 4 was not found!")));
    }

}
