package naimaier.aluraflix.controller.form;

import javax.validation.constraints.NotEmpty;

import naimaier.aluraflix.model.Categoria;

public class CategoriaForm {

	@NotEmpty(message = "O campo 'título' é obrigatório")
	private String titulo;
	@NotEmpty(message = "O campo 'cor' é obrigatório")
	private String cor;
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getCor() {
		return cor;
	}
	public void setCor(String cor) {
		this.cor = cor;
	}
	
	public Categoria toCategoria() {
		Categoria categoria = new Categoria();
		
		categoria.setTitulo(titulo);
		categoria.setCor(cor);
		
		return categoria;
	}
	
	public void update(Categoria categoria) {
		categoria.setTitulo(titulo);
		categoria.setCor(cor);
	}
}
