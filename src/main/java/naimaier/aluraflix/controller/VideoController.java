package naimaier.aluraflix.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import naimaier.aluraflix.controller.dto.VideoDto;
import naimaier.aluraflix.controller.form.VideoForm;
import naimaier.aluraflix.model.Video;
import naimaier.aluraflix.repository.VideoRepository;

@RestController
@RequestMapping("/videos")
public class VideoController {
	
	@Autowired
	private VideoRepository videoRepository;

	
	@GetMapping
	public List<VideoDto> getAll() {
		return VideoDto.convert(videoRepository.findAll());
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<VideoDto> getById(@PathVariable Long id) {
		Optional<Video> videoOptional = videoRepository.findById(id);
		
		return videoOptional
				.map(video -> ResponseEntity.ok(new VideoDto(video)))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não encontrado"));
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
			@PathVariable Long id,
			@Valid @RequestBody VideoForm videoForm) {
		
		Optional<Video> videoOptional = videoRepository.findById(id);
		
		return videoOptional
				.map(video -> {
					Video updatedVideo = videoForm.update(id, videoRepository);
					return ResponseEntity.ok(new VideoDto(updatedVideo));
				})
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não encontrado"));
	}
	
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> delete(@PathVariable Long id){
		
		Optional<Video> videoOptional = videoRepository.findById(id);
		
		return videoOptional
				.map(video -> {
					videoRepository.deleteById(id);
					return ResponseEntity.ok().build();
				})
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não encontrado"));
	}
}
