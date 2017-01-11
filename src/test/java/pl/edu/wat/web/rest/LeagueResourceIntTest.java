package pl.edu.wat.web.rest;

import pl.edu.wat.TableTennisApp;

import pl.edu.wat.domain.League;
import pl.edu.wat.repository.LeagueRepository;
import pl.edu.wat.service.LeagueService;

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
 * Test class for the LeagueResource REST controller.
 *
 * @see LeagueResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TableTennisApp.class)
public class LeagueResourceIntTest {

    private static final String DEFAULT_NAME = "AA";
    private static final String UPDATED_NAME = "BB";

    @Inject
    private LeagueRepository leagueRepository;

    @Inject
    private LeagueService leagueService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restLeagueMockMvc;

    private League league;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LeagueResource leagueResource = new LeagueResource();
        ReflectionTestUtils.setField(leagueResource, "leagueService", leagueService);
        this.restLeagueMockMvc = MockMvcBuilders.standaloneSetup(leagueResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static League createEntity(EntityManager em) {
        League league = new League()
                .name(DEFAULT_NAME);
        return league;
    }

    @Before
    public void initTest() {
        league = createEntity(em);
    }

    @Test
    @Transactional
    public void createLeague() throws Exception {
        int databaseSizeBeforeCreate = leagueRepository.findAll().size();

        // Create the League

        restLeagueMockMvc.perform(post("/api/leagues")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(league)))
                .andExpect(status().isCreated());

        // Validate the League in the database
        List<League> leagues = leagueRepository.findAll();
        assertThat(leagues).hasSize(databaseSizeBeforeCreate + 1);
        League testLeague = leagues.get(leagues.size() - 1);
        assertThat(testLeague.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = leagueRepository.findAll().size();
        // set the field null
        league.setName(null);

        // Create the League, which fails.

        restLeagueMockMvc.perform(post("/api/leagues")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(league)))
                .andExpect(status().isBadRequest());

        List<League> leagues = leagueRepository.findAll();
        assertThat(leagues).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLeagues() throws Exception {
        // Initialize the database
        leagueRepository.saveAndFlush(league);

        // Get all the leagues
        restLeagueMockMvc.perform(get("/api/leagues?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(league.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getLeague() throws Exception {
        // Initialize the database
        leagueRepository.saveAndFlush(league);

        // Get the league
        restLeagueMockMvc.perform(get("/api/leagues/{id}", league.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(league.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLeague() throws Exception {
        // Get the league
        restLeagueMockMvc.perform(get("/api/leagues/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLeague() throws Exception {
        // Initialize the database
        leagueService.save(league);

        int databaseSizeBeforeUpdate = leagueRepository.findAll().size();

        // Update the league
        League updatedLeague = leagueRepository.findOne(league.getId());
        updatedLeague
                .name(UPDATED_NAME);

        restLeagueMockMvc.perform(put("/api/leagues")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedLeague)))
                .andExpect(status().isOk());

        // Validate the League in the database
        List<League> leagues = leagueRepository.findAll();
        assertThat(leagues).hasSize(databaseSizeBeforeUpdate);
        League testLeague = leagues.get(leagues.size() - 1);
        assertThat(testLeague.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteLeague() throws Exception {
        // Initialize the database
        leagueService.save(league);

        int databaseSizeBeforeDelete = leagueRepository.findAll().size();

        // Get the league
        restLeagueMockMvc.perform(delete("/api/leagues/{id}", league.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<League> leagues = leagueRepository.findAll();
        assertThat(leagues).hasSize(databaseSizeBeforeDelete - 1);
    }
}
