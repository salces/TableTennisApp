package pl.edu.wat.web.rest;

import pl.edu.wat.TableTennisApp;

import pl.edu.wat.domain.Round;
import pl.edu.wat.repository.RoundRepository;
import pl.edu.wat.service.RoundService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RoundResource REST controller.
 *
 * @see RoundResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TableTennisApp.class)
public class RoundResourceIntTest {

    @Inject
    private RoundRepository roundRepository;

    @Inject
    private RoundService roundService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restRoundMockMvc;

    private Round round;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RoundResource roundResource = new RoundResource();
        ReflectionTestUtils.setField(roundResource, "roundService", roundService);
        this.restRoundMockMvc = MockMvcBuilders.standaloneSetup(roundResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Round createEntity(EntityManager em) {
        Round round = new Round();
        return round;
    }

    @Before
    public void initTest() {
        round = createEntity(em);
    }

    @Test
    @Transactional
    public void createRound() throws Exception {
        int databaseSizeBeforeCreate = roundRepository.findAll().size();

        // Create the Round

        restRoundMockMvc.perform(post("/api/rounds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(round)))
                .andExpect(status().isCreated());

        // Validate the Round in the database
        List<Round> rounds = roundRepository.findAll();
        assertThat(rounds).hasSize(databaseSizeBeforeCreate + 1);
        Round testRound = rounds.get(rounds.size() - 1);
    }

    @Test
    @Transactional
    public void getAllRounds() throws Exception {
        // Initialize the database
        roundRepository.saveAndFlush(round);

        // Get all the rounds
        restRoundMockMvc.perform(get("/api/rounds?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(round.getId().intValue())));
    }

    @Test
    @Transactional
    public void getRound() throws Exception {
        // Initialize the database
        roundRepository.saveAndFlush(round);

        // Get the round
        restRoundMockMvc.perform(get("/api/rounds/{id}", round.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(round.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRound() throws Exception {
        // Get the round
        restRoundMockMvc.perform(get("/api/rounds/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRound() throws Exception {
        // Initialize the database
        roundService.save(round);

        int databaseSizeBeforeUpdate = roundRepository.findAll().size();

        // Update the round
        Round updatedRound = roundRepository.findOne(round.getId());

        restRoundMockMvc.perform(put("/api/rounds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedRound)))
                .andExpect(status().isOk());

        // Validate the Round in the database
        List<Round> rounds = roundRepository.findAll();
        assertThat(rounds).hasSize(databaseSizeBeforeUpdate);
        Round testRound = rounds.get(rounds.size() - 1);
    }

    @Test
    @Transactional
    public void deleteRound() throws Exception {
        // Initialize the database
        roundService.save(round);

        int databaseSizeBeforeDelete = roundRepository.findAll().size();

        // Get the round
        restRoundMockMvc.perform(delete("/api/rounds/{id}", round.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Round> rounds = roundRepository.findAll();
        assertThat(rounds).hasSize(databaseSizeBeforeDelete - 1);
    }
}
