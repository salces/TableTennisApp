package pl.edu.wat.web.rest;

import pl.edu.wat.TableTennisApp;

import pl.edu.wat.domain.TournamentStage;
import pl.edu.wat.repository.TournamentStageRepository;
import pl.edu.wat.service.TournamentStageService;
import pl.edu.wat.service.dto.TournamentStageDTO;
import pl.edu.wat.service.mapper.TournamentStageMapper;

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

import pl.edu.wat.domain.enumeration.TournamentPhase;
/**
 * Test class for the TournamentStageResource REST controller.
 *
 * @see TournamentStageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TableTennisApp.class)
public class TournamentStageResourceIntTest {

    private static final TournamentPhase DEFAULT_PHASE = TournamentPhase.FINAL;
    private static final TournamentPhase UPDATED_PHASE = TournamentPhase.SEMIFINAL;

    private static final Integer DEFAULT_PHASE_CODE = 1;
    private static final Integer UPDATED_PHASE_CODE = 2;

    @Inject
    private TournamentStageRepository tournamentStageRepository;

    @Inject
    private TournamentStageMapper tournamentStageMapper;

    @Inject
    private TournamentStageService tournamentStageService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTournamentStageMockMvc;

    private TournamentStage tournamentStage;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TournamentStageResource tournamentStageResource = new TournamentStageResource();
        ReflectionTestUtils.setField(tournamentStageResource, "tournamentStageService", tournamentStageService);
        this.restTournamentStageMockMvc = MockMvcBuilders.standaloneSetup(tournamentStageResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TournamentStage createEntity(EntityManager em) {
        TournamentStage tournamentStage = new TournamentStage()
                .phase(DEFAULT_PHASE)
                .phaseCode(DEFAULT_PHASE_CODE);
        return tournamentStage;
    }

    @Before
    public void initTest() {
        tournamentStage = createEntity(em);
    }

    @Test
    @Transactional
    public void createTournamentStage() throws Exception {
        int databaseSizeBeforeCreate = tournamentStageRepository.findAll().size();

        // Create the TournamentStage
        TournamentStageDTO tournamentStageDTO = tournamentStageMapper.tournamentStageToTournamentStageDTO(tournamentStage);

        restTournamentStageMockMvc.perform(post("/api/tournament-stages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tournamentStageDTO)))
                .andExpect(status().isCreated());

        // Validate the TournamentStage in the database
        List<TournamentStage> tournamentStages = tournamentStageRepository.findAll();
        assertThat(tournamentStages).hasSize(databaseSizeBeforeCreate + 1);
        TournamentStage testTournamentStage = tournamentStages.get(tournamentStages.size() - 1);
        assertThat(testTournamentStage.getPhase()).isEqualTo(DEFAULT_PHASE);
        assertThat(testTournamentStage.getPhaseCode()).isEqualTo(DEFAULT_PHASE_CODE);
    }

    @Test
    @Transactional
    public void getAllTournamentStages() throws Exception {
        // Initialize the database
        tournamentStageRepository.saveAndFlush(tournamentStage);

        // Get all the tournamentStages
        restTournamentStageMockMvc.perform(get("/api/tournament-stages?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(tournamentStage.getId().intValue())))
                .andExpect(jsonPath("$.[*].phase").value(hasItem(DEFAULT_PHASE.toString())))
                .andExpect(jsonPath("$.[*].phaseCode").value(hasItem(DEFAULT_PHASE_CODE)));
    }

    @Test
    @Transactional
    public void getTournamentStage() throws Exception {
        // Initialize the database
        tournamentStageRepository.saveAndFlush(tournamentStage);

        // Get the tournamentStage
        restTournamentStageMockMvc.perform(get("/api/tournament-stages/{id}", tournamentStage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tournamentStage.getId().intValue()))
            .andExpect(jsonPath("$.phase").value(DEFAULT_PHASE.toString()))
            .andExpect(jsonPath("$.phaseCode").value(DEFAULT_PHASE_CODE));
    }

    @Test
    @Transactional
    public void getNonExistingTournamentStage() throws Exception {
        // Get the tournamentStage
        restTournamentStageMockMvc.perform(get("/api/tournament-stages/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTournamentStage() throws Exception {
        // Initialize the database
        tournamentStageRepository.saveAndFlush(tournamentStage);
        int databaseSizeBeforeUpdate = tournamentStageRepository.findAll().size();

        // Update the tournamentStage
        TournamentStage updatedTournamentStage = tournamentStageRepository.findOne(tournamentStage.getId());
        updatedTournamentStage
                .phase(UPDATED_PHASE)
                .phaseCode(UPDATED_PHASE_CODE);
        TournamentStageDTO tournamentStageDTO = tournamentStageMapper.tournamentStageToTournamentStageDTO(updatedTournamentStage);

        restTournamentStageMockMvc.perform(put("/api/tournament-stages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tournamentStageDTO)))
                .andExpect(status().isOk());

        // Validate the TournamentStage in the database
        List<TournamentStage> tournamentStages = tournamentStageRepository.findAll();
        assertThat(tournamentStages).hasSize(databaseSizeBeforeUpdate);
        TournamentStage testTournamentStage = tournamentStages.get(tournamentStages.size() - 1);
        assertThat(testTournamentStage.getPhase()).isEqualTo(UPDATED_PHASE);
        assertThat(testTournamentStage.getPhaseCode()).isEqualTo(UPDATED_PHASE_CODE);
    }

    @Test
    @Transactional
    public void deleteTournamentStage() throws Exception {
        // Initialize the database
        tournamentStageRepository.saveAndFlush(tournamentStage);
        int databaseSizeBeforeDelete = tournamentStageRepository.findAll().size();

        // Get the tournamentStage
        restTournamentStageMockMvc.perform(delete("/api/tournament-stages/{id}", tournamentStage.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TournamentStage> tournamentStages = tournamentStageRepository.findAll();
        assertThat(tournamentStages).hasSize(databaseSizeBeforeDelete - 1);
    }
}
