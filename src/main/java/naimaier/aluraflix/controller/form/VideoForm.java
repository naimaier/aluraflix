package naimaier.aluraflix.controller.form;

import java.util.Optional;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.URL;

import naimaier.aluraflix.exception.CategoryNotFoundException;
import naimaier.aluraflix.model.Categoria;
import naimaier.aluraflix.model.Video;
import naimaier.aluraflix.repository.CategoriaRepository;

public class VideoForm {

	@NotEmpty
	private String titulo;
	@NotEmpty
	private String descricao;
	@NotEmpty
	@URL
	private String url;
	
	private Long categoriaId;
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Long getCategoriaId() {
		return categoriaId;
	}
	public void setCategoriaId(Long categoriaId) {
		this.categoriaId = categoriaId;
	}
	
	public Video toVideo(CategoriaRepository categoriaRepository) {
		Video video = new Video();
		
		video.setTitulo(titulo);
		video.setDescricao(descricao);
		video.setUrl(url);
		checkAndSetCategory(video, categoriaRepository);
		
		return video;
	}
	
	public Video update(Video video, CategoriaRepository categoriaRepository) {
		
		video.setTitulo(titulo);
		video.setDescricao(descricao);
		video.setUrl(url);
		checkAndSetCategory(video, categoriaRepository);
		
		return video;
	}
	
	private void checkAndSetCategory(Video video, 
			CategoriaRepository categoriaRepository) {
		
		if (categoriaId == null) {
			categoriaId = 1l;
		}
		
		Optional<Categoria> categoriaOptional = 
				categoriaRepository.findById(categoriaId);
		
		if (!categoriaOptional.isPresent()) {
			throw new CategoryNotFoundException();
		}
		
		video.setCategoria(categoriaOptional.get());
	}
}
