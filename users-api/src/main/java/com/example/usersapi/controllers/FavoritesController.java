package com.example.usersapi.controllers;

import com.example.usersapi.models.Favorite;
import com.example.usersapi.repositories.FavoriteRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class FavoritesController {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @GetMapping("/favorites")
    public List<Favorite> findAllFavorites() {
        return favoriteRepository.findAll();
    }

    @GetMapping("/favorites/{favoriteId}")
    public Favorite findFavoriteById(@PathVariable Long favoriteId) throws NotFoundException {

        Favorite foundFavorite = favoriteRepository.findOne(favoriteId);

        if (foundFavorite == null) {
            throw new NotFoundException("Favorite Wifi Location with ID of " + favoriteId + " was not found!");
        }

        return foundFavorite;
    }

    @PostMapping("/favorites")
    public Favorite createNewFavorite(@RequestBody Favorite newFavorite) {
        return favoriteRepository.save(newFavorite);
    }

    @DeleteMapping("/favorites/{favoriteId}")
    public HttpStatus deleteFavoriteById(@PathVariable Long favoriteId) throws EmptyResultDataAccessException {

        favoriteRepository.delete(favoriteId);
        return HttpStatus.OK;
    }

    @PatchMapping("/favorites/{favoriteId}")
    public Favorite updateFavoriteById(@PathVariable Long favoriteId, @RequestBody Favorite favoriteRequest) throws NotFoundException {
        Favorite favoriteFromDb = favoriteRepository.findOne(favoriteId);

        if (favoriteFromDb == null) {
            throw new NotFoundException("Favorite Wifi Location with ID of " + favoriteId + " was not found!");
        }


        favoriteFromDb.setLocation_name(favoriteRequest.getLocation_name());
        favoriteFromDb.setBoro_name(favoriteRequest.getBoro_name());
        favoriteFromDb.setType_name(favoriteRequest.getType_name());
        favoriteFromDb.setProvider(favoriteRequest.getProvider());
        favoriteFromDb.setSsid(favoriteRequest.getSsid());
        favoriteFromDb.setRemarks(favoriteRequest.getRemarks());
        favoriteFromDb.setNotes(favoriteRequest.getNotes());
        favoriteFromDb.setUser_id(favoriteRequest.getUser_id());

        return favoriteRepository.save(favoriteFromDb);
    }

    // EXCEPTION HANDLERS

    @ExceptionHandler
    void handleUserNotFound(
            NotFoundException exception,
            HttpServletResponse response) throws IOException {

        response.sendError(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

    @ExceptionHandler
    void handleDeleteNotFoundException(
            EmptyResultDataAccessException exception,
            HttpServletResponse response) throws IOException {

        response.sendError(HttpStatus.NOT_FOUND.value());
    }
}

