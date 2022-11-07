package naimaier.aluraflix.controller.dto;

import org.springframework.data.domain.Page;

import naimaier.aluraflix.model.Video;

public class VideoDto {

	private Long id;
	private Long categoriaId;
	private String titulo;
	private String descricao;
	private String url;
	
	public VideoDto(Video video) {
		this.id = video.getId();
		this.titulo = video.getTitulo();
		this.descricao = video.getDescricao();
		this.url = video.getUrl();
		this.categoriaId = video.getCategoria().getId();
	}

	public Long getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getUrl() {
		return url;
	}

	public Long getCategoriaId() {
		return categoriaId;
	}

	public static Page<VideoDto> convert(Page<Video> videos) {
		return videos.map(VideoDto::new);
	}
}
