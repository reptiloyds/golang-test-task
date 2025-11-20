package io.github.testwork;

import io.github.testwork.template.PostgresTestTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class TestworkApplicationTests extends PostgresTestTemplate {

	@Test
	void contextLoads() {
	}

}
