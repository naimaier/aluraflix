package naimaier.aluraflix.controller.form;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;

class CategoriaFormTest {
	
	private final Validator validator = 
			Validation.buildDefaultValidatorFactory().getValidator();

	
	@Test
	void shouldReturnTwoViolationsWhenFormIsEmpty() {
		CategoriaForm form = new CategoriaForm();
		
		Set<ConstraintViolation<CategoriaForm>> violations = validator.validate(form);
		
		assertThat(violations.size()).isEqualTo(2);
	}
	
	
	@Test
	void shouldReturnViolationWhenTitleIsEmpty() {
		CategoriaForm form = new CategoriaForm();
		form.setCor("FFF");
		
		Set<ConstraintViolation<CategoriaForm>> violations = validator.validate(form);
		
		assertThat(violations.size()).isEqualTo(1);
	}
	
	
	@Test
	void shouldReturnViolationWhenColorIsEmpty() {
		CategoriaForm form = new CategoriaForm();
		form.setTitulo("Categoria");
		
		Set<ConstraintViolation<CategoriaForm>> violations = validator.validate(form);
		
		assertThat(violations.size()).isEqualTo(1);
	}
	
	
	@Test
	void shouldReturnNoViolationsWhenEverythingIsOk() {
		CategoriaForm form = new CategoriaForm();
		form.setTitulo("Categoria");
		form.setCor("FFF");
		
		Set<ConstraintViolation<CategoriaForm>> violations = validator.validate(form);
		
		assertThat(violations.size()).isZero();
	}

}
