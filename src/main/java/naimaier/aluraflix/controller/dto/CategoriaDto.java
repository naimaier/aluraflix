package naimaier.aluraflix.controller.dto;

import org.springframework.data.domain.Page;

import naimaier.aluraflix.model.Categoria;

public class CategoriaDto {

	private Long id;
	private String titulo;
	private String cor;
	
	public CategoriaDto(Categoria categoria) {
		this.id = categoria.getId();
		this.titulo = categoria.getTitulo();
		this.cor = categoria.getCor();
	}
	
	public Long getId() {
		return id;
	}
	public String getTitulo() {
		return titulo;
	}
	public String getCor() {
		return cor;
	}
	public static Page<CategoriaDto> convert(Page<Categoria> categorias) {
		return categorias.map(CategoriaDto::new);
	}
}
