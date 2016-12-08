package pl.edu.wat.web.rest;

import pl.edu.wat.TableTennisApp;

import pl.edu.wat.domain.Tournament;
import pl.edu.wat.repository.TournamentRepository;
import pl.edu.wat.service.TournamentService;
import pl.edu.wat.service.dto.TournamentDTO;
import pl.edu.wat.service.mapper.TournamentMapper;

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
 * Test class for the TournamentResource REST controller.
 *
 * @see TournamentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TableTennisApp.class)
public class TournamentResourceIntTest {

    private static final String DEFAULT_NAME = "AA";
    private static final String UPDATED_NAME = "BB";

    @Inject
    private TournamentRepository tournamentRepository;

    @Inject
    private TournamentMapper tournamentMapper;

    @Inject
    private TournamentService tournamentService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTournamentMockMvc;

    private Tournament tournament;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TournamentResource tournamentResource = new TournamentResource();
        ReflectionTestUtils.setField(tournamentResource, "tournamentService", tournamentService);
        this.restTournamentMockMvc = MockMvcBuilders.standaloneSetup(tournamentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tournament createEntity(EntityManager em) {
        Tournament tournament = new Tournament()
                .name(DEFAULT_NAME);
        return tournament;
    }

    @Before
    public void initTest() {
        tournament = createEntity(em);
    }

    @Test
    @Transactional
    public void createTournament() throws Exception {
        int databaseSizeBeforeCreate = tournamentRepository.findAll().size();

        // Create the Tournament
        TournamentDTO tournamentDTO = tournamentMapper.tournamentToTournamentDTO(tournament);

        restTournamentMockMvc.perform(post("/api/tournaments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tournamentDTO)))
                .andExpect(status().isCreated());

        // Validate the Tournament in the database
        List<Tournament> tournaments = tournamentRepository.findAll();
        assertThat(tournaments).hasSize(databaseSizeBeforeCreate + 1);
        Tournament testTournament = tournaments.get(tournaments.size() - 1);
        assertThat(testTournament.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tournamentRepository.findAll().size();
        // set the field null
        tournament.setName(null);

        // Create the Tournament, which fails.
        TournamentDTO tournamentDTO = tournamentMapper.tournamentToTournamentDTO(tournament);

        restTournamentMockMvc.perform(post("/api/tournaments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tournamentDTO)))
                .andExpect(status().isBadRequest());

        List<Tournament> tournaments = tournamentRepository.findAll();
        assertThat(tournaments).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTournaments() throws Exception {
        // Initialize the database
        tournamentRepository.saveAndFlush(tournament);

        // Get all the tournaments
        restTournamentMockMvc.perform(get("/api/tournaments?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(tournament.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getTournament() throws Exception {
        // Initialize the database
        tournamentRepository.saveAndFlush(tournament);

        // Get the tournament
        restTournamentMockMvc.perform(get("/api/tournaments/{id}", tournament.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tournament.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTournament() throws Exception {
        // Get the tournament
        restTournamentMockMvc.perform(get("/api/tournaments/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTournament() throws Exception {
        // Initialize the database
        tournamentRepository.saveAndFlush(tournament);
        int databaseSizeBeforeUpdate = tournamentRepository.findAll().size();

        // Update the tournament
        Tournament updatedTournament = tournamentRepository.findOne(tournament.getId());
        updatedTournament
                .name(UPDATED_NAME);
        TournamentDTO tournamentDTO = tournamentMapper.tournamentToTournamentDTO(updatedTournament);

        restTournamentMockMvc.perform(put("/api/tournaments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tournamentDTO)))
                .andExpect(status().isOk());

        // Validate the Tournament in the database
        List<Tournament> tournaments = tournamentRepository.findAll();
        assertThat(tournaments).hasSize(databaseSizeBeforeUpdate);
        Tournament testTournament = tournaments.get(tournaments.size() - 1);
        assertThat(testTournament.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteTournament() throws Exception {
        // Initialize the database
        tournamentRepository.saveAndFlush(tournament);
        int databaseSizeBeforeDelete = tournamentRepository.findAll().size();

        // Get the tournament
        restTournamentMockMvc.perform(delete("/api/tournaments/{id}", tournament.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Tournament> tournaments = tournamentRepository.findAll();
        assertThat(tournaments).hasSize(databaseSizeBeforeDelete - 1);
    }
}
