package naimaier.aluraflix.controller.form;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.URL;

import naimaier.aluraflix.model.Video;

public class VideoForm {

	@NotEmpty
	private String titulo;
	@NotEmpty
	private String descricao;
	@NotEmpty
	@URL
	private String url;
	
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
	
	public Video toVideo() {
		Video video = new Video();
		
		video.setTitulo(titulo);
		video.setDescricao(descricao);
		video.setUrl(url);
		
		return video;
	}
	
	public Video update(Video video) {
		
		video.setTitulo(titulo);
		video.setDescricao(descricao);
		video.setUrl(url);
		
		return video;
	}
}
