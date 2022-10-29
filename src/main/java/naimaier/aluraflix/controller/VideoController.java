package naimaier.aluraflix.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import naimaier.aluraflix.controller.dto.VideoDto;
import naimaier.aluraflix.controller.form.VideoForm;
import naimaier.aluraflix.exception.VideoNotFoundException;
import naimaier.aluraflix.model.Video;
import naimaier.aluraflix.repository.VideoRepository;

@RestController
@RequestMapping("/videos")
public class VideoController {
	
	@Autowired
	private VideoRepository videoRepository;

	
	@GetMapping
	public Page<VideoDto> getAll(Pageable pageable) {
		
		Page<Video> videos = videoRepository.findAll(pageable);
		
		return VideoDto.convert(videos);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<VideoDto> getById(
			@PathVariable("id") Optional<Video> videoOptional) {
		
		return videoOptional
				.map(video -> ResponseEntity.ok(new VideoDto(video)))
				.orElseThrow(VideoNotFoundException::new);
	}
	
	
	@PostMapping
	@Transactional
	public ResponseEntity<VideoDto> save(
			@Valid @RequestBody VideoForm videoForm, 
			UriComponentsBuilder uriBuilder) {
		
		Video video = videoRepository.save(videoForm.toVideo());
		
		URI uri = uriBuilder
				.path("/videos/{id}")
				.buildAndExpand(video.getId())
				.toUri();
		
		return ResponseEntity
				.created(uri)
				.body(new VideoDto(video));
	}
	
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<VideoDto> update(
			@PathVariable("id") Optional<Video> videoOptional,
			@Valid @RequestBody VideoForm videoForm) {
		
		return videoOptional
				.map(video -> {
					Video updatedVideo = videoForm.update(video);
					return ResponseEntity.ok(new VideoDto(updatedVideo));
				})
				.orElseThrow(VideoNotFoundException::new);
	}
	
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> delete(
			@PathVariable("id") Optional<Video> videoOptional){
		
		return videoOptional
				.map(video -> {
					videoRepository.deleteById(video.getId());
					return ResponseEntity.noContent().build();
				})
				.orElseThrow(VideoNotFoundException::new);
	}
}
