package com.b2012202.mxhtknt.Initial;

import com.b2012202.mxhtknt.Repositories.LauRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class SequenceInitializer {

//    @PersistenceUnit
//    private EntityManagerFactory entityManagerFactory;
//
//
//    @Transactional
//    @PostConstruct
//    public void initializeSequence() {
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        try {
//            Object result = entityManager.createNativeQuery("SELECT 1 FROM INFORMATION_SCHEMA.SEQUENCES WHERE SEQUENCE_NAME = 'lau_sequence'").getSingleResult();
//            if (result != null) {
//                // Sequence tồn tại
//                System.out.println("~~~>Sequence đã tồn tại");
//            } else {
//                // Sequence không tồn tại, thực hiện tạo mới
//                System.out.println("~~~>Sequence chưa tồn tại, thực hiện tạo mới");
//                entityManager.createNativeQuery("CREATE SEQUENCE lau_sequence START WITH 1").executeUpdate();
//            }
//        } catch (NoResultException e) {
//            // Sequence không tồn tại, thực hiện tạo mới
//            System.out.println("~~~> Catch <NoResultException> : Sequence chưa tồn tại, thực hiện tạo mới");
//            entityManager.createNativeQuery("CREATE SEQUENCE lau_sequence START WITH 1").executeUpdate();
//        } catch (Exception e) {
//            System.out.println("~~~FAILED " + e.getMessage());
//        } finally {
//            entityManager.close();
//        }
//    }
}