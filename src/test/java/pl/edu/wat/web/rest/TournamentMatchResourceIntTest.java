package pl.edu.wat.web.rest;

import pl.edu.wat.TableTennisApp;

import pl.edu.wat.domain.TournamentMatch;
import pl.edu.wat.repository.TournamentMatchRepository;
import pl.edu.wat.service.TournamentMatchService;

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
 * Test class for the TournamentMatchResource REST controller.
 *
 * @see TournamentMatchResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TableTennisApp.class)
public class TournamentMatchResourceIntTest {

    private static final Integer DEFAULT_FIRST_PLAYER_SCORE = 1;
    private static final Integer UPDATED_FIRST_PLAYER_SCORE = 2;

    private static final Integer DEFAULT_SECOND_PLAYER_SCORE = 1;
    private static final Integer UPDATED_SECOND_PLAYER_SCORE = 2;

    @Inject
    private TournamentMatchRepository tournamentMatchRepository;

    @Inject
    private TournamentMatchService tournamentMatchService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTournamentMatchMockMvc;

    private TournamentMatch tournamentMatch;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TournamentMatchResource tournamentMatchResource = new TournamentMatchResource();
        ReflectionTestUtils.setField(tournamentMatchResource, "tournamentMatchService", tournamentMatchService);
        this.restTournamentMatchMockMvc = MockMvcBuilders.standaloneSetup(tournamentMatchResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TournamentMatch createEntity(EntityManager em) {
        TournamentMatch tournamentMatch = new TournamentMatch()
                .firstPlayerScore(DEFAULT_FIRST_PLAYER_SCORE)
                .secondPlayerScore(DEFAULT_SECOND_PLAYER_SCORE);
        return tournamentMatch;
    }

    @Before
    public void initTest() {
        tournamentMatch = createEntity(em);
    }

    @Test
    @Transactional
    public void createTournamentMatch() throws Exception {
        int databaseSizeBeforeCreate = tournamentMatchRepository.findAll().size();

        // Create the TournamentMatch

        restTournamentMatchMockMvc.perform(post("/api/tournament-matches")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tournamentMatch)))
                .andExpect(status().isCreated());

        // Validate the TournamentMatch in the database
        List<TournamentMatch> tournamentMatches = tournamentMatchRepository.findAll();
        assertThat(tournamentMatches).hasSize(databaseSizeBeforeCreate + 1);
        TournamentMatch testTournamentMatch = tournamentMatches.get(tournamentMatches.size() - 1);
        assertThat(testTournamentMatch.getFirstPlayerScore()).isEqualTo(DEFAULT_FIRST_PLAYER_SCORE);
        assertThat(testTournamentMatch.getSecondPlayerScore()).isEqualTo(DEFAULT_SECOND_PLAYER_SCORE);
    }

    @Test
    @Transactional
    public void checkFirstPlayerScoreIsRequired() throws Exception {
        int databaseSizeBeforeTest = tournamentMatchRepository.findAll().size();
        // set the field null
        tournamentMatch.setFirstPlayerScore(null);

        // Create the TournamentMatch, which fails.

        restTournamentMatchMockMvc.perform(post("/api/tournament-matches")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tournamentMatch)))
                .andExpect(status().isBadRequest());

        List<TournamentMatch> tournamentMatches = tournamentMatchRepository.findAll();
        assertThat(tournamentMatches).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSecondPlayerScoreIsRequired() throws Exception {
        int databaseSizeBeforeTest = tournamentMatchRepository.findAll().size();
        // set the field null
        tournamentMatch.setSecondPlayerScore(null);

        // Create the TournamentMatch, which fails.

        restTournamentMatchMockMvc.perform(post("/api/tournament-matches")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tournamentMatch)))
                .andExpect(status().isBadRequest());

        List<TournamentMatch> tournamentMatches = tournamentMatchRepository.findAll();
        assertThat(tournamentMatches).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTournamentMatches() throws Exception {
        // Initialize the database
        tournamentMatchRepository.saveAndFlush(tournamentMatch);

        // Get all the tournamentMatches
        restTournamentMatchMockMvc.perform(get("/api/tournament-matches?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(tournamentMatch.getId().intValue())))
                .andExpect(jsonPath("$.[*].firstPlayerScore").value(hasItem(DEFAULT_FIRST_PLAYER_SCORE)))
                .andExpect(jsonPath("$.[*].secondPlayerScore").value(hasItem(DEFAULT_SECOND_PLAYER_SCORE)));
    }

    @Test
    @Transactional
    public void getTournamentMatch() throws Exception {
        // Initialize the database
        tournamentMatchRepository.saveAndFlush(tournamentMatch);

        // Get the tournamentMatch
        restTournamentMatchMockMvc.perform(get("/api/tournament-matches/{id}", tournamentMatch.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tournamentMatch.getId().intValue()))
            .andExpect(jsonPath("$.firstPlayerScore").value(DEFAULT_FIRST_PLAYER_SCORE))
            .andExpect(jsonPath("$.secondPlayerScore").value(DEFAULT_SECOND_PLAYER_SCORE));
    }

    @Test
    @Transactional
    public void getNonExistingTournamentMatch() throws Exception {
        // Get the tournamentMatch
        restTournamentMatchMockMvc.perform(get("/api/tournament-matches/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTournamentMatch() throws Exception {
        // Initialize the database
        tournamentMatchService.save(tournamentMatch);

        int databaseSizeBeforeUpdate = tournamentMatchRepository.findAll().size();

        // Update the tournamentMatch
        TournamentMatch updatedTournamentMatch = tournamentMatchRepository.findOne(tournamentMatch.getId());
        updatedTournamentMatch
                .firstPlayerScore(UPDATED_FIRST_PLAYER_SCORE)
                .secondPlayerScore(UPDATED_SECOND_PLAYER_SCORE);

        restTournamentMatchMockMvc.perform(put("/api/tournament-matches")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedTournamentMatch)))
                .andExpect(status().isOk());

        // Validate the TournamentMatch in the database
        List<TournamentMatch> tournamentMatches = tournamentMatchRepository.findAll();
        assertThat(tournamentMatches).hasSize(databaseSizeBeforeUpdate);
        TournamentMatch testTournamentMatch = tournamentMatches.get(tournamentMatches.size() - 1);
        assertThat(testTournamentMatch.getFirstPlayerScore()).isEqualTo(UPDATED_FIRST_PLAYER_SCORE);
        assertThat(testTournamentMatch.getSecondPlayerScore()).isEqualTo(UPDATED_SECOND_PLAYER_SCORE);
    }

    @Test
    @Transactional
    public void deleteTournamentMatch() throws Exception {
        // Initialize the database
        tournamentMatchService.save(tournamentMatch);

        int databaseSizeBeforeDelete = tournamentMatchRepository.findAll().size();

        // Get the tournamentMatch
        restTournamentMatchMockMvc.perform(delete("/api/tournament-matches/{id}", tournamentMatch.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TournamentMatch> tournamentMatches = tournamentMatchRepository.findAll();
        assertThat(tournamentMatches).hasSize(databaseSizeBeforeDelete - 1);
    }
}
