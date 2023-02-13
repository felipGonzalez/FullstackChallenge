package com.saludtools.fullstackchallenge.Repository;

import com.saludtools.fullstackchallenge.Domain.entity.Medicament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository(value = "medicamentRepository")
@Transactional
public interface MedicamentRepository extends JpaRepository<Medicament,Long> {

    List<Medicament> findTop20ByNameContains(String name);

    @Query("SELECT m FROM Medicament m " +
            "WHERE m.name LIKE :name")
    List<Medicament> findByTerm(
            @Param("name") String name);
}
