package br.slobra.aplicacao.web.rest;

import br.slobra.aplicacao.ObrasApp;

import br.slobra.aplicacao.domain.Obras;
import br.slobra.aplicacao.repository.ObrasRepository;
import br.slobra.aplicacao.service.ObrasService;
import br.slobra.aplicacao.service.dto.ObrasDTO;
import br.slobra.aplicacao.service.mapper.ObrasMapper;
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
 * Test class for the ObrasResource REST controller.
 *
 * @see ObrasResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ObrasApp.class)
public class ObrasResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_LOCAL = "AAAAAAAAAA";
    private static final String UPDATED_LOCAL = "BBBBBBBBBB";

    private static final Long DEFAULT_METRAGEM = 1L;
    private static final Long UPDATED_METRAGEM = 2L;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATA_INICIO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_INICIO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_FIM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_FIM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ObrasRepository obrasRepository;

    @Autowired
    private ObrasMapper obrasMapper;

    @Autowired
    private ObrasService obrasService;

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

    private MockMvc restObrasMockMvc;

    private Obras obras;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ObrasResource obrasResource = new ObrasResource(obrasService);
        this.restObrasMockMvc = MockMvcBuilders.standaloneSetup(obrasResource)
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
    public static Obras createEntity(EntityManager em) {
        Obras obras = new Obras()
            .nome(DEFAULT_NOME)
            .local(DEFAULT_LOCAL)
            .metragem(DEFAULT_METRAGEM)
            .status(DEFAULT_STATUS)
            .dataInicio(DEFAULT_DATA_INICIO)
            .dataFim(DEFAULT_DATA_FIM);
        return obras;
    }

    @Before
    public void initTest() {
        obras = createEntity(em);
    }

    @Test
    @Transactional
    public void createObras() throws Exception {
        int databaseSizeBeforeCreate = obrasRepository.findAll().size();

        // Create the Obras
        ObrasDTO obrasDTO = obrasMapper.toDto(obras);
        restObrasMockMvc.perform(post("/api/obras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(obrasDTO)))
            .andExpect(status().isCreated());

        // Validate the Obras in the database
        List<Obras> obrasList = obrasRepository.findAll();
        assertThat(obrasList).hasSize(databaseSizeBeforeCreate + 1);
        Obras testObras = obrasList.get(obrasList.size() - 1);
        assertThat(testObras.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testObras.getLocal()).isEqualTo(DEFAULT_LOCAL);
        assertThat(testObras.getMetragem()).isEqualTo(DEFAULT_METRAGEM);
        assertThat(testObras.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testObras.getDataInicio()).isEqualTo(DEFAULT_DATA_INICIO);
        assertThat(testObras.getDataFim()).isEqualTo(DEFAULT_DATA_FIM);
    }

    @Test
    @Transactional
    public void createObrasWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = obrasRepository.findAll().size();

        // Create the Obras with an existing ID
        obras.setId(1L);
        ObrasDTO obrasDTO = obrasMapper.toDto(obras);

        // An entity with an existing ID cannot be created, so this API call must fail
        restObrasMockMvc.perform(post("/api/obras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(obrasDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Obras in the database
        List<Obras> obrasList = obrasRepository.findAll();
        assertThat(obrasList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllObras() throws Exception {
        // Initialize the database
        obrasRepository.saveAndFlush(obras);

        // Get all the obrasList
        restObrasMockMvc.perform(get("/api/obras?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(obras.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].local").value(hasItem(DEFAULT_LOCAL.toString())))
            .andExpect(jsonPath("$.[*].metragem").value(hasItem(DEFAULT_METRAGEM.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].dataInicio").value(hasItem(DEFAULT_DATA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].dataFim").value(hasItem(DEFAULT_DATA_FIM.toString())));
    }
    
    @Test
    @Transactional
    public void getObras() throws Exception {
        // Initialize the database
        obrasRepository.saveAndFlush(obras);

        // Get the obras
        restObrasMockMvc.perform(get("/api/obras/{id}", obras.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(obras.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.local").value(DEFAULT_LOCAL.toString()))
            .andExpect(jsonPath("$.metragem").value(DEFAULT_METRAGEM.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.dataInicio").value(DEFAULT_DATA_INICIO.toString()))
            .andExpect(jsonPath("$.dataFim").value(DEFAULT_DATA_FIM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingObras() throws Exception {
        // Get the obras
        restObrasMockMvc.perform(get("/api/obras/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateObras() throws Exception {
        // Initialize the database
        obrasRepository.saveAndFlush(obras);

        int databaseSizeBeforeUpdate = obrasRepository.findAll().size();

        // Update the obras
        Obras updatedObras = obrasRepository.findById(obras.getId()).get();
        // Disconnect from session so that the updates on updatedObras are not directly saved in db
        em.detach(updatedObras);
        updatedObras
            .nome(UPDATED_NOME)
            .local(UPDATED_LOCAL)
            .metragem(UPDATED_METRAGEM)
            .status(UPDATED_STATUS)
            .dataInicio(UPDATED_DATA_INICIO)
            .dataFim(UPDATED_DATA_FIM);
        ObrasDTO obrasDTO = obrasMapper.toDto(updatedObras);

        restObrasMockMvc.perform(put("/api/obras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(obrasDTO)))
            .andExpect(status().isOk());

        // Validate the Obras in the database
        List<Obras> obrasList = obrasRepository.findAll();
        assertThat(obrasList).hasSize(databaseSizeBeforeUpdate);
        Obras testObras = obrasList.get(obrasList.size() - 1);
        assertThat(testObras.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testObras.getLocal()).isEqualTo(UPDATED_LOCAL);
        assertThat(testObras.getMetragem()).isEqualTo(UPDATED_METRAGEM);
        assertThat(testObras.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testObras.getDataInicio()).isEqualTo(UPDATED_DATA_INICIO);
        assertThat(testObras.getDataFim()).isEqualTo(UPDATED_DATA_FIM);
    }

    @Test
    @Transactional
    public void updateNonExistingObras() throws Exception {
        int databaseSizeBeforeUpdate = obrasRepository.findAll().size();

        // Create the Obras
        ObrasDTO obrasDTO = obrasMapper.toDto(obras);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restObrasMockMvc.perform(put("/api/obras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(obrasDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Obras in the database
        List<Obras> obrasList = obrasRepository.findAll();
        assertThat(obrasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteObras() throws Exception {
        // Initialize the database
        obrasRepository.saveAndFlush(obras);

        int databaseSizeBeforeDelete = obrasRepository.findAll().size();

        // Get the obras
        restObrasMockMvc.perform(delete("/api/obras/{id}", obras.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Obras> obrasList = obrasRepository.findAll();
        assertThat(obrasList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Obras.class);
        Obras obras1 = new Obras();
        obras1.setId(1L);
        Obras obras2 = new Obras();
        obras2.setId(obras1.getId());
        assertThat(obras1).isEqualTo(obras2);
        obras2.setId(2L);
        assertThat(obras1).isNotEqualTo(obras2);
        obras1.setId(null);
        assertThat(obras1).isNotEqualTo(obras2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ObrasDTO.class);
        ObrasDTO obrasDTO1 = new ObrasDTO();
        obrasDTO1.setId(1L);
        ObrasDTO obrasDTO2 = new ObrasDTO();
        assertThat(obrasDTO1).isNotEqualTo(obrasDTO2);
        obrasDTO2.setId(obrasDTO1.getId());
        assertThat(obrasDTO1).isEqualTo(obrasDTO2);
        obrasDTO2.setId(2L);
        assertThat(obrasDTO1).isNotEqualTo(obrasDTO2);
        obrasDTO1.setId(null);
        assertThat(obrasDTO1).isNotEqualTo(obrasDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(obrasMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(obrasMapper.fromId(null)).isNull();
    }
}
