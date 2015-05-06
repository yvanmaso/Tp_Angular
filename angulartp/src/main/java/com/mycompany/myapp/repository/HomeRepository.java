package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Home;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Home entity.
 */
public interface HomeRepository extends JpaRepository<Home,Long> {

}
