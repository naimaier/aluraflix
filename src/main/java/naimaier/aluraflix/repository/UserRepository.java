package naimaier.aluraflix.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import naimaier.aluraflix.model.User;

public interface UserRepository extends JpaRepository<User, String> {

}
