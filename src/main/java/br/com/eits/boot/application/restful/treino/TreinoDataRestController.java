package br.com.eits.boot.application.restful.treino;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.eits.boot.domain.entity.academia.treino.TreinoData;
import br.com.eits.boot.domain.service.academia.treino.TreinoDataService;

@RequestMapping("/api/treino-datas")
@RestController
public class TreinoDataRestController {

	@Autowired
	private TreinoDataService treinoDataService;
	
	/**
	 * 
	 * retonra uma lista de treinos dos proximos dias
	 * 
	 * @param dataInicio
	 * @param idAluno
	 * @return
	 */
	@GetMapping(
		value = "/data-inicio/{dataInicio}/aluno/{idAluno}",
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Page<TreinoData>> listTreinoData( 
		@PathVariable @DateTimeFormat(iso = ISO.DATE) LocalDate dataInicio, 
		@PathVariable Long idAluno 
	){
	
		try {
			
			Sort sort = new Sort(Direction.ASC, "data");
			
			Page<TreinoData> treinosData = this.treinoDataService.listTreinoDataByFilters(
				dataInicio, 
				idAluno, 
				false,// somente completo
				PageRequest.of(0, 10, sort)
			);
			
			return ResponseEntity.ok().body(treinosData);
//			return new ResponseEntity<Page<TreinoData>>(treinosData, HttpStatus.OK);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			
		}
		
		
	}
	
	/**
	 * 
	 * Salva o treino realizado no dia 
	 * 
	 * @param treinoData
	 * @return
	 */
	@PostMapping(
		value = {"/", ""}
	)
	public ResponseEntity<TreinoData> salvarTreinoDoDia(
		@RequestBody TreinoData treinoData
	){
		
		try{
			
			treinoData =  this.treinoDataService.updateTreinoData(treinoData);
			
			return new ResponseEntity<TreinoData>(treinoData,HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>( HttpStatus.NOT_FOUND );
		}
		
		
	}
	
}
