import config.HibernateConfig;
import org.hibernate.Session;

public class HibernateTest {
    public static void main(String[] args) {
        try (Session session = HibernateConfig.getSession()) {
            System.out.println("Hibernate SessionFactory successfully configured!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
