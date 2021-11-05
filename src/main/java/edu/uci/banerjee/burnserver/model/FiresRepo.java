package edu.uci.banerjee.burnserver.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*")
@RepositoryRestResource(collectionResourceRel = "fires", path = "fires")
public interface FiresRepo extends JpaRepository<Fire, Integer> {
  List<Fire> findBySource(String source);

  List<Fire> findByCounty(String county);

  List<Fire> findByBurnType(String burnType);

  List<Fire> findByYear(int year);

  List<Fire> findByYearIsBetween(int fromYear, int toYear);

  List<Fire> findByAcres(double acres);

  List<Fire> findByAcresIsBetween(double min, double max);

  List<Fire> findByDateIsBetween(Date fromDate, Date toDate);

  List<Fire> findByOwner(String owner);

  List<Fire> findByIntensityBetween(double min, double max);

  // todo this needs some work done to how the start and end months were implemented. We need to
  // revisit the ux of that filter
  @Query(
      "SELECT f FROM Fire f WHERE (:source is null or f.source = :source) and (:county is null or f.county = :county) and"
          + "(:minAcres is null or f.acres >= :minAcres) and (:maxAcres is null or f.acres < :maxAcres) and "
          + "(:burnType is null or f.burnType = :burnType) and (:startYear is null or f.date >= :startYear) and "
          + "(:endYear is null or f.date >= :endYear) and (:owner is null or f.owner = :owner)")
  List<Fire> findByAllParams(
      @Param("source") String source,
      @Param("county") String county,
      @Param("minAcres") Double minAcres,
      @Param("maxAcres") Double maxAcres,
      @Param("burnType") String burnType,
      @Param("startYear") Date startYear,
      @Param("endYear") Date endYear,
      @Param("owner") String owner);
}
