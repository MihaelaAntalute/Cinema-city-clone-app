package com.cinema.cinemaapp.repository;

import com.cinema.cinemaapp.model.Projection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectionRepository extends JpaRepository<Projection,Long> {
}
