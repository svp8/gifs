package com.gifs.repositories;

import com.gifs.models.Gif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<Gif,Integer> {
}
