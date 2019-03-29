package br.slobra.aplicacao.web.rest;

import br.slobra.aplicacao.ObrasApp;

import br.slobra.aplicacao.domain.Obra;
import br.slobra.aplicacao.repository.ObraRepository;
import br.slobra.aplicacao.service.ObraService;
import br.slobra.aplicacao.service.dto.ObraDTO;
import br.slobra.aplicacao.service.mapper.ObraMapper;
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

import br.slobra.aplicacao.domain.enumeration.TipoCorretagem;
import br.slobra.aplicacao.domain.enumeration.StatusObra;
/**
 * Test class for the ObraResource REST controller.
 *
 * @see ObraResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ObrasApp.class)
public class ObraResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_ENDERECO = "AAAAAAAAAA";
    private static final String UPDATED_ENDERECO = "BBBBBBBBBB";

    private static final Long DEFAULT_METRAGEM = 1L;
    private static final Long UPDATED_METRAGEM = 2L;

    private static final BigDecimal DEFAULT_VALOR_TERRENO = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_TERRENO = new BigDecimal(2);

    private static final BigDecimal DEFAULT_VALOR_ESCRITURA = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_ESCRITURA = new BigDecimal(2);

    private static final BigDecimal DEFAULT_VALOR_ESCRITURA_COMPRA = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_ESCRITURA_COMPRA = new BigDecimal(2);

    private static final BigDecimal DEFAULT_VALOR_CORRETAGEM_VENDA = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_CORRETAGEM_VENDA = new BigDecimal(2);

    private static final BigDecimal DEFAULT_VALOR_FISCAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_FISCAL = new BigDecimal(2);

    private static final Integer DEFAULT_PORCENTAGEM_CORRETAGEM = 1;
    private static final Integer UPDATED_PORCENTAGEM_CORRETAGEM = 2;

    private static final TipoCorretagem DEFAULT_TIPO_CORRETAGEM = TipoCorretagem.Tipo1;
    private static final TipoCorretagem UPDATED_TIPO_CORRETAGEM = TipoCorretagem.Tipo2;

    private static final StatusObra DEFAULT_STATUS = StatusObra.ANDAMENTO;
    private static final StatusObra UPDATED_STATUS = StatusObra.FINALIZADA;

    private static final LocalDate DEFAULT_DATA_INICIO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_INICIO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATA_FIM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_FIM = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ObraRepository obraRepository;

    @Autowired
    private ObraMapper obraMapper;

    @Autowired
    private ObraService obraService;

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

    private MockMvc restObraMockMvc;

    private Obra obra;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ObraResource obraResource = new ObraResource(obraService);
        this.restObraMockMvc = MockMvcBuilders.standaloneSetup(obraResource)
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
    public static Obra createEntity(EntityManager em) {
        Obra obra = new Obra()
            .nome(DEFAULT_NOME)
            .endereco(DEFAULT_ENDERECO)
            .metragem(DEFAULT_METRAGEM)
            .valorTerreno(DEFAULT_VALOR_TERRENO)
            .valorEscritura(DEFAULT_VALOR_ESCRITURA)
            .valorEscrituraCompra(DEFAULT_VALOR_ESCRITURA_COMPRA)
            .valorCorretagemVenda(DEFAULT_VALOR_CORRETAGEM_VENDA)
            .valorFiscal(DEFAULT_VALOR_FISCAL)
            .porcentagemCorretagem(DEFAULT_PORCENTAGEM_CORRETAGEM)
            .tipoCorretagem(DEFAULT_TIPO_CORRETAGEM)
            .status(DEFAULT_STATUS)
            .dataInicio(DEFAULT_DATA_INICIO)
            .dataFim(DEFAULT_DATA_FIM);
        return obra;
    }

    @Before
    public void initTest() {
        obra = createEntity(em);
    }

    @Test
    @Transactional
    public void createObra() throws Exception {
        int databaseSizeBeforeCreate = obraRepository.findAll().size();

        // Create the Obra
        ObraDTO obraDTO = obraMapper.toDto(obra);
        restObraMockMvc.perform(post("/api/obras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(obraDTO)))
            .andExpect(status().isCreated());

        // Validate the Obra in the database
        List<Obra> obraList = obraRepository.findAll();
        assertThat(obraList).hasSize(databaseSizeBeforeCreate + 1);
        Obra testObra = obraList.get(obraList.size() - 1);
        assertThat(testObra.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testObra.getEndereco()).isEqualTo(DEFAULT_ENDERECO);
        assertThat(testObra.getMetragem()).isEqualTo(DEFAULT_METRAGEM);
        assertThat(testObra.getValorTerreno()).isEqualTo(DEFAULT_VALOR_TERRENO);
        assertThat(testObra.getValorEscritura()).isEqualTo(DEFAULT_VALOR_ESCRITURA);
        assertThat(testObra.getValorEscrituraCompra()).isEqualTo(DEFAULT_VALOR_ESCRITURA_COMPRA);
        assertThat(testObra.getValorCorretagemVenda()).isEqualTo(DEFAULT_VALOR_CORRETAGEM_VENDA);
        assertThat(testObra.getValorFiscal()).isEqualTo(DEFAULT_VALOR_FISCAL);
        assertThat(testObra.getPorcentagemCorretagem()).isEqualTo(DEFAULT_PORCENTAGEM_CORRETAGEM);
        assertThat(testObra.getTipoCorretagem()).isEqualTo(DEFAULT_TIPO_CORRETAGEM);
        assertThat(testObra.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testObra.getDataInicio()).isEqualTo(DEFAULT_DATA_INICIO);
        assertThat(testObra.getDataFim()).isEqualTo(DEFAULT_DATA_FIM);
    }

    @Test
    @Transactional
    public void createObraWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = obraRepository.findAll().size();

        // Create the Obra with an existing ID
        obra.setId(1L);
        ObraDTO obraDTO = obraMapper.toDto(obra);

        // An entity with an existing ID cannot be created, so this API call must fail
        restObraMockMvc.perform(post("/api/obras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(obraDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Obra in the database
        List<Obra> obraList = obraRepository.findAll();
        assertThat(obraList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = obraRepository.findAll().size();
        // set the field null
        obra.setNome(null);

        // Create the Obra, which fails.
        ObraDTO obraDTO = obraMapper.toDto(obra);

        restObraMockMvc.perform(post("/api/obras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(obraDTO)))
            .andExpect(status().isBadRequest());

        List<Obra> obraList = obraRepository.findAll();
        assertThat(obraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnderecoIsRequired() throws Exception {
        int databaseSizeBeforeTest = obraRepository.findAll().size();
        // set the field null
        obra.setEndereco(null);

        // Create the Obra, which fails.
        ObraDTO obraDTO = obraMapper.toDto(obra);

        restObraMockMvc.perform(post("/api/obras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(obraDTO)))
            .andExpect(status().isBadRequest());

        List<Obra> obraList = obraRepository.findAll();
        assertThat(obraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = obraRepository.findAll().size();
        // set the field null
        obra.setStatus(null);

        // Create the Obra, which fails.
        ObraDTO obraDTO = obraMapper.toDto(obra);

        restObraMockMvc.perform(post("/api/obras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(obraDTO)))
            .andExpect(status().isBadRequest());

        List<Obra> obraList = obraRepository.findAll();
        assertThat(obraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataInicioIsRequired() throws Exception {
        int databaseSizeBeforeTest = obraRepository.findAll().size();
        // set the field null
        obra.setDataInicio(null);

        // Create the Obra, which fails.
        ObraDTO obraDTO = obraMapper.toDto(obra);

        restObraMockMvc.perform(post("/api/obras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(obraDTO)))
            .andExpect(status().isBadRequest());

        List<Obra> obraList = obraRepository.findAll();
        assertThat(obraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataFimIsRequired() throws Exception {
        int databaseSizeBeforeTest = obraRepository.findAll().size();
        // set the field null
        obra.setDataFim(null);

        // Create the Obra, which fails.
        ObraDTO obraDTO = obraMapper.toDto(obra);

        restObraMockMvc.perform(post("/api/obras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(obraDTO)))
            .andExpect(status().isBadRequest());

        List<Obra> obraList = obraRepository.findAll();
        assertThat(obraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllObras() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList
        restObraMockMvc.perform(get("/api/obras?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(obra.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].endereco").value(hasItem(DEFAULT_ENDERECO.toString())))
            .andExpect(jsonPath("$.[*].metragem").value(hasItem(DEFAULT_METRAGEM.intValue())))
            .andExpect(jsonPath("$.[*].valorTerreno").value(hasItem(DEFAULT_VALOR_TERRENO.intValue())))
            .andExpect(jsonPath("$.[*].valorEscritura").value(hasItem(DEFAULT_VALOR_ESCRITURA.intValue())))
            .andExpect(jsonPath("$.[*].valorEscrituraCompra").value(hasItem(DEFAULT_VALOR_ESCRITURA_COMPRA.intValue())))
            .andExpect(jsonPath("$.[*].valorCorretagemVenda").value(hasItem(DEFAULT_VALOR_CORRETAGEM_VENDA.intValue())))
            .andExpect(jsonPath("$.[*].valorFiscal").value(hasItem(DEFAULT_VALOR_FISCAL.intValue())))
            .andExpect(jsonPath("$.[*].porcentagemCorretagem").value(hasItem(DEFAULT_PORCENTAGEM_CORRETAGEM)))
            .andExpect(jsonPath("$.[*].tipoCorretagem").value(hasItem(DEFAULT_TIPO_CORRETAGEM.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].dataInicio").value(hasItem(DEFAULT_DATA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].dataFim").value(hasItem(DEFAULT_DATA_FIM.toString())));
    }
    
    @Test
    @Transactional
    public void getObra() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get the obra
        restObraMockMvc.perform(get("/api/obras/{id}", obra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(obra.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.endereco").value(DEFAULT_ENDERECO.toString()))
            .andExpect(jsonPath("$.metragem").value(DEFAULT_METRAGEM.intValue()))
            .andExpect(jsonPath("$.valorTerreno").value(DEFAULT_VALOR_TERRENO.intValue()))
            .andExpect(jsonPath("$.valorEscritura").value(DEFAULT_VALOR_ESCRITURA.intValue()))
            .andExpect(jsonPath("$.valorEscrituraCompra").value(DEFAULT_VALOR_ESCRITURA_COMPRA.intValue()))
            .andExpect(jsonPath("$.valorCorretagemVenda").value(DEFAULT_VALOR_CORRETAGEM_VENDA.intValue()))
            .andExpect(jsonPath("$.valorFiscal").value(DEFAULT_VALOR_FISCAL.intValue()))
            .andExpect(jsonPath("$.porcentagemCorretagem").value(DEFAULT_PORCENTAGEM_CORRETAGEM))
            .andExpect(jsonPath("$.tipoCorretagem").value(DEFAULT_TIPO_CORRETAGEM.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.dataInicio").value(DEFAULT_DATA_INICIO.toString()))
            .andExpect(jsonPath("$.dataFim").value(DEFAULT_DATA_FIM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingObra() throws Exception {
        // Get the obra
        restObraMockMvc.perform(get("/api/obras/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateObra() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        int databaseSizeBeforeUpdate = obraRepository.findAll().size();

        // Update the obra
        Obra updatedObra = obraRepository.findById(obra.getId()).get();
        // Disconnect from session so that the updates on updatedObra are not directly saved in db
        em.detach(updatedObra);
        updatedObra
            .nome(UPDATED_NOME)
            .endereco(UPDATED_ENDERECO)
            .metragem(UPDATED_METRAGEM)
            .valorTerreno(UPDATED_VALOR_TERRENO)
            .valorEscritura(UPDATED_VALOR_ESCRITURA)
            .valorEscrituraCompra(UPDATED_VALOR_ESCRITURA_COMPRA)
            .valorCorretagemVenda(UPDATED_VALOR_CORRETAGEM_VENDA)
            .valorFiscal(UPDATED_VALOR_FISCAL)
            .porcentagemCorretagem(UPDATED_PORCENTAGEM_CORRETAGEM)
            .tipoCorretagem(UPDATED_TIPO_CORRETAGEM)
            .status(UPDATED_STATUS)
            .dataInicio(UPDATED_DATA_INICIO)
            .dataFim(UPDATED_DATA_FIM);
        ObraDTO obraDTO = obraMapper.toDto(updatedObra);

        restObraMockMvc.perform(put("/api/obras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(obraDTO)))
            .andExpect(status().isOk());

        // Validate the Obra in the database
        List<Obra> obraList = obraRepository.findAll();
        assertThat(obraList).hasSize(databaseSizeBeforeUpdate);
        Obra testObra = obraList.get(obraList.size() - 1);
        assertThat(testObra.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testObra.getEndereco()).isEqualTo(UPDATED_ENDERECO);
        assertThat(testObra.getMetragem()).isEqualTo(UPDATED_METRAGEM);
        assertThat(testObra.getValorTerreno()).isEqualTo(UPDATED_VALOR_TERRENO);
        assertThat(testObra.getValorEscritura()).isEqualTo(UPDATED_VALOR_ESCRITURA);
        assertThat(testObra.getValorEscrituraCompra()).isEqualTo(UPDATED_VALOR_ESCRITURA_COMPRA);
        assertThat(testObra.getValorCorretagemVenda()).isEqualTo(UPDATED_VALOR_CORRETAGEM_VENDA);
        assertThat(testObra.getValorFiscal()).isEqualTo(UPDATED_VALOR_FISCAL);
        assertThat(testObra.getPorcentagemCorretagem()).isEqualTo(UPDATED_PORCENTAGEM_CORRETAGEM);
        assertThat(testObra.getTipoCorretagem()).isEqualTo(UPDATED_TIPO_CORRETAGEM);
        assertThat(testObra.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testObra.getDataInicio()).isEqualTo(UPDATED_DATA_INICIO);
        assertThat(testObra.getDataFim()).isEqualTo(UPDATED_DATA_FIM);
    }

    @Test
    @Transactional
    public void updateNonExistingObra() throws Exception {
        int databaseSizeBeforeUpdate = obraRepository.findAll().size();

        // Create the Obra
        ObraDTO obraDTO = obraMapper.toDto(obra);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restObraMockMvc.perform(put("/api/obras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(obraDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Obra in the database
        List<Obra> obraList = obraRepository.findAll();
        assertThat(obraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteObra() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        int databaseSizeBeforeDelete = obraRepository.findAll().size();

        // Get the obra
        restObraMockMvc.perform(delete("/api/obras/{id}", obra.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Obra> obraList = obraRepository.findAll();
        assertThat(obraList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Obra.class);
        Obra obra1 = new Obra();
        obra1.setId(1L);
        Obra obra2 = new Obra();
        obra2.setId(obra1.getId());
        assertThat(obra1).isEqualTo(obra2);
        obra2.setId(2L);
        assertThat(obra1).isNotEqualTo(obra2);
        obra1.setId(null);
        assertThat(obra1).isNotEqualTo(obra2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ObraDTO.class);
        ObraDTO obraDTO1 = new ObraDTO();
        obraDTO1.setId(1L);
        ObraDTO obraDTO2 = new ObraDTO();
        assertThat(obraDTO1).isNotEqualTo(obraDTO2);
        obraDTO2.setId(obraDTO1.getId());
        assertThat(obraDTO1).isEqualTo(obraDTO2);
        obraDTO2.setId(2L);
        assertThat(obraDTO1).isNotEqualTo(obraDTO2);
        obraDTO1.setId(null);
        assertThat(obraDTO1).isNotEqualTo(obraDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(obraMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(obraMapper.fromId(null)).isNull();
    }
}
