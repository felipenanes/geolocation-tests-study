package nl.felipenanes.geoloc;

import org.junit.jupiter.api.Test;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@ActiveProfiles("test")
class GeolocApplicationTest {

    @Test
    void contextLoads() {
        assertThat(GeolocApplication.class).isNotNull();
    }

    @Test
    void applicationStartsSuccessfully() {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(GeolocApplication.class);
        assertThat(builder).isNotNull();
        assertThat(builder.sources(GeolocApplication.class)).isNotNull();
    }

    @Test
    void mainMethodExists() {
        assertThat(GeolocApplication.class).hasDeclaredMethods("main");
    }

    @Test
    void mainMethodExecutesWithoutException() {
        String[] args = {"--spring.main.web-application-type=none", "--spring.autoconfigure.exclude=*"};

        assertThatCode(() -> GeolocApplication.main(args))
            .doesNotThrowAnyException();
    }
}
