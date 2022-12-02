package naimaier.aluraflix.controller;

import java.net.URI;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import naimaier.aluraflix.controller.dto.CategoriaDto;
import naimaier.aluraflix.controller.dto.VideoDto;
import naimaier.aluraflix.controller.form.CategoriaForm;
import naimaier.aluraflix.exception.CategoryNotEditableException;
import naimaier.aluraflix.exception.CategoryNotEmptyException;
import naimaier.aluraflix.exception.CategoryNotFoundException;
import naimaier.aluraflix.model.Categoria;
import naimaier.aluraflix.model.Video;
import naimaier.aluraflix.repository.CategoriaRepository;
import naimaier.aluraflix.repository.VideoRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private VideoRepository videoRepository;
	
	
	@PostConstruct
	public void init() {
		if (categoriaRepository.count() != 0) return;
		
		Categoria categoria = new Categoria();
		categoria.setTitulo("Livre");
		categoria.setCor("#FFF");
		
		categoriaRepository.save(categoria);
	}
	
	
	@GetMapping
	public Page<CategoriaDto> getAll(
			@PageableDefault(size=5) Pageable pageable){
		
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
	
	
	@PostMapping
	@Transactional
	public ResponseEntity<CategoriaDto> save(
			@Valid @RequestBody CategoriaForm categoriaForm,
			UriComponentsBuilder uriBuilder) {
	
		Categoria savedItem = categoriaRepository
								.save(categoriaForm.toCategoria());
		
		URI uri = uriBuilder
					.path("/categorias/{id}")
					.buildAndExpand(savedItem.getId())
					.toUri();
		
		return ResponseEntity
				.created(uri)
				.body(new CategoriaDto(savedItem));
	}
	
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<CategoriaDto> update(
			@PathVariable("id") Optional<Categoria> categoriaOptional,
			@Valid @RequestBody CategoriaForm categoriaForm){
		
		return categoriaOptional
				.map(categoria -> {
					if (categoria.getId() == 1l) throw new CategoryNotEditableException();
					
					categoriaForm.update(categoria);
					return ResponseEntity.ok(new CategoriaDto(categoria));
				})
				.orElseThrow(CategoryNotFoundException::new);
	}
	
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> delete(
			@PathVariable("id") Optional<Categoria> categoriaOptional) {
		
		return categoriaOptional
				.map(categoria -> {
					if (categoria.getId() == 1l) throw new CategoryNotEditableException();
					if (videoRepository.existsByCategoria(categoria)) throw new CategoryNotEmptyException();
					
					categoriaRepository.deleteById(categoria.getId());
					return ResponseEntity.noContent().build();
				})
				.orElseThrow(CategoryNotFoundException::new);
	}
	
	
	@GetMapping("/{id}/videos")
	public Page<VideoDto> getByCategory(
			@PathVariable("id") Optional<Categoria> categoriaOptional,
			Pageable pageable){
		
		return categoriaOptional
				.map(categoria -> { 
					Page<Video> categorias = videoRepository.findByCategoria(categoria, pageable);
					return VideoDto.convert(categorias);
				})
				.orElseThrow(CategoryNotFoundException::new);
		
	}
}
