package de.thaso.demo.payara.example.person.database;

import de.thaso.demo.payara.example.person.database.exception.DatabaseError;
import de.thaso.demo.payara.example.person.database.exception.DatabaseException;
import de.thaso.demo.payara.example.person.database.util.DatabaseExceptionCodeMatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@ExtendWith(MockitoExtension.class)
public class DemoDAOTest {

    public static final DatabaseExceptionCodeMatcher EXCEPTION_MATCHER_ENTITY_NOT_FOUND
            = new DatabaseExceptionCodeMatcher(DatabaseError.ENTITY_NOT_FOUND);

    @InjectMocks
    private DemoDAO underTest;

    @Mock
    private EntityManager entityManager;

    private Long primaryKey;
    private DemoEntity demoEntity;

    @BeforeEach
    public void setUp() {
        openMocks(this);

        primaryKey = 1L;
        demoEntity = new DemoEntity();
    }

    @Test
    public void storeDemo() {
        // when
        final DemoEntity result = underTest.storeDemo(demoEntity);
        // then
        verify(entityManager).persist(demoEntity);

        assertThat(result, is(demoEntity));
    }

    @Test
    public void findDemo() {
        // given
        when(entityManager.find(DemoEntity.class, primaryKey)).thenReturn(demoEntity);
        // when
        final DemoEntity result = underTest.findDemoById(primaryKey);
        // then
        assertThat(result, is(demoEntity));
    }

    @Test
    public void findDemo_whenPrimaryKeyNotFound() {
        // given
        when(entityManager.find(DemoEntity.class, primaryKey)).thenReturn(null);
        // when
        final DemoEntity result = underTest.findDemoById(primaryKey);
        // then
        assertThat(result, is(nullValue()));
    }

    @Test
    public void loadDemo() {
        // given
        when(entityManager.find(DemoEntity.class, primaryKey)).thenReturn(demoEntity);
        // when
        final DemoEntity result = underTest.loadDemoById(primaryKey);
        // then
        assertThat(result, is(demoEntity));
    }

    @Test
    public void loadDemo_whenPrimaryKeyNotFound() {
        // given
        when(entityManager.find(DemoEntity.class, primaryKey)).thenReturn(null);
        // when
        Throwable exception = assertThrows(DatabaseException.class, () -> {
            underTest.loadDemoById(primaryKey);
        });
        // then
        assertThat(exception.getMessage(), containsString(" " + primaryKey.toString() + " "));
        //exception.expect(EXCEPTION_MATCHER_ENTITY_NOT_FOUND);
    }

    @Test
    public void findOpenDemos() {
        // given
        final TypedQuery query = mock(TypedQuery.class);
        when(entityManager.createNamedQuery(DemoEntity.FIND_ACTIVE_DEMOS,DemoEntity.class)).thenReturn(query);
        final List<DemoEntity> demoEntityList = new ArrayList<>();
        when(query.getResultList()).thenReturn(demoEntityList);
        // when
        final List<DemoEntity> result = underTest.findActiveDemos();
        // then
        assertThat(result,is(demoEntityList));
    }
}
