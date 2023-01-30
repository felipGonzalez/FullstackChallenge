package com.saludtools.fullstackchallengehexagonalarchitecture.infrastracture.repository.medicamentRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringDataMedicamentRepository extends JpaRepository<MedicamentEntity,Long> {

    List<MedicamentEntity> findTop20ByNameContains(String name);

    @Query("SELECT m FROM MedicamentEntity m " +
            "WHERE m.name LIKE :name")
    List<MedicamentEntity> findByTerm(
            @Param("name") String name);
}
