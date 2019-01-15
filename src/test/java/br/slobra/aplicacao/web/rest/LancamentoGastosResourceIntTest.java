package br.slobra.aplicacao.web.rest;

import br.slobra.aplicacao.ObrasApp;

import br.slobra.aplicacao.domain.LancamentoGastos;
import br.slobra.aplicacao.repository.LancamentoGastosRepository;
import br.slobra.aplicacao.service.LancamentoGastosService;
import br.slobra.aplicacao.service.dto.LancamentoGastosDTO;
import br.slobra.aplicacao.service.mapper.LancamentoGastosMapper;
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
import java.util.List;


import static br.slobra.aplicacao.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the LancamentoGastosResource REST controller.
 *
 * @see LancamentoGastosResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ObrasApp.class)
public class LancamentoGastosResourceIntTest {

    private static final String DEFAULT_NOME_OBRA = "AAAAAAAAAA";
    private static final String UPDATED_NOME_OBRA = "BBBBBBBBBB";

    private static final Long DEFAULT_VALOR_DEPOSITO = 1L;
    private static final Long UPDATED_VALOR_DEPOSITO = 2L;

    private static final Long DEFAULT_VALOR_DESPESA = 1L;
    private static final Long UPDATED_VALOR_DESPESA = 2L;

    private static final Long DEFAULT_VALOR_SALDO = 1L;
    private static final Long UPDATED_VALOR_SALDO = 2L;

    @Autowired
    private LancamentoGastosRepository lancamentoGastosRepository;

    @Autowired
    private LancamentoGastosMapper lancamentoGastosMapper;

    @Autowired
    private LancamentoGastosService lancamentoGastosService;

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

    private MockMvc restLancamentoGastosMockMvc;

    private LancamentoGastos lancamentoGastos;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LancamentoGastosResource lancamentoGastosResource = new LancamentoGastosResource(lancamentoGastosService);
        this.restLancamentoGastosMockMvc = MockMvcBuilders.standaloneSetup(lancamentoGastosResource)
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
    public static LancamentoGastos createEntity(EntityManager em) {
        LancamentoGastos lancamentoGastos = new LancamentoGastos()
            .nomeObra(DEFAULT_NOME_OBRA)
            .valorDeposito(DEFAULT_VALOR_DEPOSITO)
            .valorDespesa(DEFAULT_VALOR_DESPESA)
            .valorSaldo(DEFAULT_VALOR_SALDO);
        return lancamentoGastos;
    }

    @Before
    public void initTest() {
        lancamentoGastos = createEntity(em);
    }

    @Test
    @Transactional
    public void createLancamentoGastos() throws Exception {
        int databaseSizeBeforeCreate = lancamentoGastosRepository.findAll().size();

        // Create the LancamentoGastos
        LancamentoGastosDTO lancamentoGastosDTO = lancamentoGastosMapper.toDto(lancamentoGastos);
        restLancamentoGastosMockMvc.perform(post("/api/lancamento-gastos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lancamentoGastosDTO)))
            .andExpect(status().isCreated());

        // Validate the LancamentoGastos in the database
        List<LancamentoGastos> lancamentoGastosList = lancamentoGastosRepository.findAll();
        assertThat(lancamentoGastosList).hasSize(databaseSizeBeforeCreate + 1);
        LancamentoGastos testLancamentoGastos = lancamentoGastosList.get(lancamentoGastosList.size() - 1);
        assertThat(testLancamentoGastos.getNomeObra()).isEqualTo(DEFAULT_NOME_OBRA);
        assertThat(testLancamentoGastos.getValorDeposito()).isEqualTo(DEFAULT_VALOR_DEPOSITO);
        assertThat(testLancamentoGastos.getValorDespesa()).isEqualTo(DEFAULT_VALOR_DESPESA);
        assertThat(testLancamentoGastos.getValorSaldo()).isEqualTo(DEFAULT_VALOR_SALDO);
    }

    @Test
    @Transactional
    public void createLancamentoGastosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lancamentoGastosRepository.findAll().size();

        // Create the LancamentoGastos with an existing ID
        lancamentoGastos.setId(1L);
        LancamentoGastosDTO lancamentoGastosDTO = lancamentoGastosMapper.toDto(lancamentoGastos);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLancamentoGastosMockMvc.perform(post("/api/lancamento-gastos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lancamentoGastosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LancamentoGastos in the database
        List<LancamentoGastos> lancamentoGastosList = lancamentoGastosRepository.findAll();
        assertThat(lancamentoGastosList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLancamentoGastos() throws Exception {
        // Initialize the database
        lancamentoGastosRepository.saveAndFlush(lancamentoGastos);

        // Get all the lancamentoGastosList
        restLancamentoGastosMockMvc.perform(get("/api/lancamento-gastos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lancamentoGastos.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeObra").value(hasItem(DEFAULT_NOME_OBRA.toString())))
            .andExpect(jsonPath("$.[*].valorDeposito").value(hasItem(DEFAULT_VALOR_DEPOSITO.intValue())))
            .andExpect(jsonPath("$.[*].valorDespesa").value(hasItem(DEFAULT_VALOR_DESPESA.intValue())))
            .andExpect(jsonPath("$.[*].valorSaldo").value(hasItem(DEFAULT_VALOR_SALDO.intValue())));
    }
    
    @Test
    @Transactional
    public void getLancamentoGastos() throws Exception {
        // Initialize the database
        lancamentoGastosRepository.saveAndFlush(lancamentoGastos);

        // Get the lancamentoGastos
        restLancamentoGastosMockMvc.perform(get("/api/lancamento-gastos/{id}", lancamentoGastos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lancamentoGastos.getId().intValue()))
            .andExpect(jsonPath("$.nomeObra").value(DEFAULT_NOME_OBRA.toString()))
            .andExpect(jsonPath("$.valorDeposito").value(DEFAULT_VALOR_DEPOSITO.intValue()))
            .andExpect(jsonPath("$.valorDespesa").value(DEFAULT_VALOR_DESPESA.intValue()))
            .andExpect(jsonPath("$.valorSaldo").value(DEFAULT_VALOR_SALDO.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLancamentoGastos() throws Exception {
        // Get the lancamentoGastos
        restLancamentoGastosMockMvc.perform(get("/api/lancamento-gastos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLancamentoGastos() throws Exception {
        // Initialize the database
        lancamentoGastosRepository.saveAndFlush(lancamentoGastos);

        int databaseSizeBeforeUpdate = lancamentoGastosRepository.findAll().size();

        // Update the lancamentoGastos
        LancamentoGastos updatedLancamentoGastos = lancamentoGastosRepository.findById(lancamentoGastos.getId()).get();
        // Disconnect from session so that the updates on updatedLancamentoGastos are not directly saved in db
        em.detach(updatedLancamentoGastos);
        updatedLancamentoGastos
            .nomeObra(UPDATED_NOME_OBRA)
            .valorDeposito(UPDATED_VALOR_DEPOSITO)
            .valorDespesa(UPDATED_VALOR_DESPESA)
            .valorSaldo(UPDATED_VALOR_SALDO);
        LancamentoGastosDTO lancamentoGastosDTO = lancamentoGastosMapper.toDto(updatedLancamentoGastos);

        restLancamentoGastosMockMvc.perform(put("/api/lancamento-gastos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lancamentoGastosDTO)))
            .andExpect(status().isOk());

        // Validate the LancamentoGastos in the database
        List<LancamentoGastos> lancamentoGastosList = lancamentoGastosRepository.findAll();
        assertThat(lancamentoGastosList).hasSize(databaseSizeBeforeUpdate);
        LancamentoGastos testLancamentoGastos = lancamentoGastosList.get(lancamentoGastosList.size() - 1);
        assertThat(testLancamentoGastos.getNomeObra()).isEqualTo(UPDATED_NOME_OBRA);
        assertThat(testLancamentoGastos.getValorDeposito()).isEqualTo(UPDATED_VALOR_DEPOSITO);
        assertThat(testLancamentoGastos.getValorDespesa()).isEqualTo(UPDATED_VALOR_DESPESA);
        assertThat(testLancamentoGastos.getValorSaldo()).isEqualTo(UPDATED_VALOR_SALDO);
    }

    @Test
    @Transactional
    public void updateNonExistingLancamentoGastos() throws Exception {
        int databaseSizeBeforeUpdate = lancamentoGastosRepository.findAll().size();

        // Create the LancamentoGastos
        LancamentoGastosDTO lancamentoGastosDTO = lancamentoGastosMapper.toDto(lancamentoGastos);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLancamentoGastosMockMvc.perform(put("/api/lancamento-gastos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lancamentoGastosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LancamentoGastos in the database
        List<LancamentoGastos> lancamentoGastosList = lancamentoGastosRepository.findAll();
        assertThat(lancamentoGastosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLancamentoGastos() throws Exception {
        // Initialize the database
        lancamentoGastosRepository.saveAndFlush(lancamentoGastos);

        int databaseSizeBeforeDelete = lancamentoGastosRepository.findAll().size();

        // Get the lancamentoGastos
        restLancamentoGastosMockMvc.perform(delete("/api/lancamento-gastos/{id}", lancamentoGastos.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LancamentoGastos> lancamentoGastosList = lancamentoGastosRepository.findAll();
        assertThat(lancamentoGastosList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LancamentoGastos.class);
        LancamentoGastos lancamentoGastos1 = new LancamentoGastos();
        lancamentoGastos1.setId(1L);
        LancamentoGastos lancamentoGastos2 = new LancamentoGastos();
        lancamentoGastos2.setId(lancamentoGastos1.getId());
        assertThat(lancamentoGastos1).isEqualTo(lancamentoGastos2);
        lancamentoGastos2.setId(2L);
        assertThat(lancamentoGastos1).isNotEqualTo(lancamentoGastos2);
        lancamentoGastos1.setId(null);
        assertThat(lancamentoGastos1).isNotEqualTo(lancamentoGastos2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LancamentoGastosDTO.class);
        LancamentoGastosDTO lancamentoGastosDTO1 = new LancamentoGastosDTO();
        lancamentoGastosDTO1.setId(1L);
        LancamentoGastosDTO lancamentoGastosDTO2 = new LancamentoGastosDTO();
        assertThat(lancamentoGastosDTO1).isNotEqualTo(lancamentoGastosDTO2);
        lancamentoGastosDTO2.setId(lancamentoGastosDTO1.getId());
        assertThat(lancamentoGastosDTO1).isEqualTo(lancamentoGastosDTO2);
        lancamentoGastosDTO2.setId(2L);
        assertThat(lancamentoGastosDTO1).isNotEqualTo(lancamentoGastosDTO2);
        lancamentoGastosDTO1.setId(null);
        assertThat(lancamentoGastosDTO1).isNotEqualTo(lancamentoGastosDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(lancamentoGastosMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(lancamentoGastosMapper.fromId(null)).isNull();
    }
}
