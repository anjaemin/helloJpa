package hellojpa;

import hellojpa.entity.Member;
import hellojpa.entity.MemberType;
import hellojpa.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) {
//        System.out.println(MemberType.ADMIN);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
//        member.setId(100L);
            member.setName("hello");
            member.setAge(20);
            member.setMemberType(MemberType.ADMIN);
            member.setRegTime(new Date());
            member.setTeam(team);
            em.persist(member);

            em.flush(); //DB로 쿼리전송
            em.clear(); //캐시제거


            // 데이터 중심의 모델링 - 객체를 테이블에 맞추었기때문에 객체간에 연관관계가 없다
            /*Member findMember = em.find(Member.class, member.getId());
            Long teamId = findMember.getTeamId();

            Team findTeam = em.find(Team.class, teamId);*/

            Member findMember = em.find(Member.class, member.getId());
            Team findTeam = findMember.getTeam();

            List<Member> members = findTeam.getMembers();
            for(Member m : members) {
                System.out.println(m);
            }

            tx.commit();
        }catch(Exception e) {
            tx.rollback();
            e.printStackTrace();
        }finally {
            em.close();
            emf.close();
        }
    }
}
