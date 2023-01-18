package de.thaso.demo.payara.example.person.database;

import de.thaso.demo.payara.example.person.database.util.DbTestBase;
import de.thaso.demo.payara.example.person.database.util.PropertiesManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.MockitoAnnotations.openMocks;

@ExtendWith(MockitoExtension.class)
public class DemoDAOIT extends DbTestBase {
    public static final Logger LOG = LogManager.getLogger(DemoDAOIT.class);
    private static final Properties properties = PropertiesManager.readDevelopProperties();

    @InjectMocks
    private DemoDAO underTest;

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void storeDemo() throws SQLException {

        final DemoEntity demoEntity = new DemoEntity();
        demoEntity.setId(35L);
        demoEntity.setName("Ralf, Rabe");

        DemoEntity result = underTest.storeDemo(demoEntity);

        assertThat(result.getName(), is("Ralf, Rabe"));

        entityManager.flush();

        final ResultSet resultSet = getConnection().prepareStatement("SELECT COUNT(*) FROM T_DEMO WHERE ID = 35").executeQuery();
        resultSet.next();
        assertThat(resultSet.getInt(1),is(1));
    }

    @Test
    public void findActiveDemo() throws SQLException {

        final DemoEntity demoEntity = new DemoEntity();
        demoEntity.setId(655L);
        demoEntity.setName("Ralf, Rabe");

        underTest.storeDemo(demoEntity);

        final DemoEntity demoEntity2 = new DemoEntity();
        demoEntity2.setId(621L);
        demoEntity2.setName("Habicht, Horst");

        underTest.storeDemo(demoEntity2);

        List<DemoEntity> result = underTest.findActiveDemos();

        assertThat(result.size(), is(2));

        entityManager.flush();
        final ResultSet resultSet = getConnection().prepareStatement("SELECT COUNT(*) FROM T_DEMO WHERE ID > 600").executeQuery();
        resultSet.next();
        assertThat(resultSet.getInt(1),is(2));
    }
}
