package naimaier.aluraflix.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import naimaier.aluraflix.controller.dto.CategoriaDto;
import naimaier.aluraflix.exception.CategoryNotFoundException;
import naimaier.aluraflix.model.Categoria;
import naimaier.aluraflix.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	
	@GetMapping
	public Page<CategoriaDto> getAll(Pageable pageable){
		
		Page<Categoria> categorias = categoriaRepository.findAll(pageable);
		
		return CategoriaDto.convert(categorias); 
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<CategoriaDto> getById(
			@PathVariable("id") Optional<Categoria> categoriaOptional){
		
		return categoriaOptional
				.map(categoria -> ResponseEntity.ok(new CategoriaDto(categoria)))
				.orElseThrow(CategoryNotFoundException::new);
	}
}
