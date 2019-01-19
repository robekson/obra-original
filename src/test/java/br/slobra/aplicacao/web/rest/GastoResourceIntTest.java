package br.slobra.aplicacao.web.rest;

import br.slobra.aplicacao.ObrasApp;

import br.slobra.aplicacao.domain.Gasto;
import br.slobra.aplicacao.repository.GastoRepository;
import br.slobra.aplicacao.service.GastoService;
import br.slobra.aplicacao.service.dto.GastoDTO;
import br.slobra.aplicacao.service.mapper.GastoMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static br.slobra.aplicacao.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.slobra.aplicacao.domain.enumeration.Pago;
import br.slobra.aplicacao.domain.enumeration.NotaFiscal;
import br.slobra.aplicacao.domain.enumeration.TipoConta;
/**
 * Test class for the GastoResource REST controller.
 *
 * @see GastoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ObrasApp.class)
public class GastoResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_VALOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR = new BigDecimal(2);

    private static final LocalDate DEFAULT_DATA_VENCIMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_VENCIMENTO = LocalDate.now(ZoneId.systemDefault());

    private static final Pago DEFAULT_PAGAMENTO = Pago.SIM;
    private static final Pago UPDATED_PAGAMENTO = Pago.NAO;

    private static final NotaFiscal DEFAULT_NOTA = NotaFiscal.SIM;
    private static final NotaFiscal UPDATED_NOTA = NotaFiscal.NAO;

    private static final TipoConta DEFAULT_TIPO = TipoConta.MAO_DE_OBRA;
    private static final TipoConta UPDATED_TIPO = TipoConta.MATERIAIS;

    private static final Integer DEFAULT_PARCELADO = 1;
    private static final Integer UPDATED_PARCELADO = 2;

    private static final LocalDate DEFAULT_MES_ANO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MES_ANO = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private GastoRepository gastoRepository;

    @Autowired
    private GastoMapper gastoMapper;

    @Autowired
    private GastoService gastoService;

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

    private MockMvc restGastoMockMvc;

    private Gasto gasto;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GastoResource gastoResource = new GastoResource(gastoService);
        this.restGastoMockMvc = MockMvcBuilders.standaloneSetup(gastoResource)
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
    public static Gasto createEntity(EntityManager em) {
        Gasto gasto = new Gasto()
            .nome(DEFAULT_NOME)
            .valor(DEFAULT_VALOR)
            .dataVencimento(DEFAULT_DATA_VENCIMENTO)
            .pagamento(DEFAULT_PAGAMENTO)
            .nota(DEFAULT_NOTA)
            .tipo(DEFAULT_TIPO)
            .parcelado(DEFAULT_PARCELADO)
            .mesAno(DEFAULT_MES_ANO);
        return gasto;
    }

    @Before
    public void initTest() {
        gasto = createEntity(em);
    }

    @Test
    @Transactional
    public void createGasto() throws Exception {
        int databaseSizeBeforeCreate = gastoRepository.findAll().size();

        // Create the Gasto
        GastoDTO gastoDTO = gastoMapper.toDto(gasto);
        restGastoMockMvc.perform(post("/api/gastos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gastoDTO)))
            .andExpect(status().isCreated());

        // Validate the Gasto in the database
        List<Gasto> gastoList = gastoRepository.findAll();
        assertThat(gastoList).hasSize(databaseSizeBeforeCreate + 1);
        Gasto testGasto = gastoList.get(gastoList.size() - 1);
        assertThat(testGasto.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testGasto.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testGasto.getDataVencimento()).isEqualTo(DEFAULT_DATA_VENCIMENTO);
        assertThat(testGasto.getPagamento()).isEqualTo(DEFAULT_PAGAMENTO);
        assertThat(testGasto.getNota()).isEqualTo(DEFAULT_NOTA);
        assertThat(testGasto.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testGasto.getParcelado()).isEqualTo(DEFAULT_PARCELADO);
        assertThat(testGasto.getMesAno()).isEqualTo(DEFAULT_MES_ANO);
    }

    @Test
    @Transactional
    public void createGastoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gastoRepository.findAll().size();

        // Create the Gasto with an existing ID
        gasto.setId(1L);
        GastoDTO gastoDTO = gastoMapper.toDto(gasto);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGastoMockMvc.perform(post("/api/gastos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gastoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Gasto in the database
        List<Gasto> gastoList = gastoRepository.findAll();
        assertThat(gastoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = gastoRepository.findAll().size();
        // set the field null
        gasto.setNome(null);

        // Create the Gasto, which fails.
        GastoDTO gastoDTO = gastoMapper.toDto(gasto);

        restGastoMockMvc.perform(post("/api/gastos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gastoDTO)))
            .andExpect(status().isBadRequest());

        List<Gasto> gastoList = gastoRepository.findAll();
        assertThat(gastoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataVencimentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = gastoRepository.findAll().size();
        // set the field null
        gasto.setDataVencimento(null);

        // Create the Gasto, which fails.
        GastoDTO gastoDTO = gastoMapper.toDto(gasto);

        restGastoMockMvc.perform(post("/api/gastos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gastoDTO)))
            .andExpect(status().isBadRequest());

        List<Gasto> gastoList = gastoRepository.findAll();
        assertThat(gastoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGastos() throws Exception {
        // Initialize the database
        gastoRepository.saveAndFlush(gasto);

        // Get all the gastoList
        restGastoMockMvc.perform(get("/api/gastos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gasto.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())))
            .andExpect(jsonPath("$.[*].dataVencimento").value(hasItem(DEFAULT_DATA_VENCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].pagamento").value(hasItem(DEFAULT_PAGAMENTO.toString())))
            .andExpect(jsonPath("$.[*].nota").value(hasItem(DEFAULT_NOTA.toString())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].parcelado").value(hasItem(DEFAULT_PARCELADO)))
            .andExpect(jsonPath("$.[*].mesAno").value(hasItem(DEFAULT_MES_ANO.toString())));
    }
    
    @Test
    @Transactional
    public void getGasto() throws Exception {
        // Initialize the database
        gastoRepository.saveAndFlush(gasto);

        // Get the gasto
        restGastoMockMvc.perform(get("/api/gastos/{id}", gasto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(gasto.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.intValue()))
            .andExpect(jsonPath("$.dataVencimento").value(DEFAULT_DATA_VENCIMENTO.toString()))
            .andExpect(jsonPath("$.pagamento").value(DEFAULT_PAGAMENTO.toString()))
            .andExpect(jsonPath("$.nota").value(DEFAULT_NOTA.toString()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.parcelado").value(DEFAULT_PARCELADO))
            .andExpect(jsonPath("$.mesAno").value(DEFAULT_MES_ANO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGasto() throws Exception {
        // Get the gasto
        restGastoMockMvc.perform(get("/api/gastos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGasto() throws Exception {
        // Initialize the database
        gastoRepository.saveAndFlush(gasto);

        int databaseSizeBeforeUpdate = gastoRepository.findAll().size();

        // Update the gasto
        Gasto updatedGasto = gastoRepository.findById(gasto.getId()).get();
        // Disconnect from session so that the updates on updatedGasto are not directly saved in db
        em.detach(updatedGasto);
        updatedGasto
            .nome(UPDATED_NOME)
            .valor(UPDATED_VALOR)
            .dataVencimento(UPDATED_DATA_VENCIMENTO)
            .pagamento(UPDATED_PAGAMENTO)
            .nota(UPDATED_NOTA)
            .tipo(UPDATED_TIPO)
            .parcelado(UPDATED_PARCELADO)
            .mesAno(UPDATED_MES_ANO);
        GastoDTO gastoDTO = gastoMapper.toDto(updatedGasto);

        restGastoMockMvc.perform(put("/api/gastos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gastoDTO)))
            .andExpect(status().isOk());

        // Validate the Gasto in the database
        List<Gasto> gastoList = gastoRepository.findAll();
        assertThat(gastoList).hasSize(databaseSizeBeforeUpdate);
        Gasto testGasto = gastoList.get(gastoList.size() - 1);
        assertThat(testGasto.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testGasto.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testGasto.getDataVencimento()).isEqualTo(UPDATED_DATA_VENCIMENTO);
        assertThat(testGasto.getPagamento()).isEqualTo(UPDATED_PAGAMENTO);
        assertThat(testGasto.getNota()).isEqualTo(UPDATED_NOTA);
        assertThat(testGasto.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testGasto.getParcelado()).isEqualTo(UPDATED_PARCELADO);
        assertThat(testGasto.getMesAno()).isEqualTo(UPDATED_MES_ANO);
    }

    @Test
    @Transactional
    public void updateNonExistingGasto() throws Exception {
        int databaseSizeBeforeUpdate = gastoRepository.findAll().size();

        // Create the Gasto
        GastoDTO gastoDTO = gastoMapper.toDto(gasto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGastoMockMvc.perform(put("/api/gastos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gastoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Gasto in the database
        List<Gasto> gastoList = gastoRepository.findAll();
        assertThat(gastoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGasto() throws Exception {
        // Initialize the database
        gastoRepository.saveAndFlush(gasto);

        int databaseSizeBeforeDelete = gastoRepository.findAll().size();

        // Get the gasto
        restGastoMockMvc.perform(delete("/api/gastos/{id}", gasto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Gasto> gastoList = gastoRepository.findAll();
        assertThat(gastoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Gasto.class);
        Gasto gasto1 = new Gasto();
        gasto1.setId(1L);
        Gasto gasto2 = new Gasto();
        gasto2.setId(gasto1.getId());
        assertThat(gasto1).isEqualTo(gasto2);
        gasto2.setId(2L);
        assertThat(gasto1).isNotEqualTo(gasto2);
        gasto1.setId(null);
        assertThat(gasto1).isNotEqualTo(gasto2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GastoDTO.class);
        GastoDTO gastoDTO1 = new GastoDTO();
        gastoDTO1.setId(1L);
        GastoDTO gastoDTO2 = new GastoDTO();
        assertThat(gastoDTO1).isNotEqualTo(gastoDTO2);
        gastoDTO2.setId(gastoDTO1.getId());
        assertThat(gastoDTO1).isEqualTo(gastoDTO2);
        gastoDTO2.setId(2L);
        assertThat(gastoDTO1).isNotEqualTo(gastoDTO2);
        gastoDTO1.setId(null);
        assertThat(gastoDTO1).isNotEqualTo(gastoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(gastoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(gastoMapper.fromId(null)).isNull();
    }
}
