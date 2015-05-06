package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Device;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Device entity.
 */
public interface DeviceRepository extends JpaRepository<Device,Long> {

}
