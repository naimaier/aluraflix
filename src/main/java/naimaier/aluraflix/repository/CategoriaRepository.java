package naimaier.aluraflix.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import naimaier.aluraflix.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

}
