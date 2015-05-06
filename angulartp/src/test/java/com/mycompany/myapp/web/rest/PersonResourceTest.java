package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Person;
import com.mycompany.myapp.repository.PersonRepository;

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
import org.joda.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PersonResource REST controller.
 *
 * @see PersonResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PersonResourceTest {

    private static final String DEFAULT_NOM = "SAMPLE_TEXT";
    private static final String UPDATED_NOM = "UPDATED_TEXT";
    private static final String DEFAULT_PRENOM = "SAMPLE_TEXT";
    private static final String UPDATED_PRENOM = "UPDATED_TEXT";
    private static final String DEFAULT_GENRE = "SAMPLE_TEXT";
    private static final String UPDATED_GENRE = "UPDATED_TEXT";
    private static final String DEFAULT_MAIL = "SAMPLE_TEXT";
    private static final String UPDATED_MAIL = "UPDATED_TEXT";

    private static final LocalDate DEFAULT_DATENAISS = new LocalDate(0L);
    private static final LocalDate UPDATED_DATENAISS = new LocalDate();
    private static final String DEFAULT_PROFIL = "SAMPLE_TEXT";
    private static final String UPDATED_PROFIL = "UPDATED_TEXT";

    @Inject
    private PersonRepository personRepository;

    private MockMvc restPersonMockMvc;

    private Person person;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonResource personResource = new PersonResource();
        ReflectionTestUtils.setField(personResource, "personRepository", personRepository);
        this.restPersonMockMvc = MockMvcBuilders.standaloneSetup(personResource).build();
    }

    @Before
    public void initTest() {
        person = new Person();
        person.setNom(DEFAULT_NOM);
        person.setPrenom(DEFAULT_PRENOM);
        person.setGenre(DEFAULT_GENRE);
        person.setMail(DEFAULT_MAIL);
        person.setDatenaiss(DEFAULT_DATENAISS);
        person.setProfil(DEFAULT_PROFIL);
    }

    @Test
    @Transactional
    public void createPerson() throws Exception {
        int databaseSizeBeforeCreate = personRepository.findAll().size();

        // Create the Person
        restPersonMockMvc.perform(post("/api/persons")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(person)))
                .andExpect(status().isCreated());

        // Validate the Person in the database
        List<Person> persons = personRepository.findAll();
        assertThat(persons).hasSize(databaseSizeBeforeCreate + 1);
        Person testPerson = persons.get(persons.size() - 1);
        assertThat(testPerson.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testPerson.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testPerson.getGenre()).isEqualTo(DEFAULT_GENRE);
        assertThat(testPerson.getMail()).isEqualTo(DEFAULT_MAIL);
        assertThat(testPerson.getDatenaiss()).isEqualTo(DEFAULT_DATENAISS);
        assertThat(testPerson.getProfil()).isEqualTo(DEFAULT_PROFIL);
    }

    @Test
    @Transactional
    public void getAllPersons() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get all the persons
        restPersonMockMvc.perform(get("/api/persons"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(person.getId().intValue())))
                .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
                .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())))
                .andExpect(jsonPath("$.[*].genre").value(hasItem(DEFAULT_GENRE.toString())))
                .andExpect(jsonPath("$.[*].mail").value(hasItem(DEFAULT_MAIL.toString())))
                .andExpect(jsonPath("$.[*].datenaiss").value(hasItem(DEFAULT_DATENAISS.toString())))
                .andExpect(jsonPath("$.[*].profil").value(hasItem(DEFAULT_PROFIL.toString())));
    }

    @Test
    @Transactional
    public void getPerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get the person
        restPersonMockMvc.perform(get("/api/persons/{id}", person.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(person.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM.toString()))
            .andExpect(jsonPath("$.genre").value(DEFAULT_GENRE.toString()))
            .andExpect(jsonPath("$.mail").value(DEFAULT_MAIL.toString()))
            .andExpect(jsonPath("$.datenaiss").value(DEFAULT_DATENAISS.toString()))
            .andExpect(jsonPath("$.profil").value(DEFAULT_PROFIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPerson() throws Exception {
        // Get the person
        restPersonMockMvc.perform(get("/api/persons/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

		int databaseSizeBeforeUpdate = personRepository.findAll().size();

        // Update the person
        person.setNom(UPDATED_NOM);
        person.setPrenom(UPDATED_PRENOM);
        person.setGenre(UPDATED_GENRE);
        person.setMail(UPDATED_MAIL);
        person.setDatenaiss(UPDATED_DATENAISS);
        person.setProfil(UPDATED_PROFIL);
        restPersonMockMvc.perform(put("/api/persons")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(person)))
                .andExpect(status().isOk());

        // Validate the Person in the database
        List<Person> persons = personRepository.findAll();
        assertThat(persons).hasSize(databaseSizeBeforeUpdate);
        Person testPerson = persons.get(persons.size() - 1);
        assertThat(testPerson.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testPerson.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testPerson.getGenre()).isEqualTo(UPDATED_GENRE);
        assertThat(testPerson.getMail()).isEqualTo(UPDATED_MAIL);
        assertThat(testPerson.getDatenaiss()).isEqualTo(UPDATED_DATENAISS);
        assertThat(testPerson.getProfil()).isEqualTo(UPDATED_PROFIL);
    }

    @Test
    @Transactional
    public void deletePerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

		int databaseSizeBeforeDelete = personRepository.findAll().size();

        // Get the person
        restPersonMockMvc.perform(delete("/api/persons/{id}", person.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Person> persons = personRepository.findAll();
        assertThat(persons).hasSize(databaseSizeBeforeDelete - 1);
    }
}
