package com.example.microservicesandtesting.repostitories;




import com.example.microservicesandtesting.models.Favorite;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FavoriteRepository extends CrudRepository<Favorite, Long> {

    List<Favorite> findAll();

}