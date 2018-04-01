package com.example.usersapi.repositories;



import com.example.usersapi.models.Favorite;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FavoriteRepository extends CrudRepository<Favorite, Long> {

    List<Favorite> findAll();

}