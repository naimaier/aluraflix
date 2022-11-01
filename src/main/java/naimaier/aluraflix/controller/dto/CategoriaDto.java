package naimaier.aluraflix.controller.dto;

import org.springframework.data.domain.Page;

import naimaier.aluraflix.model.Categoria;

public class CategoriaDto {

	private Long id;
	private String nome;
	
	public CategoriaDto(Categoria categoria) {
		this.id = categoria.getId();
		this.nome = categoria.getNome();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public static Page<CategoriaDto> convert(Page<Categoria> categorias) {
		return categorias.map(CategoriaDto::new);
	}
}
