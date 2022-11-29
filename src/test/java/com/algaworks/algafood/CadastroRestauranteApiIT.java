package com.algaworks.algafood;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class CadastroRestauranteApiIT {

	private static final String VIOLACAO_DE_REGRA_DE_NEGOCIO_PROBLEM_TYPE = "Violação de regra de negócio";

	private static final String DADOS_INVALIDOS_PROBLEM_TITLE = "Dados inválidos";

	private static final int RESTAURANTE_ID_INEXISTENTE = 100;

	@LocalServerPort
	private int port;

	@Autowired
	private DatabaseCleaner databaseCleaner;

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private RestauranteRepository restauranteRepository;

	private String jsonRestauranteCorreto;
	private String jsonRestauranteSemFrete;
	private String jsonRestauranteSemCozinha;
	private String jsonRestauranteComCozinhaInexistente;

	private Restaurante restauranteAmericano;

	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/restaurantes";

		jsonRestauranteCorreto = ResourceUtils.getContentFromResource(
				"/json/correto/restaurante-chines.json");

		jsonRestauranteSemFrete = ResourceUtils.getContentFromResource(
				"/json/incorreto/restaurante-chines-sem-frete.json");

		jsonRestauranteSemCozinha = ResourceUtils.getContentFromResource(
				"/json/incorreto/restaurante-chines-sem-cozinha.json");

		jsonRestauranteComCozinhaInexistente = ResourceUtils.getContentFromResource(
				"/json/incorreto/restaurante-chines-com-cozinha-inexistente.json");

		databaseCleaner.clearTables();
		prepararDados();
	}

	// ############### TESTES DE API ###############

	@Test
	public void deveRetornarStatus200_QuandoConsultarRestaurantes() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deveRetornarStatus201_QuandoCadastarRestaurante() {
		given()
				.body(jsonRestauranteCorreto)
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}

	@Test
	public void deveRetornarStatus400_QuandoCadastrarRestauranteSemTaxaFrete() {
		given()
				.body(jsonRestauranteSemFrete)
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
			.when()
				.post()
			.then()
				.statusCode(HttpStatus.BAD_REQUEST.value())
				.body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TITLE));
	}

	@Test
	public void deveRetornarStatus400_QuandoCadastrarRestauranteSemCozinha() {
		given()
				.body(jsonRestauranteSemCozinha)
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
			.when()
				.post()
			.then()
				.statusCode(HttpStatus.BAD_REQUEST.value())
				.body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TITLE));
	}

	@Test
	public void deveRetornarStatus400_QuandoCadastrarRestauranteComCozinhaInexistente() {
		given()
				.body(jsonRestauranteComCozinhaInexistente)
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
			.when()
				.post()
			.then()
				.statusCode(HttpStatus.BAD_REQUEST.value())
				.body("title", equalTo(VIOLACAO_DE_REGRA_DE_NEGOCIO_PROBLEM_TYPE));
	}

	// GET /cozinhas/{id}
	@Test
	public void deveRetornarRespostaEStatusCorretos_QuandoConsultarRestauranteExistente() {
		given()
				.pathParam("id", restauranteAmericano.getId())
				.accept(ContentType.JSON)
			.when()
				.get("/{id}")
			.then()
				.statusCode(HttpStatus.OK.value())
				.body("nome", equalTo(restauranteAmericano.getNome()));
	}

	@Test
	public void deveRetornarStatus404_QuandoConsultarCozinhaInexistente() {
		given()
				.pathParam("id", RESTAURANTE_ID_INEXISTENTE)
				.accept(ContentType.JSON)
			.when()
				.get("/{id}")
			.then()
				.statusCode(HttpStatus.NOT_FOUND.value());
	}

	private void prepararDados() {
		Cozinha cozinhaTailandesa = new Cozinha();
		cozinhaTailandesa.setNome("Tailandesa");
		cozinhaRepository.save(cozinhaTailandesa);

		Cozinha cozinhaAmericana = new Cozinha();
		cozinhaAmericana.setNome("Americana");
		cozinhaRepository.save(cozinhaAmericana);

		Restaurante restauranteTailandes = new Restaurante();
		restauranteTailandes.setNome("Restaurante Tailandes");
		restauranteTailandes.setTaxaFrete(new BigDecimal(5));
		restauranteTailandes.setCozinha(cozinhaTailandesa);
		restauranteRepository.save(restauranteTailandes);

		restauranteAmericano = new Restaurante();
		restauranteAmericano.setNome("Restaurante Americano");
		restauranteAmericano.setTaxaFrete(new BigDecimal(3));
		restauranteAmericano.setCozinha(cozinhaAmericana);
		restauranteRepository.save(restauranteAmericano);

	}
}
