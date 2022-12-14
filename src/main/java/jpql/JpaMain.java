package jpql;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("관리자");
            member.setTeam(team);
            member.setAge(10);
            member.setType(MemberType.ADMIN);

            em.persist(member);

            em.flush();
            em.clear();

            List<String> resultList = em.createQuery("select nullif(m.username, '관리자') from Member m", String.class)
                    .getResultList();

            System.out.println("resultList = " + resultList);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
