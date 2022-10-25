package naimaier.aluraflix.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import naimaier.aluraflix.model.Video;

public interface VideoRepository extends JpaRepository<Video, Long>{

}
