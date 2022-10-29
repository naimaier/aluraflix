package naimaier.aluraflix.controller.dto;

import org.springframework.data.domain.Page;

import naimaier.aluraflix.model.Video;

public class VideoDto {

	private Long id;
	private String titulo;
	private String descricao;
	private String url;
	
	public VideoDto(Video video) {
		this.id = video.getId();
		this.titulo = video.getTitulo();
		this.descricao = video.getDescricao();
		this.url = video.getUrl();
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

	public static Page<VideoDto> convert(Page<Video> videos) {
		return videos.map(VideoDto::new);
	}
}
