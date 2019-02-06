package br.slobra.aplicacao.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.slobra.aplicacao.service.GastoService;
import br.slobra.aplicacao.service.ObraService;
import br.slobra.aplicacao.service.ResumoGastoService;
import br.slobra.aplicacao.web.rest.errors.BadRequestAlertException;
import br.slobra.aplicacao.web.rest.util.HeaderUtil;
import br.slobra.aplicacao.web.rest.util.PaginationUtil;
import br.slobra.aplicacao.service.dto.GastoDTO;
import br.slobra.aplicacao.service.dto.ObraDTO;
import br.slobra.aplicacao.service.dto.ResumoContaDTO;
import br.slobra.aplicacao.service.dto.ResumoGastoDTO;
import br.slobra.aplicacao.service.mapper.GastoMapper;
import br.slobra.aplicacao.service.dto.MesAnoDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.math.BigDecimal;

import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Locale;
import java.util.Date;
import java.util.Optional;
import java.util.Collections;
import java.util.Map;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import br.slobra.aplicacao.domain.enumeration.NotaFiscal;
import br.slobra.aplicacao.domain.enumeration.TipoConta;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.time.format.DateTimeFormatter;

import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;


/**
 * REST controller for managing Gasto.
 */
@RestController
@RequestMapping("/api")
public class GastoResource {

    private final Logger log = LoggerFactory.getLogger(GastoResource.class);

    private static final String ENTITY_NAME = "gasto";

    private final GastoService gastoService;

    @Autowired
    ResumoGastoService resumoGastoService;

    @Autowired
    ObraService obraService;

    public GastoResource(GastoService gastoService) {
        this.gastoService = gastoService;
    }

    /**
     * POST  /gastos : Create a new gasto.
     *
     * @param gastoDTO the gastoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new gastoDTO, or with status 400 (Bad Request) if the gasto has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/gastos")
    @Timed
    public ResponseEntity<GastoDTO> createGasto(@Valid @RequestBody GastoDTO gastoDTO) throws URISyntaxException {
        log.debug("REST request to save Gasto : {}", gastoDTO);
        if (gastoDTO.getId() != null) {
            throw new BadRequestAlertException("A new gasto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GastoDTO result = gastoService.save(gastoDTO);
        //Regra de inserir os calculos no resumo da obra
        ResumoGastoDTO resumoGastoDTO = new ResumoGastoDTO();
        ObraDTO obra = obraService.findOne(result.getObraId()).get();
        resumoGastoDTO.setNomeObra(obra.getNome());
        List<GastoDTO> lista = gastoService.findByObra(result.getObraId());
        BigDecimal valorDeposito = lista.stream().filter(i -> i.getTipo().equals(TipoConta.INVESTIMENTO_DEPOSITO)).map(GastoDTO::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
        resumoGastoDTO.setValorDeposito(valorDeposito);
        BigDecimal valorDespesa = lista.stream().filter(i -> ! i.getTipo().equals(TipoConta.INVESTIMENTO_DEPOSITO)).map(GastoDTO::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
        resumoGastoDTO.setValorDespesa(valorDespesa);
        BigDecimal valorSaldo = valorDeposito.subtract(valorDespesa);
        resumoGastoDTO.setValorSaldo(valorSaldo);

        ResumoGastoDTO  resumoGastoDTO1 = resumoGastoService.findByObra(result.getObraId());

        if(resumoGastoDTO1==null) {//insert
            resumoGastoDTO.setIdObra(result.getObraId());
        	ResumoGastoDTO result2 = resumoGastoService.save(resumoGastoDTO);
        } else {//update
            resumoGastoDTO1.setValorDeposito(valorDeposito);
            resumoGastoDTO1.setValorDespesa(valorDespesa);
        	BigDecimal valorSaldo1 = valorDeposito.subtract(valorDespesa);
            resumoGastoDTO1.setValorSaldo(valorSaldo1);
            ResumoGastoDTO result2 = resumoGastoService.save(resumoGastoDTO1);
        }



        return ResponseEntity.created(new URI("/api/gastos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /gastos : Updates an existing gasto.
     *
     * @param gastoDTO the gastoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated gastoDTO,
     * or with status 400 (Bad Request) if the gastoDTO is not valid,
     * or with status 500 (Internal Server Error) if the gastoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/gastos")
    @Timed
    public ResponseEntity<GastoDTO> updateGasto(@Valid @RequestBody GastoDTO gastoDTO) throws URISyntaxException {
        log.debug("REST request to update Gasto : {}", gastoDTO);
        if (gastoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GastoDTO result = gastoService.save(gastoDTO);

        //Regra de atualizar os calculos no resumo da obra
        ResumoGastoDTO resumoGastoDTO = new ResumoGastoDTO();
        ObraDTO obra = obraService.findOne(result.getObraId()).get();
        resumoGastoDTO.setNomeObra(obra.getNome());
        List<GastoDTO> lista = gastoService.findByObra(result.getObraId());
        BigDecimal valorDeposito = lista.stream().filter(i -> i.getTipo().equals(TipoConta.INVESTIMENTO_DEPOSITO)).map(GastoDTO::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
        resumoGastoDTO.setValorDeposito(valorDeposito);
        BigDecimal valorDespesa = lista.stream().filter(i -> ! i.getTipo().equals(TipoConta.INVESTIMENTO_DEPOSITO)).map(GastoDTO::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
        resumoGastoDTO.setValorDespesa(valorDespesa);
        BigDecimal valorSaldo = valorDeposito.subtract(valorDespesa);
        resumoGastoDTO.setValorSaldo(valorSaldo);

        ResumoGastoDTO  resumoGastoDTO1 = resumoGastoService.findByObra(result.getObraId());

        if(resumoGastoDTO1==null) {//insert
            resumoGastoDTO.setIdObra(result.getObraId());
            ResumoGastoDTO result2 = resumoGastoService.save(resumoGastoDTO);
        } else {//update
            resumoGastoDTO1.setValorDeposito(valorDeposito);
            resumoGastoDTO1.setValorDespesa(valorDespesa);
            BigDecimal valorSaldo1 = valorDeposito.subtract(valorDespesa);
            resumoGastoDTO1.setValorSaldo(valorSaldo1);
            ResumoGastoDTO result2 = resumoGastoService.save(resumoGastoDTO1);
        }


        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, gastoDTO.getId().toString()))
            .body(result);
    }


    /**
     *
     * @param pageable
     * @return
     */
    @GetMapping("/graficoResumoConta")
    @Timed
    public ResponseEntity<ResumoContaDTO> graficoResumoConta(Pageable pageable) {
        log.debug("Request to get all Gastos");


        Page<GastoDTO> invoiceList = gastoService.findAll(pageable);

        BigDecimal semNota = invoiceList.stream().filter(i -> i.getNota().equals(NotaFiscal.NAO)).map(GastoDTO::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal comNota = invoiceList.stream().filter(i -> i.getNota().equals(NotaFiscal.SIM)).map(GastoDTO::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);

        long countSemNota = invoiceList.stream().filter(i -> i.getNota().equals(NotaFiscal.NAO)).count();
        long countComNota = invoiceList.stream().filter(i -> i.getNota().equals(NotaFiscal.SIM)).count();

        BigDecimal valorDeposito = invoiceList.stream().filter(i -> i.getTipo().equals(TipoConta.INVESTIMENTO_DEPOSITO)).map(GastoDTO::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal total = invoiceList.stream().map(GastoDTO::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);

        ResumoContaDTO dto = new ResumoContaDTO();
        dto.setDespesaSemNota(semNota);
        dto.setDespesaComNota(comNota);
        dto.setHonorarioAdministracao(new BigDecimal("100"));
        //dto.setQuantidadeComNota(countComNota);
        //dto.setQuantidadeSemNota(countSemNota);
        dto.setValorDeposito(valorDeposito);
        dto.setDespesaGeralSubTotal(total);


        return ResponseUtil.wrapOrNotFound(Optional.of(dto));
    }



    @GetMapping("/resumoConta")
    @Timed
    public ResponseEntity<ResumoContaDTO> getResumoConta(Pageable pageable,@RequestParam Map<String, String> parameters) {
        log.debug("getResumoConta");

        Page<GastoDTO> invoiceList = gastoService.findAll(pageable);

        if(parameters.get("data")!=null) {
            SimpleDateFormat formato = new SimpleDateFormat("MMM/yyyy",new Locale("pt", "br"));
            try {
                Date date = formato.parse(parameters.get("data"));
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(date);
                int ano = calendar.get(Calendar.YEAR);
                int mes = calendar.get(Calendar.MONTH)+1;

                invoiceList = gastoService.findByAnoMes(ano,mes,pageable);

                log.debug("REST invoiceList"+invoiceList);
            }
            catch (Exception e) {
                //The handling for the code
            }
        }



        BigDecimal semNota = invoiceList.stream().filter(i -> i.getNota().equals(NotaFiscal.NAO)).map(GastoDTO::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal comNota = invoiceList.stream().filter(i -> i.getNota().equals(NotaFiscal.SIM)).map(GastoDTO::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);

        long countSemNota = invoiceList.stream().filter(i -> i.getNota().equals(NotaFiscal.NAO)).count();
        long countComNota = invoiceList.stream().filter(i -> i.getNota().equals(NotaFiscal.SIM)).count();

        BigDecimal valorDeposito = invoiceList.stream().filter(i -> i.getTipo().equals(TipoConta.INVESTIMENTO_DEPOSITO)).map(GastoDTO::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal total = invoiceList.stream().map(GastoDTO::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);

        ResumoContaDTO dto = new ResumoContaDTO();
        dto.setDespesaSemNota(semNota);
        dto.setDespesaComNota(comNota);
        dto.setQuantidadeComNota(countComNota);
        dto.setQuantidadeSemNota(countSemNota);
        dto.setValorDeposito(valorDeposito);
        dto.setDespesaGeralSubTotal(total);

        List<GastoDTO> listData = invoiceList.getContent();
        if(!listData.isEmpty()) {
            dto.setMesAno(listData.get(0).getMesAno());
        }


        return ResponseUtil.wrapOrNotFound(Optional.of(dto));
    }


    @GetMapping("/gastoMesAno")
    @Timed
    public ResponseEntity<List<MesAnoDTO>> getMesAno() {
        List<MesAnoDTO> lista = getListaMesAno();
		return ResponseEntity.ok().body(lista);
    }



    @GetMapping("/graficoGastoObra")
    @Timed
    public ResponseEntity<List<ResumoContaDTO>> graficoGastoObra(Pageable pageable) {

    	List<ResumoContaDTO> listaResumo = new ArrayList<>();
        List<MesAnoDTO> lista = getListaMesAno();
        List<GastoDTO> listGasto = gastoService.findAll(pageable).getContent();

        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("MMM/yyyy").withLocale(new Locale("pt", "br"));
        for(MesAnoDTO mesAno : lista) {
        	ResumoContaDTO contaDTO = new ResumoContaDTO();
            BigDecimal valorTotal = listGasto.stream().filter(i -> i.getMesAno().format(formatador).equals(mesAno.getData())).map(GastoDTO::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
            System.out.println("valorTotal "+valorTotal);
            System.out.println("mesAno.getData()"+mesAno.getData());
            contaDTO.setTotalDespesas(valorTotal);
            contaDTO.setDespesaGeralSubTotal(valorTotal);
            contaDTO.setMesAnoFormatado(mesAno.getData());
           listaResumo.add(contaDTO);
        }

		return ResponseEntity.ok().body(listaResumo);
    }


    /**
     * GET  /gastos : get all the gastos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of gastos in body
     */
    @GetMapping("/gastos")
    @Timed
    public ResponseEntity<List<GastoDTO>> getAllGastos(Pageable pageable, @RequestParam Map<String, String> parameters) {
        log.debug("REST request to get a page of Gastos");
        Page<GastoDTO> page = gastoService.findAll(pageable);
       if(parameters.get("data")!=null) {
	        SimpleDateFormat formato = new SimpleDateFormat("MMM/yyyy",new Locale("pt", "br"));
           try {
               Date date = formato.parse(parameters.get("data"));
               Calendar calendar = new GregorianCalendar();
               calendar.setTime(date);
               int ano = calendar.get(Calendar.YEAR);
               int mes = calendar.get(Calendar.MONTH)+1;

               page = gastoService.findByAnoMes(ano,mes,pageable);

               log.debug("REST request to get a page of Gastos");
           }
           catch (Exception e) {
               //The handling for the code
           }
        }

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/gastos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /gastos/:id : get the "id" gasto.
     *
     * @param id the id of the gastoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the gastoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/gastos/{id}")
    @Timed
    public ResponseEntity<GastoDTO> getGasto(@PathVariable Long id) {
        log.debug("REST request to get Gasto : {}", id);
        Optional<GastoDTO> gastoDTO = gastoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gastoDTO);
    }

    /**
     * DELETE  /gastos/:id : delete the "id" gasto.
     *
     * @param id the id of the gastoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/gastos/{id}")
    @Timed
    public ResponseEntity<Void> deleteGasto(@PathVariable Long id) {
        log.debug("REST request to delete Gasto : {}", id);

        GastoDTO result = gastoService.findOne(id).get();
        gastoService.delete(id);

        //Regra de atualizar os calculos no resumo da obra
        ResumoGastoDTO resumoGastoDTO = new ResumoGastoDTO();
        ObraDTO obra = obraService.findOne(result.getObraId()).get();
        resumoGastoDTO.setNomeObra(obra.getNome());
        List<GastoDTO> lista = gastoService.findByObra(result.getObraId());
        BigDecimal valorDeposito = lista.stream().filter(i -> i.getTipo().equals(TipoConta.INVESTIMENTO_DEPOSITO)).map(GastoDTO::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
        resumoGastoDTO.setValorDeposito(valorDeposito);
        BigDecimal valorDespesa = lista.stream().filter(i -> ! i.getTipo().equals(TipoConta.INVESTIMENTO_DEPOSITO)).map(GastoDTO::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
        resumoGastoDTO.setValorDespesa(valorDespesa);
        BigDecimal valorSaldo = valorDeposito.subtract(valorDespesa);
        resumoGastoDTO.setValorSaldo(valorSaldo);

        ResumoGastoDTO  resumoGastoDTO1 = resumoGastoService.findByObra(result.getObraId());

        if(resumoGastoDTO1!=null) {//update
            resumoGastoDTO1.setValorDeposito(valorDeposito);
            resumoGastoDTO1.setValorDespesa(valorDespesa);
            BigDecimal valorSaldo1 = valorDeposito.subtract(valorDespesa);
            resumoGastoDTO1.setValorSaldo(valorSaldo1);
            ResumoGastoDTO result2 = resumoGastoService.save(resumoGastoDTO1);
        }



        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }


	private List<MesAnoDTO> getListaMesAno() {

		SimpleDateFormat formato = new SimpleDateFormat("MMM/yyyy",new Locale("pt", "br"));
		Calendar dia25 = Calendar.getInstance();

		List<MesAnoDTO> lista = new ArrayList<>();
		MesAnoDTO dto1=new MesAnoDTO();
		dto1.setData(formato.format(dia25.getTime()));
		lista.add(dto1);

		for(int x=0;x<10;x++) {
			dia25.add(Calendar.MONTH, -1);
			MesAnoDTO dto= new MesAnoDTO();
			dto.setData(formato.format(dia25.getTime()));
			lista.add(dto);
		}
        log.debug("Lista : {}", lista );
        Collections.reverse(lista);
        log.debug("reverse : {}", lista );

		//lista = lista.stream().sorted(Comparator.comparing(MesAnoDTO::getData).reversed()).collect(Collectors.toList());
		return lista;
	}
}
