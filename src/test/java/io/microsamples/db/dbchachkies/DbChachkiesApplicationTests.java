package io.microsamples.db.dbchachkies;

import oracle.jdbc.OracleTypes;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DbChachkiesApplicationTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private EasyRandom easyRandom;

    @BeforeEach
    void setUp(){
        EasyRandomParameters parameters = new EasyRandomParameters();
        parameters.stringLengthRange(5, 29);
        easyRandom = new EasyRandom(parameters);
    }

    @AfterEach
    void cleanUp(){
        SimpleJdbcCall jdbcCall = callFor("delete_chachkies");
        jdbcCall.execute();
    }

    @Test
	void shouldReturnAllChachkies(){
        final int expectedNumberOfChachkies = 13;
        easyRandom.objects(Chachkie.class, expectedNumberOfChachkies)
                .forEach(this::addChachkie);

        assertThat((List) allChachkies()).hasSize(expectedNumberOfChachkies);
    }



    @Test
    void shouldInsertOneChachkie() {
        final int expectedSize = 1;
        addChachkie(new Chachkie(expectedSize, "Applo", Date.from(Instant.now())));

        assertThat(allChachkies()).hasSize(expectedSize);
    }

    private void addChachkie(Chachkie chachkie) {
        SimpleJdbcCall jdbcCall = callFor("insert_chachkie");

        jdbcCall.addDeclaredParameter(new SqlParameter("id", OracleTypes.NUMBER));
        jdbcCall.addDeclaredParameter(new SqlParameter("name", OracleTypes.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("when", OracleTypes.TIMESTAMP));
        jdbcCall.execute(chachkie.getId(), chachkie.getName(), chachkie.getWhen());
    }

    private List<Chachkie> allChachkies() {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("test_user")
                .withProcedureName("all_chachkies")
                .returningResultSet("C_CURSOR", new ChachkieRowMapper());
        final Map<String, Object> execute = jdbcCall.execute();
        return (List<Chachkie>) execute.get("C_CURSOR");
    }

    private SimpleJdbcCall callFor(String procName){
        return new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("test_user")
                .withProcedureName(procName);
    }
}
