package pl.edu.wat.web.rest;

import pl.edu.wat.TableTennisApp;

import pl.edu.wat.domain.Club;
import pl.edu.wat.repository.ClubRepository;
import pl.edu.wat.service.ClubService;
import pl.edu.wat.service.dto.ClubDTO;
import pl.edu.wat.service.mapper.ClubMapper;

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
 * Test class for the ClubResource REST controller.
 *
 * @see ClubResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TableTennisApp.class)
public class ClubResourceIntTest {

    private static final String DEFAULT_PREFIX = "AA";
    private static final String UPDATED_PREFIX = "BB";

    private static final String DEFAULT_LOCATION = "AA";
    private static final String UPDATED_LOCATION = "BB";

    private static final Integer DEFAULT_ESTABILISHED = 1750;
    private static final Integer UPDATED_ESTABILISHED = 1751;

    private static final String DEFAULT_EMAIL = "AAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBB";

    private static final String DEFAULT_HOME_PAGE = "AAAAAAAAAA";
    private static final String UPDATED_HOME_PAGE = "BBBBBBBBBB";

    @Inject
    private ClubRepository clubRepository;

    @Inject
    private ClubMapper clubMapper;

    @Inject
    private ClubService clubService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restClubMockMvc;

    private Club club;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ClubResource clubResource = new ClubResource();
        ReflectionTestUtils.setField(clubResource, "clubService", clubService);
        this.restClubMockMvc = MockMvcBuilders.standaloneSetup(clubResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Club createEntity(EntityManager em) {
        Club club = new Club()
                .prefix(DEFAULT_PREFIX)
                .location(DEFAULT_LOCATION)
                .estabilished(DEFAULT_ESTABILISHED)
                .email(DEFAULT_EMAIL)
                .homePage(DEFAULT_HOME_PAGE);
        return club;
    }

    @Before
    public void initTest() {
        club = createEntity(em);
    }

    @Test
    @Transactional
    public void createClub() throws Exception {
        int databaseSizeBeforeCreate = clubRepository.findAll().size();

        // Create the Club
        ClubDTO clubDTO = clubMapper.clubToClubDTO(club);

        restClubMockMvc.perform(post("/api/clubs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(clubDTO)))
                .andExpect(status().isCreated());

        // Validate the Club in the database
        List<Club> clubs = clubRepository.findAll();
        assertThat(clubs).hasSize(databaseSizeBeforeCreate + 1);
        Club testClub = clubs.get(clubs.size() - 1);
        assertThat(testClub.getPrefix()).isEqualTo(DEFAULT_PREFIX);
        assertThat(testClub.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testClub.getEstabilished()).isEqualTo(DEFAULT_ESTABILISHED);
        assertThat(testClub.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testClub.getHomePage()).isEqualTo(DEFAULT_HOME_PAGE);
    }

    @Test
    @Transactional
    public void checkPrefixIsRequired() throws Exception {
        int databaseSizeBeforeTest = clubRepository.findAll().size();
        // set the field null
        club.setPrefix(null);

        // Create the Club, which fails.
        ClubDTO clubDTO = clubMapper.clubToClubDTO(club);

        restClubMockMvc.perform(post("/api/clubs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(clubDTO)))
                .andExpect(status().isBadRequest());

        List<Club> clubs = clubRepository.findAll();
        assertThat(clubs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocationIsRequired() throws Exception {
        int databaseSizeBeforeTest = clubRepository.findAll().size();
        // set the field null
        club.setLocation(null);

        // Create the Club, which fails.
        ClubDTO clubDTO = clubMapper.clubToClubDTO(club);

        restClubMockMvc.perform(post("/api/clubs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(clubDTO)))
                .andExpect(status().isBadRequest());

        List<Club> clubs = clubRepository.findAll();
        assertThat(clubs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEstabilishedIsRequired() throws Exception {
        int databaseSizeBeforeTest = clubRepository.findAll().size();
        // set the field null
        club.setEstabilished(null);

        // Create the Club, which fails.
        ClubDTO clubDTO = clubMapper.clubToClubDTO(club);

        restClubMockMvc.perform(post("/api/clubs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(clubDTO)))
                .andExpect(status().isBadRequest());

        List<Club> clubs = clubRepository.findAll();
        assertThat(clubs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = clubRepository.findAll().size();
        // set the field null
        club.setEmail(null);

        // Create the Club, which fails.
        ClubDTO clubDTO = clubMapper.clubToClubDTO(club);

        restClubMockMvc.perform(post("/api/clubs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(clubDTO)))
                .andExpect(status().isBadRequest());

        List<Club> clubs = clubRepository.findAll();
        assertThat(clubs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHomePageIsRequired() throws Exception {
        int databaseSizeBeforeTest = clubRepository.findAll().size();
        // set the field null
        club.setHomePage(null);

        // Create the Club, which fails.
        ClubDTO clubDTO = clubMapper.clubToClubDTO(club);

        restClubMockMvc.perform(post("/api/clubs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(clubDTO)))
                .andExpect(status().isBadRequest());

        List<Club> clubs = clubRepository.findAll();
        assertThat(clubs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClubs() throws Exception {
        // Initialize the database
        clubRepository.saveAndFlush(club);

        // Get all the clubs
        restClubMockMvc.perform(get("/api/clubs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(club.getId().intValue())))
                .andExpect(jsonPath("$.[*].prefix").value(hasItem(DEFAULT_PREFIX.toString())))
                .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
                .andExpect(jsonPath("$.[*].estabilished").value(hasItem(DEFAULT_ESTABILISHED)))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].homePage").value(hasItem(DEFAULT_HOME_PAGE.toString())));
    }

    @Test
    @Transactional
    public void getClub() throws Exception {
        // Initialize the database
        clubRepository.saveAndFlush(club);

        // Get the club
        restClubMockMvc.perform(get("/api/clubs/{id}", club.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(club.getId().intValue()))
            .andExpect(jsonPath("$.prefix").value(DEFAULT_PREFIX.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.estabilished").value(DEFAULT_ESTABILISHED))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.homePage").value(DEFAULT_HOME_PAGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClub() throws Exception {
        // Get the club
        restClubMockMvc.perform(get("/api/clubs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClub() throws Exception {
        // Initialize the database
        clubRepository.saveAndFlush(club);
        int databaseSizeBeforeUpdate = clubRepository.findAll().size();

        // Update the club
        Club updatedClub = clubRepository.findOne(club.getId());
        updatedClub
                .prefix(UPDATED_PREFIX)
                .location(UPDATED_LOCATION)
                .estabilished(UPDATED_ESTABILISHED)
                .email(UPDATED_EMAIL)
                .homePage(UPDATED_HOME_PAGE);
        ClubDTO clubDTO = clubMapper.clubToClubDTO(updatedClub);

        restClubMockMvc.perform(put("/api/clubs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(clubDTO)))
                .andExpect(status().isOk());

        // Validate the Club in the database
        List<Club> clubs = clubRepository.findAll();
        assertThat(clubs).hasSize(databaseSizeBeforeUpdate);
        Club testClub = clubs.get(clubs.size() - 1);
        assertThat(testClub.getPrefix()).isEqualTo(UPDATED_PREFIX);
        assertThat(testClub.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testClub.getEstabilished()).isEqualTo(UPDATED_ESTABILISHED);
        assertThat(testClub.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testClub.getHomePage()).isEqualTo(UPDATED_HOME_PAGE);
    }

    @Test
    @Transactional
    public void deleteClub() throws Exception {
        // Initialize the database
        clubRepository.saveAndFlush(club);
        int databaseSizeBeforeDelete = clubRepository.findAll().size();

        // Get the club
        restClubMockMvc.perform(delete("/api/clubs/{id}", club.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Club> clubs = clubRepository.findAll();
        assertThat(clubs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
