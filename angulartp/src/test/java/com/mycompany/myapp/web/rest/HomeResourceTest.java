package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Home;
import com.mycompany.myapp.repository.HomeRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the HomeResource REST controller.
 *
 * @see HomeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HomeResourceTest {

    private static final String DEFAULT_ADRESSE = "SAMPLE_TEXT";
    private static final String UPDATED_ADRESSE = "UPDATED_TEXT";

    private static final Integer DEFAULT_AIRE = 0;
    private static final Integer UPDATED_AIRE = 1;
    private static final String DEFAULT_IP = "SAMPLE_TEXT";
    private static final String UPDATED_IP = "UPDATED_TEXT";

    @Inject
    private HomeRepository homeRepository;

    private MockMvc restHomeMockMvc;

    private Home home;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HomeResource homeResource = new HomeResource();
        ReflectionTestUtils.setField(homeResource, "homeRepository", homeRepository);
        this.restHomeMockMvc = MockMvcBuilders.standaloneSetup(homeResource).build();
    }

    @Before
    public void initTest() {
        home = new Home();
        home.setAdresse(DEFAULT_ADRESSE);
        home.setAire(DEFAULT_AIRE);
        home.setIp(DEFAULT_IP);
    }

    @Test
    @Transactional
    public void createHome() throws Exception {
        int databaseSizeBeforeCreate = homeRepository.findAll().size();

        // Create the Home
        restHomeMockMvc.perform(post("/api/homes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(home)))
                .andExpect(status().isCreated());

        // Validate the Home in the database
        List<Home> homes = homeRepository.findAll();
        assertThat(homes).hasSize(databaseSizeBeforeCreate + 1);
        Home testHome = homes.get(homes.size() - 1);
        assertThat(testHome.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testHome.getAire()).isEqualTo(DEFAULT_AIRE);
        assertThat(testHome.getIp()).isEqualTo(DEFAULT_IP);
    }

    @Test
    @Transactional
    public void getAllHomes() throws Exception {
        // Initialize the database
        homeRepository.saveAndFlush(home);

        // Get all the homes
        restHomeMockMvc.perform(get("/api/homes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(home.getId().intValue())))
                .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE.toString())))
                .andExpect(jsonPath("$.[*].aire").value(hasItem(DEFAULT_AIRE)))
                .andExpect(jsonPath("$.[*].ip").value(hasItem(DEFAULT_IP.toString())));
    }

    @Test
    @Transactional
    public void getHome() throws Exception {
        // Initialize the database
        homeRepository.saveAndFlush(home);

        // Get the home
        restHomeMockMvc.perform(get("/api/homes/{id}", home.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(home.getId().intValue()))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE.toString()))
            .andExpect(jsonPath("$.aire").value(DEFAULT_AIRE))
            .andExpect(jsonPath("$.ip").value(DEFAULT_IP.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHome() throws Exception {
        // Get the home
        restHomeMockMvc.perform(get("/api/homes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHome() throws Exception {
        // Initialize the database
        homeRepository.saveAndFlush(home);

		int databaseSizeBeforeUpdate = homeRepository.findAll().size();

        // Update the home
        home.setAdresse(UPDATED_ADRESSE);
        home.setAire(UPDATED_AIRE);
        home.setIp(UPDATED_IP);
        restHomeMockMvc.perform(put("/api/homes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(home)))
                .andExpect(status().isOk());

        // Validate the Home in the database
        List<Home> homes = homeRepository.findAll();
        assertThat(homes).hasSize(databaseSizeBeforeUpdate);
        Home testHome = homes.get(homes.size() - 1);
        assertThat(testHome.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testHome.getAire()).isEqualTo(UPDATED_AIRE);
        assertThat(testHome.getIp()).isEqualTo(UPDATED_IP);
    }

    @Test
    @Transactional
    public void deleteHome() throws Exception {
        // Initialize the database
        homeRepository.saveAndFlush(home);

		int databaseSizeBeforeDelete = homeRepository.findAll().size();

        // Get the home
        restHomeMockMvc.perform(delete("/api/homes/{id}", home.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Home> homes = homeRepository.findAll();
        assertThat(homes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
