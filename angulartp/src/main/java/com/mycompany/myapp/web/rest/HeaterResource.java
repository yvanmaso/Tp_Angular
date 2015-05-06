package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Heater;
import com.mycompany.myapp.repository.HeaterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Heater.
 */
@RestController
@RequestMapping("/api")
public class HeaterResource {

    private final Logger log = LoggerFactory.getLogger(HeaterResource.class);

    @Inject
    private HeaterRepository heaterRepository;

    /**
     * POST  /heaters -> Create a new heater.
     */
    @RequestMapping(value = "/heaters",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Heater heater) throws URISyntaxException {
        log.debug("REST request to save Heater : {}", heater);
        if (heater.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new heater cannot already have an ID").build();
        }
        heaterRepository.save(heater);
        return ResponseEntity.created(new URI("/api/heaters/" + heater.getId())).build();
    }

    /**
     * PUT  /heaters -> Updates an existing heater.
     */
    @RequestMapping(value = "/heaters",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Heater heater) throws URISyntaxException {
        log.debug("REST request to update Heater : {}", heater);
        if (heater.getId() == null) {
            return create(heater);
        }
        heaterRepository.save(heater);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /heaters -> get all the heaters.
     */
    @RequestMapping(value = "/heaters",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Heater> getAll() {
        log.debug("REST request to get all Heaters");
        return heaterRepository.findAll();
    }

    /**
     * GET  /heaters/:id -> get the "id" heater.
     */
    @RequestMapping(value = "/heaters/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Heater> get(@PathVariable Long id) {
        log.debug("REST request to get Heater : {}", id);
        return Optional.ofNullable(heaterRepository.findOne(id))
            .map(heater -> new ResponseEntity<>(
                heater,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /heaters/:id -> delete the "id" heater.
     */
    @RequestMapping(value = "/heaters/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Heater : {}", id);
        heaterRepository.delete(id);
    }
}
