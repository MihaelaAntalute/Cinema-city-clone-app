package com.cinema.cinemaapp.repository;

import com.cinema.cinemaapp.model.Role;
import com.cinema.cinemaapp.model.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    Role findByRoleType(RoleType roleType);
}
