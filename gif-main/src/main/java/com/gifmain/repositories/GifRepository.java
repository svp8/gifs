package com.gifmain.repositories;

import com.gifmain.models.Gif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GifRepository extends JpaRepository<Gif,Integer> {

}
