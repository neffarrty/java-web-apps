import org.hibernate.Session;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import se.semit.ykovtun.webappskyvlab2.hibernate.HibernateUtil;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class HibernateUtilTest {
    private static Session session = null;

    @BeforeAll
    public static void setUp() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    @Test
    public void connectionTest() {
        assertNotNull(session);
    }

    @AfterAll
    public static void tearDown() {
        HibernateUtil.shutdown();
        session.close();
    }
}
