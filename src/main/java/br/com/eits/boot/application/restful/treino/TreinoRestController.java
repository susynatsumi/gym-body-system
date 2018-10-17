package br.com.eits.boot.application.restful.treino;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.eits.boot.domain.entity.academia.treino.Treino;
import br.com.eits.boot.domain.service.academia.treino.TreinoService;

@RestController
@RequestMapping("/api/treinos")
public class TreinoRestController {

	@Autowired
	private TreinoService treinoService;
	
	
	@SuppressWarnings("rawtypes")
	@GetMapping(
		value = {
			"/data-inicio/{dataInicio}/data-termino/{dataTermino}/aluno/{idAluno}/somente-completos/{somenteCompletos}/",
		}
	)
	public ResponseEntity listTreinoData( 
		@PathVariable @DateTimeFormat(iso = ISO.DATE) LocalDate dataInicio, 
		@PathVariable @DateTimeFormat(iso = ISO.DATE) LocalDate dataTermino, 
		@PathVariable Boolean somenteCompletos,
		@PathVariable Long idAluno 
	){
	
		try {
	
			List<Sort.Order> orders = Arrays
					.asList(
						new Sort.Order(Direction.DESC, "treinoData.data"),
						new Sort.Order(Direction.ASC, "nome")
					);
			
			Page<Treino> treinos = this.treinoService.listTreinosComDatasByFilters(
				dataInicio, 
				dataTermino, 
				idAluno, 
				somenteCompletos, 
				PageRequest.of(0, 10, Sort.by(orders))
			);
			
			return new ResponseEntity<Page<Treino>>(treinos, HttpStatus.OK); 
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
			
		}
		
		
	}
	
	/**
	 * 
	 * retonra o histórico de treinos
	 * 
	 * @param dataInicio
	 * @param idAluno
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping(
		value = {
			"/data-inicio/{dataInicio}/data-termino/{dataTermino}/aluno/{idAluno}/"
		}
	)
	public ResponseEntity listTreinoDataHistorico( 
		@PathVariable @DateTimeFormat(iso = ISO.DATE) LocalDate dataInicio, 
		@PathVariable @DateTimeFormat(iso = ISO.DATE) LocalDate dataTermino, 
		@PathVariable Long idAluno 
	){
	
		try {
			
			String[] order = new String[]{
				"treinoData.data",
				"nome"
			};
			
			
			Page<Treino> treinos = this.treinoService.listTreinoDataHistoricoByFilters(
				dataInicio, 
				dataTermino,
				idAluno,
				PageRequest.of(0, 10, Direction.ASC, order)
			);

			return new ResponseEntity<Page<Treino>>(treinos, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
	}
	
}
