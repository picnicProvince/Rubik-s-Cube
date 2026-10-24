package ru.nsu.mkorovkina.rubics_cube.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.nsu.mkorovkina.rubics_cube.model.Competition;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface CompetitionRepository extends JpaRepository<Competition, String> {
    List<Competition> findByCountryAndFromGreaterThanEqualOrderByFromAsc(String country, LocalDate today);

    List<Competition> findByFromGreaterThanEqualOrderByFromAsc(LocalDate today);

    @Modifying
    @Query("DELETE FROM Competition c WHERE c.id NOT IN :ids")
    void deleteByIdNotIn(@Param("ids") Collection<String> ids);
}
