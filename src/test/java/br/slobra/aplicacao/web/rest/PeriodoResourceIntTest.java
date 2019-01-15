package br.slobra.aplicacao.web.rest;

import br.slobra.aplicacao.ObrasApp;

import br.slobra.aplicacao.domain.Periodo;
import br.slobra.aplicacao.repository.PeriodoRepository;
import br.slobra.aplicacao.service.PeriodoService;
import br.slobra.aplicacao.service.dto.PeriodoDTO;
import br.slobra.aplicacao.service.mapper.PeriodoMapper;
import br.slobra.aplicacao.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static br.slobra.aplicacao.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PeriodoResource REST controller.
 *
 * @see PeriodoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ObrasApp.class)
public class PeriodoResourceIntTest {

    private static final String DEFAULT_ID_CONTA = "AAAAAAAAAA";
    private static final String UPDATED_ID_CONTA = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PeriodoRepository periodoRepository;

    @Autowired
    private PeriodoMapper periodoMapper;

    @Autowired
    private PeriodoService periodoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restPeriodoMockMvc;

    private Periodo periodo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PeriodoResource periodoResource = new PeriodoResource(periodoService);
        this.restPeriodoMockMvc = MockMvcBuilders.standaloneSetup(periodoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Periodo createEntity(EntityManager em) {
        Periodo periodo = new Periodo()
            .idConta(DEFAULT_ID_CONTA)
            .data(DEFAULT_DATA);
        return periodo;
    }

    @Before
    public void initTest() {
        periodo = createEntity(em);
    }

    @Test
    @Transactional
    public void createPeriodo() throws Exception {
        int databaseSizeBeforeCreate = periodoRepository.findAll().size();

        // Create the Periodo
        PeriodoDTO periodoDTO = periodoMapper.toDto(periodo);
        restPeriodoMockMvc.perform(post("/api/periodos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodoDTO)))
            .andExpect(status().isCreated());

        // Validate the Periodo in the database
        List<Periodo> periodoList = periodoRepository.findAll();
        assertThat(periodoList).hasSize(databaseSizeBeforeCreate + 1);
        Periodo testPeriodo = periodoList.get(periodoList.size() - 1);
        assertThat(testPeriodo.getIdConta()).isEqualTo(DEFAULT_ID_CONTA);
        assertThat(testPeriodo.getData()).isEqualTo(DEFAULT_DATA);
    }

    @Test
    @Transactional
    public void createPeriodoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = periodoRepository.findAll().size();

        // Create the Periodo with an existing ID
        periodo.setId(1L);
        PeriodoDTO periodoDTO = periodoMapper.toDto(periodo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPeriodoMockMvc.perform(post("/api/periodos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Periodo in the database
        List<Periodo> periodoList = periodoRepository.findAll();
        assertThat(periodoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPeriodos() throws Exception {
        // Initialize the database
        periodoRepository.saveAndFlush(periodo);

        // Get all the periodoList
        restPeriodoMockMvc.perform(get("/api/periodos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(periodo.getId().intValue())))
            .andExpect(jsonPath("$.[*].idConta").value(hasItem(DEFAULT_ID_CONTA.toString())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())));
    }
    
    @Test
    @Transactional
    public void getPeriodo() throws Exception {
        // Initialize the database
        periodoRepository.saveAndFlush(periodo);

        // Get the periodo
        restPeriodoMockMvc.perform(get("/api/periodos/{id}", periodo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(periodo.getId().intValue()))
            .andExpect(jsonPath("$.idConta").value(DEFAULT_ID_CONTA.toString()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPeriodo() throws Exception {
        // Get the periodo
        restPeriodoMockMvc.perform(get("/api/periodos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePeriodo() throws Exception {
        // Initialize the database
        periodoRepository.saveAndFlush(periodo);

        int databaseSizeBeforeUpdate = periodoRepository.findAll().size();

        // Update the periodo
        Periodo updatedPeriodo = periodoRepository.findById(periodo.getId()).get();
        // Disconnect from session so that the updates on updatedPeriodo are not directly saved in db
        em.detach(updatedPeriodo);
        updatedPeriodo
            .idConta(UPDATED_ID_CONTA)
            .data(UPDATED_DATA);
        PeriodoDTO periodoDTO = periodoMapper.toDto(updatedPeriodo);

        restPeriodoMockMvc.perform(put("/api/periodos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodoDTO)))
            .andExpect(status().isOk());

        // Validate the Periodo in the database
        List<Periodo> periodoList = periodoRepository.findAll();
        assertThat(periodoList).hasSize(databaseSizeBeforeUpdate);
        Periodo testPeriodo = periodoList.get(periodoList.size() - 1);
        assertThat(testPeriodo.getIdConta()).isEqualTo(UPDATED_ID_CONTA);
        assertThat(testPeriodo.getData()).isEqualTo(UPDATED_DATA);
    }

    @Test
    @Transactional
    public void updateNonExistingPeriodo() throws Exception {
        int databaseSizeBeforeUpdate = periodoRepository.findAll().size();

        // Create the Periodo
        PeriodoDTO periodoDTO = periodoMapper.toDto(periodo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPeriodoMockMvc.perform(put("/api/periodos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Periodo in the database
        List<Periodo> periodoList = periodoRepository.findAll();
        assertThat(periodoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePeriodo() throws Exception {
        // Initialize the database
        periodoRepository.saveAndFlush(periodo);

        int databaseSizeBeforeDelete = periodoRepository.findAll().size();

        // Get the periodo
        restPeriodoMockMvc.perform(delete("/api/periodos/{id}", periodo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Periodo> periodoList = periodoRepository.findAll();
        assertThat(periodoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Periodo.class);
        Periodo periodo1 = new Periodo();
        periodo1.setId(1L);
        Periodo periodo2 = new Periodo();
        periodo2.setId(periodo1.getId());
        assertThat(periodo1).isEqualTo(periodo2);
        periodo2.setId(2L);
        assertThat(periodo1).isNotEqualTo(periodo2);
        periodo1.setId(null);
        assertThat(periodo1).isNotEqualTo(periodo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PeriodoDTO.class);
        PeriodoDTO periodoDTO1 = new PeriodoDTO();
        periodoDTO1.setId(1L);
        PeriodoDTO periodoDTO2 = new PeriodoDTO();
        assertThat(periodoDTO1).isNotEqualTo(periodoDTO2);
        periodoDTO2.setId(periodoDTO1.getId());
        assertThat(periodoDTO1).isEqualTo(periodoDTO2);
        periodoDTO2.setId(2L);
        assertThat(periodoDTO1).isNotEqualTo(periodoDTO2);
        periodoDTO1.setId(null);
        assertThat(periodoDTO1).isNotEqualTo(periodoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(periodoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(periodoMapper.fromId(null)).isNull();
    }
}
