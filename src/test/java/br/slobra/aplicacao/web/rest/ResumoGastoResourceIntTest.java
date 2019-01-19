package br.slobra.aplicacao.web.rest;

import br.slobra.aplicacao.ObrasApp;

import br.slobra.aplicacao.domain.ResumoGasto;
import br.slobra.aplicacao.repository.ResumoGastoRepository;
import br.slobra.aplicacao.service.ResumoGastoService;
import br.slobra.aplicacao.service.dto.ResumoGastoDTO;
import br.slobra.aplicacao.service.mapper.ResumoGastoMapper;
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
import java.math.BigDecimal;
import java.util.List;


import static br.slobra.aplicacao.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ResumoGastoResource REST controller.
 *
 * @see ResumoGastoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ObrasApp.class)
public class ResumoGastoResourceIntTest {

    private static final String DEFAULT_NOME_OBRA = "AAAAAAAAAA";
    private static final String UPDATED_NOME_OBRA = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_VALOR_DEPOSITO = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_DEPOSITO = new BigDecimal(2);

    private static final BigDecimal DEFAULT_VALOR_DESPESA = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_DESPESA = new BigDecimal(2);

    private static final BigDecimal DEFAULT_VALOR_SALDO = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_SALDO = new BigDecimal(2);

    @Autowired
    private ResumoGastoRepository resumoGastoRepository;

    @Autowired
    private ResumoGastoMapper resumoGastoMapper;

    @Autowired
    private ResumoGastoService resumoGastoService;

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

    private MockMvc restResumoGastoMockMvc;

    private ResumoGasto resumoGasto;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ResumoGastoResource resumoGastoResource = new ResumoGastoResource(resumoGastoService);
        this.restResumoGastoMockMvc = MockMvcBuilders.standaloneSetup(resumoGastoResource)
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
    public static ResumoGasto createEntity(EntityManager em) {
        ResumoGasto resumoGasto = new ResumoGasto()
            .nomeObra(DEFAULT_NOME_OBRA)
            .valorDeposito(DEFAULT_VALOR_DEPOSITO)
            .valorDespesa(DEFAULT_VALOR_DESPESA)
            .valorSaldo(DEFAULT_VALOR_SALDO);
        return resumoGasto;
    }

    @Before
    public void initTest() {
        resumoGasto = createEntity(em);
    }

    @Test
    @Transactional
    public void createResumoGasto() throws Exception {
        int databaseSizeBeforeCreate = resumoGastoRepository.findAll().size();

        // Create the ResumoGasto
        ResumoGastoDTO resumoGastoDTO = resumoGastoMapper.toDto(resumoGasto);
        restResumoGastoMockMvc.perform(post("/api/resumo-gastos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resumoGastoDTO)))
            .andExpect(status().isCreated());

        // Validate the ResumoGasto in the database
        List<ResumoGasto> resumoGastoList = resumoGastoRepository.findAll();
        assertThat(resumoGastoList).hasSize(databaseSizeBeforeCreate + 1);
        ResumoGasto testResumoGasto = resumoGastoList.get(resumoGastoList.size() - 1);
        assertThat(testResumoGasto.getNomeObra()).isEqualTo(DEFAULT_NOME_OBRA);
        assertThat(testResumoGasto.getValorDeposito()).isEqualTo(DEFAULT_VALOR_DEPOSITO);
        assertThat(testResumoGasto.getValorDespesa()).isEqualTo(DEFAULT_VALOR_DESPESA);
        assertThat(testResumoGasto.getValorSaldo()).isEqualTo(DEFAULT_VALOR_SALDO);
    }

    @Test
    @Transactional
    public void createResumoGastoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = resumoGastoRepository.findAll().size();

        // Create the ResumoGasto with an existing ID
        resumoGasto.setId(1L);
        ResumoGastoDTO resumoGastoDTO = resumoGastoMapper.toDto(resumoGasto);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResumoGastoMockMvc.perform(post("/api/resumo-gastos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resumoGastoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ResumoGasto in the database
        List<ResumoGasto> resumoGastoList = resumoGastoRepository.findAll();
        assertThat(resumoGastoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllResumoGastos() throws Exception {
        // Initialize the database
        resumoGastoRepository.saveAndFlush(resumoGasto);

        // Get all the resumoGastoList
        restResumoGastoMockMvc.perform(get("/api/resumo-gastos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resumoGasto.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeObra").value(hasItem(DEFAULT_NOME_OBRA.toString())))
            .andExpect(jsonPath("$.[*].valorDeposito").value(hasItem(DEFAULT_VALOR_DEPOSITO.intValue())))
            .andExpect(jsonPath("$.[*].valorDespesa").value(hasItem(DEFAULT_VALOR_DESPESA.intValue())))
            .andExpect(jsonPath("$.[*].valorSaldo").value(hasItem(DEFAULT_VALOR_SALDO.intValue())));
    }
    
    @Test
    @Transactional
    public void getResumoGasto() throws Exception {
        // Initialize the database
        resumoGastoRepository.saveAndFlush(resumoGasto);

        // Get the resumoGasto
        restResumoGastoMockMvc.perform(get("/api/resumo-gastos/{id}", resumoGasto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(resumoGasto.getId().intValue()))
            .andExpect(jsonPath("$.nomeObra").value(DEFAULT_NOME_OBRA.toString()))
            .andExpect(jsonPath("$.valorDeposito").value(DEFAULT_VALOR_DEPOSITO.intValue()))
            .andExpect(jsonPath("$.valorDespesa").value(DEFAULT_VALOR_DESPESA.intValue()))
            .andExpect(jsonPath("$.valorSaldo").value(DEFAULT_VALOR_SALDO.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingResumoGasto() throws Exception {
        // Get the resumoGasto
        restResumoGastoMockMvc.perform(get("/api/resumo-gastos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResumoGasto() throws Exception {
        // Initialize the database
        resumoGastoRepository.saveAndFlush(resumoGasto);

        int databaseSizeBeforeUpdate = resumoGastoRepository.findAll().size();

        // Update the resumoGasto
        ResumoGasto updatedResumoGasto = resumoGastoRepository.findById(resumoGasto.getId()).get();
        // Disconnect from session so that the updates on updatedResumoGasto are not directly saved in db
        em.detach(updatedResumoGasto);
        updatedResumoGasto
            .nomeObra(UPDATED_NOME_OBRA)
            .valorDeposito(UPDATED_VALOR_DEPOSITO)
            .valorDespesa(UPDATED_VALOR_DESPESA)
            .valorSaldo(UPDATED_VALOR_SALDO);
        ResumoGastoDTO resumoGastoDTO = resumoGastoMapper.toDto(updatedResumoGasto);

        restResumoGastoMockMvc.perform(put("/api/resumo-gastos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resumoGastoDTO)))
            .andExpect(status().isOk());

        // Validate the ResumoGasto in the database
        List<ResumoGasto> resumoGastoList = resumoGastoRepository.findAll();
        assertThat(resumoGastoList).hasSize(databaseSizeBeforeUpdate);
        ResumoGasto testResumoGasto = resumoGastoList.get(resumoGastoList.size() - 1);
        assertThat(testResumoGasto.getNomeObra()).isEqualTo(UPDATED_NOME_OBRA);
        assertThat(testResumoGasto.getValorDeposito()).isEqualTo(UPDATED_VALOR_DEPOSITO);
        assertThat(testResumoGasto.getValorDespesa()).isEqualTo(UPDATED_VALOR_DESPESA);
        assertThat(testResumoGasto.getValorSaldo()).isEqualTo(UPDATED_VALOR_SALDO);
    }

    @Test
    @Transactional
    public void updateNonExistingResumoGasto() throws Exception {
        int databaseSizeBeforeUpdate = resumoGastoRepository.findAll().size();

        // Create the ResumoGasto
        ResumoGastoDTO resumoGastoDTO = resumoGastoMapper.toDto(resumoGasto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResumoGastoMockMvc.perform(put("/api/resumo-gastos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resumoGastoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ResumoGasto in the database
        List<ResumoGasto> resumoGastoList = resumoGastoRepository.findAll();
        assertThat(resumoGastoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteResumoGasto() throws Exception {
        // Initialize the database
        resumoGastoRepository.saveAndFlush(resumoGasto);

        int databaseSizeBeforeDelete = resumoGastoRepository.findAll().size();

        // Get the resumoGasto
        restResumoGastoMockMvc.perform(delete("/api/resumo-gastos/{id}", resumoGasto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ResumoGasto> resumoGastoList = resumoGastoRepository.findAll();
        assertThat(resumoGastoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResumoGasto.class);
        ResumoGasto resumoGasto1 = new ResumoGasto();
        resumoGasto1.setId(1L);
        ResumoGasto resumoGasto2 = new ResumoGasto();
        resumoGasto2.setId(resumoGasto1.getId());
        assertThat(resumoGasto1).isEqualTo(resumoGasto2);
        resumoGasto2.setId(2L);
        assertThat(resumoGasto1).isNotEqualTo(resumoGasto2);
        resumoGasto1.setId(null);
        assertThat(resumoGasto1).isNotEqualTo(resumoGasto2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResumoGastoDTO.class);
        ResumoGastoDTO resumoGastoDTO1 = new ResumoGastoDTO();
        resumoGastoDTO1.setId(1L);
        ResumoGastoDTO resumoGastoDTO2 = new ResumoGastoDTO();
        assertThat(resumoGastoDTO1).isNotEqualTo(resumoGastoDTO2);
        resumoGastoDTO2.setId(resumoGastoDTO1.getId());
        assertThat(resumoGastoDTO1).isEqualTo(resumoGastoDTO2);
        resumoGastoDTO2.setId(2L);
        assertThat(resumoGastoDTO1).isNotEqualTo(resumoGastoDTO2);
        resumoGastoDTO1.setId(null);
        assertThat(resumoGastoDTO1).isNotEqualTo(resumoGastoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(resumoGastoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(resumoGastoMapper.fromId(null)).isNull();
    }
}
