package de.thaso.demo.payara.example.person.database;

import de.thaso.demo.payara.example.person.database.exception.DatabaseError;
import de.thaso.demo.payara.example.person.database.exception.DatabaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class DemoDAO {
        private final static Logger LOG = LogManager.getLogger(DemoDAO.class);

        @Inject
        private EntityManager entityManager;

        public DemoEntity storeDemo(final DemoEntity demoEntity) {
            LOG.info("storeDemo with id {}", demoEntity.getId());

            entityManager.persist(demoEntity);
            return demoEntity;
        }

        public DemoEntity findDemoById(final Long id) {
            LOG.info("findDemoById {}", id);

            final DemoEntity demoEntity = entityManager.find(DemoEntity.class, id);
            return demoEntity;
        }

        public DemoEntity loadDemoById(final Long id) throws DatabaseException {
            LOG.info("loadDemoById {}", id);

            final DemoEntity demoEntity = entityManager.find(DemoEntity.class, id);
            if(demoEntity == null) {
                throw new DatabaseException(DatabaseError.ENTITY_NOT_FOUND, "Demo with id " + id + " not found!");
            }
            return demoEntity;
        }

        public List<DemoEntity> findActiveDemos() {
            LOG.info("findOpenDemos");

            final TypedQuery typedQuery
                    = entityManager.createNamedQuery(DemoEntity.FIND_ACTIVE_DEMOS, DemoEntity.class);
            return typedQuery.getResultList();
        }
    }
