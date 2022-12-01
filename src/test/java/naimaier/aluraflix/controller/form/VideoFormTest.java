package naimaier.aluraflix.controller.form;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.jdbc.Sql;

import naimaier.aluraflix.exception.CategoryNotFoundException;
import naimaier.aluraflix.model.Video;
import naimaier.aluraflix.repository.CategoriaRepository;

@DataJpaTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class VideoFormTest {
	
	@Autowired
	private CategoriaRepository repository;

	private final Validator validator = 
			Validation.buildDefaultValidatorFactory().getValidator();
	
	@Test
	void shouldReturnThreeViolationsWhenFormIsEmpty() {
		VideoForm form = new VideoForm();
		
		Set<ConstraintViolation<VideoForm>> violations = validator.validate(form);
		
		assertThat(violations.size()).isEqualTo(3);
	}
	
	
	@Test
	void shouldReturnViolationWhenTitleIsEmpty() {
		VideoForm form = new VideoForm();
		form.setDescricao("testing");
		form.setUrl("http://www.test.com");
		
		Set<ConstraintViolation<VideoForm>> violations = validator.validate(form);
		
		assertThat(violations.size()).isEqualTo(1);
	}
	
	
	@Test
	void shouldReturnViolationWhenDescriptionIsEmpty() {
		VideoForm form = new VideoForm();
		form.setTitulo("Testing");
		form.setUrl("http://www.test.com");
		
		Set<ConstraintViolation<VideoForm>> violations = validator.validate(form);
		
		assertThat(violations.size()).isEqualTo(1);
	}
	
	
	@Test
	void shouldReturnViolationWhenUrlIsEmpty() {
		VideoForm form = new VideoForm();
		form.setTitulo("Testing");
		form.setDescricao("testing");
		
		Set<ConstraintViolation<VideoForm>> violations = validator.validate(form);
		
		assertThat(violations.size()).isEqualTo(1);
	}
	
	
	@Test
	void shouldReturnViolationWhenUrlIsinvalid() {
		VideoForm form = new VideoForm();
		form.setTitulo("Testing");
		form.setDescricao("testing");
		form.setUrl("invalidUrl");
		
		Set<ConstraintViolation<VideoForm>> violations = validator.validate(form);
		
		assertThat(violations.size()).isEqualTo(1);
	}
	
	
	@Test
	void shouldReturnNoViolationWhenAllIsOk() {
		VideoForm form = new VideoForm();
		form.setTitulo("Testing");
		form.setDescricao("testing");
		form.setUrl("http://www.testing.com");
		
		Set<ConstraintViolation<VideoForm>> violations = validator.validate(form);
		
		assertThat(violations.size()).isZero();
	}
	
	
	@Test
	@Sql({"/test-data.sql"})
	void shouldAssignCorrectCategory() {
		Long categoryId = 2l;
		
		VideoForm form = new VideoForm();
		form.setTitulo("Testing");
		form.setDescricao("testing");
		form.setUrl("http://www.testing.com");
		form.setCategoriaId(categoryId);
		
		Video video = form.toVideo(repository);
		assertEquals(categoryId, video.getCategoria().getId());
	}
	
	
	@Test
	@Sql({"/test-data.sql"})
	void shouldAssignCategory1WhenNoCategoryIdIsSet() {
		VideoForm form = new VideoForm();
		form.setTitulo("Testing");
		form.setDescricao("testing");
		form.setUrl("http://www.testing.com");
		
		Video video = form.toVideo(repository);
		assertEquals(1l, video.getCategoria().getId());
	}
	
	
	@Test
	@Sql({"/test-data.sql"})
	void shouldThrowErrorWhenCategoryDoesNotExist() {
		Long categoryId = 5l;
		
		VideoForm form = new VideoForm();
		form.setTitulo("Testing");
		form.setDescricao("testing");
		form.setUrl("http://www.testing.com");
		form.setCategoriaId(categoryId);
		
		assertThrows(CategoryNotFoundException.class, 
				() -> form.toVideo(repository));
	}

}
