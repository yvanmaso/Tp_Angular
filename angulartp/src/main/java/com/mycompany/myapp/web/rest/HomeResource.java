package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Home;
import com.mycompany.myapp.repository.HomeRepository;
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
 * REST controller for managing Home.
 */
@RestController
@RequestMapping("/api")
public class HomeResource {

    private final Logger log = LoggerFactory.getLogger(HomeResource.class);

    @Inject
    private HomeRepository homeRepository;

    /**
     * POST  /homes -> Create a new home.
     */
    @RequestMapping(value = "/homes",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Home home) throws URISyntaxException {
        log.debug("REST request to save Home : {}", home);
        if (home.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new home cannot already have an ID").build();
        }
        homeRepository.save(home);
        return ResponseEntity.created(new URI("/api/homes/" + home.getId())).build();
    }

    /**
     * PUT  /homes -> Updates an existing home.
     */
    @RequestMapping(value = "/homes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Home home) throws URISyntaxException {
        log.debug("REST request to update Home : {}", home);
        if (home.getId() == null) {
            return create(home);
        }
        homeRepository.save(home);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /homes -> get all the homes.
     */
    @RequestMapping(value = "/homes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Home> getAll() {
        log.debug("REST request to get all Homes");
        return homeRepository.findAll();
    }

    /**
     * GET  /homes/:id -> get the "id" home.
     */
    @RequestMapping(value = "/homes/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Home> get(@PathVariable Long id) {
        log.debug("REST request to get Home : {}", id);
        return Optional.ofNullable(homeRepository.findOne(id))
            .map(home -> new ResponseEntity<>(
                home,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /homes/:id -> delete the "id" home.
     */
    @RequestMapping(value = "/homes/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Home : {}", id);
        homeRepository.delete(id);
    }
}
