package br.slobra.aplicacao.web.rest;

import br.slobra.aplicacao.ObrasApp;

import br.slobra.aplicacao.domain.Conta;
import br.slobra.aplicacao.repository.ContaRepository;
import br.slobra.aplicacao.service.ContaService;
import br.slobra.aplicacao.service.dto.ContaDTO;
import br.slobra.aplicacao.service.mapper.ContaMapper;
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
 * Test class for the ContaResource REST controller.
 *
 * @see ContaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ObrasApp.class)
public class ContaResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Long DEFAULT_VALOR = 1L;
    private static final Long UPDATED_VALOR = 2L;

    private static final Instant DEFAULT_DATA_VENCIMENTO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_VENCIMENTO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_PAGAMENTO = "AAAAAAAAAA";
    private static final String UPDATED_PAGAMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_NOTA = "AAAAAAAAAA";
    private static final String UPDATED_NOTA = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private ContaMapper contaMapper;

    @Autowired
    private ContaService contaService;

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

    private MockMvc restContaMockMvc;

    private Conta conta;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContaResource contaResource = new ContaResource(contaService);
        this.restContaMockMvc = MockMvcBuilders.standaloneSetup(contaResource)
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
    public static Conta createEntity(EntityManager em) {
        Conta conta = new Conta()
            .nome(DEFAULT_NOME)
            .valor(DEFAULT_VALOR)
            .dataVencimento(DEFAULT_DATA_VENCIMENTO)
            .pagamento(DEFAULT_PAGAMENTO)
            .nota(DEFAULT_NOTA)
            .tipo(DEFAULT_TIPO);
        return conta;
    }

    @Before
    public void initTest() {
        conta = createEntity(em);
    }

    @Test
    @Transactional
    public void createConta() throws Exception {
        int databaseSizeBeforeCreate = contaRepository.findAll().size();

        // Create the Conta
        ContaDTO contaDTO = contaMapper.toDto(conta);
        restContaMockMvc.perform(post("/api/contas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contaDTO)))
            .andExpect(status().isCreated());

        // Validate the Conta in the database
        List<Conta> contaList = contaRepository.findAll();
        assertThat(contaList).hasSize(databaseSizeBeforeCreate + 1);
        Conta testConta = contaList.get(contaList.size() - 1);
        assertThat(testConta.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testConta.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testConta.getDataVencimento()).isEqualTo(DEFAULT_DATA_VENCIMENTO);
        assertThat(testConta.getPagamento()).isEqualTo(DEFAULT_PAGAMENTO);
        assertThat(testConta.getNota()).isEqualTo(DEFAULT_NOTA);
        assertThat(testConta.getTipo()).isEqualTo(DEFAULT_TIPO);
    }

    @Test
    @Transactional
    public void createContaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contaRepository.findAll().size();

        // Create the Conta with an existing ID
        conta.setId(1L);
        ContaDTO contaDTO = contaMapper.toDto(conta);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContaMockMvc.perform(post("/api/contas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Conta in the database
        List<Conta> contaList = contaRepository.findAll();
        assertThat(contaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllContas() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList
        restContaMockMvc.perform(get("/api/contas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conta.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())))
            .andExpect(jsonPath("$.[*].dataVencimento").value(hasItem(DEFAULT_DATA_VENCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].pagamento").value(hasItem(DEFAULT_PAGAMENTO.toString())))
            .andExpect(jsonPath("$.[*].nota").value(hasItem(DEFAULT_NOTA.toString())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())));
    }
    
    @Test
    @Transactional
    public void getConta() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get the conta
        restContaMockMvc.perform(get("/api/contas/{id}", conta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(conta.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.intValue()))
            .andExpect(jsonPath("$.dataVencimento").value(DEFAULT_DATA_VENCIMENTO.toString()))
            .andExpect(jsonPath("$.pagamento").value(DEFAULT_PAGAMENTO.toString()))
            .andExpect(jsonPath("$.nota").value(DEFAULT_NOTA.toString()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingConta() throws Exception {
        // Get the conta
        restContaMockMvc.perform(get("/api/contas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConta() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        int databaseSizeBeforeUpdate = contaRepository.findAll().size();

        // Update the conta
        Conta updatedConta = contaRepository.findById(conta.getId()).get();
        // Disconnect from session so that the updates on updatedConta are not directly saved in db
        em.detach(updatedConta);
        updatedConta
            .nome(UPDATED_NOME)
            .valor(UPDATED_VALOR)
            .dataVencimento(UPDATED_DATA_VENCIMENTO)
            .pagamento(UPDATED_PAGAMENTO)
            .nota(UPDATED_NOTA)
            .tipo(UPDATED_TIPO);
        ContaDTO contaDTO = contaMapper.toDto(updatedConta);

        restContaMockMvc.perform(put("/api/contas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contaDTO)))
            .andExpect(status().isOk());

        // Validate the Conta in the database
        List<Conta> contaList = contaRepository.findAll();
        assertThat(contaList).hasSize(databaseSizeBeforeUpdate);
        Conta testConta = contaList.get(contaList.size() - 1);
        assertThat(testConta.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testConta.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testConta.getDataVencimento()).isEqualTo(UPDATED_DATA_VENCIMENTO);
        assertThat(testConta.getPagamento()).isEqualTo(UPDATED_PAGAMENTO);
        assertThat(testConta.getNota()).isEqualTo(UPDATED_NOTA);
        assertThat(testConta.getTipo()).isEqualTo(UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void updateNonExistingConta() throws Exception {
        int databaseSizeBeforeUpdate = contaRepository.findAll().size();

        // Create the Conta
        ContaDTO contaDTO = contaMapper.toDto(conta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContaMockMvc.perform(put("/api/contas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Conta in the database
        List<Conta> contaList = contaRepository.findAll();
        assertThat(contaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConta() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        int databaseSizeBeforeDelete = contaRepository.findAll().size();

        // Get the conta
        restContaMockMvc.perform(delete("/api/contas/{id}", conta.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Conta> contaList = contaRepository.findAll();
        assertThat(contaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Conta.class);
        Conta conta1 = new Conta();
        conta1.setId(1L);
        Conta conta2 = new Conta();
        conta2.setId(conta1.getId());
        assertThat(conta1).isEqualTo(conta2);
        conta2.setId(2L);
        assertThat(conta1).isNotEqualTo(conta2);
        conta1.setId(null);
        assertThat(conta1).isNotEqualTo(conta2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContaDTO.class);
        ContaDTO contaDTO1 = new ContaDTO();
        contaDTO1.setId(1L);
        ContaDTO contaDTO2 = new ContaDTO();
        assertThat(contaDTO1).isNotEqualTo(contaDTO2);
        contaDTO2.setId(contaDTO1.getId());
        assertThat(contaDTO1).isEqualTo(contaDTO2);
        contaDTO2.setId(2L);
        assertThat(contaDTO1).isNotEqualTo(contaDTO2);
        contaDTO1.setId(null);
        assertThat(contaDTO1).isNotEqualTo(contaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(contaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(contaMapper.fromId(null)).isNull();
    }
}
