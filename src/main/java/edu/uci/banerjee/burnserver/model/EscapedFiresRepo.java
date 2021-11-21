package edu.uci.banerjee.burnserver.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin(origins = "*")
@RepositoryRestResource(collectionResourceRel = "escapedfires", path = "escapes")
public interface EscapedFiresRepo  extends JpaRepository<EscapedFire, Integer> {

    @Query(
            "SELECT f FROM EscapedFire f WHERE (:source is null or f.source = :source) and (:counties is null or f.counties = :counties) and"
                    + "(:minAcres is null or f.acres >= :minAcres) and (:maxAcres is null or f.acres < :maxAcres) and "
                    + "(:startYear is null or f.year >= :startYear) and (:countyUnitID is null or f.countyUnitID = :countyUnitID) and"
                    + "(:endYear is null or f.year >= :endYear) and (:startMonth is null or f.month >= :startMonth) and "
                    + "(:treatmentType is null or f.treatmentType = :treatmentType) and (:endMonth is null or f.month >= :endMonth) and (:owner is null or f.owner = :owner)")
    List<EscapedFire> findByAllParams(
            @Param("source") String source,
            @Param("counties") String counties,
            @Param("countyUnitID") String countyUnitID,
            @Param("minAcres") Double minAcres,
            @Param("maxAcres") Double maxAcres,
            @Param("treatmentType") String treatmentType,
            @Param("startYear") Integer startYear,
            @Param("endYear") Integer endYear,
            @Param("startMonth") Integer startMonth,
            @Param("endMonth") Integer endMonth,
            @Param("owner") String owner);
}
