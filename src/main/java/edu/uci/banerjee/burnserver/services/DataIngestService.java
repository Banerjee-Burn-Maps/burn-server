package edu.uci.banerjee.burnserver.services;

import com.univocity.parsers.common.record.Record;
import edu.uci.banerjee.burnserver.model.EscapedFire;
import edu.uci.banerjee.burnserver.model.EscapedFiresRepo;
import edu.uci.banerjee.burnserver.model.Fire;
import edu.uci.banerjee.burnserver.model.FiresRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

import static java.util.stream.Collectors.toUnmodifiableList;

@Service
@Slf4j
public class DataIngestService {
  private final FiresRepo repo;
  private final EscapedFiresRepo escapesRepo;
  private final LandOwnershipService landOwnershipService;

  public DataIngestService(FiresRepo repo,EscapedFiresRepo escapesRepo, LandOwnershipService landOwnershipService) {
    this.repo = repo;
    this.escapesRepo = escapesRepo;
    this.landOwnershipService = landOwnershipService;
  }

  public int saveFires(List<Record> records) {
    log.debug("Saving new fires records.");
    final var burns = records.parallelStream().map(this::createFire).collect(toUnmodifiableList());
    repo.saveAll(burns);

    return burns.size();
  }

  public int saveEscapes(List<Record> records) {
    log.debug("Saving new escaped fires records.");
    final var escapedBurns = records.parallelStream().map(this::createEscape).collect(toUnmodifiableList());
    escapesRepo.saveAll(escapedBurns);

    return escapedBurns.size();
  }

  private Fire createFire(final Record fireRecord) {
    log.debug("Ingesting record {}", fireRecord);

    final var fire = new Fire();
    fire.setName(fireRecord.getString("name"));
    fire.setAcres(Double.parseDouble(fireRecord.getString("acres")));
    fire.setLatitude(Double.parseDouble(fireRecord.getString("latitude")));
    fire.setLongitude(Double.parseDouble(fireRecord.getString("longitude")));
    fire.setBurnType(fireRecord.getString("burn_type"));
    fire.setCounty(fireRecord.getString("county"));
    fire.setSource(fireRecord.getString("source"));
    fire.setOwner(
        landOwnershipService.getOwnershipFromCoordinate(fire.getLatitude(), fire.getLongitude()));
    try {
      final var fireDate = Calendar.getInstance();
      fireDate.setTime(new SimpleDateFormat("MM/dd/yyyy").parse(fireRecord.getString("date")));
      fire.setYear(Integer.parseInt(fireRecord.getString("year")));
      fire.setMonth(fireDate.get(Calendar.MONTH));
      fire.setDay(fireDate.get(Calendar.DAY_OF_MONTH));
    } catch (ParseException e) {
      log.warn("Date is Invalid.");
    }

    return fire;
  }

  private EscapedFire createEscape(final Record escapeRecord) {
    log.debug("Ingesting escape record {}", escapeRecord);

    final var escapeFire = new EscapedFire();
    escapeFire.setName(escapeRecord.getString("Name"));
    escapeFire.setAcres(Double.parseDouble(escapeRecord.getString("GIS_ACRES")));
    escapeFire.setLatitude(Double.parseDouble(escapeRecord.getString("lat")));
    escapeFire.setLongitude(Double.parseDouble(escapeRecord.getString("lon")));
    escapeFire.setTreatmentType(escapeRecord.getString("TreatmentType"));
    escapeFire.setCounties(escapeRecord.getString("Counties"));
    escapeFire.setCountyUnitID(escapeRecord.getString("CountyUNIT_ID"));
    escapeFire.setSource(escapeRecord.getString("Source"));
    escapeFire.setOwner(
            landOwnershipService.getOwnershipFromCoordinate(escapeFire.getLatitude(), escapeFire.getLongitude()));
    try {
      final var escapeDate = Calendar.getInstance();
      escapeDate.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(escapeRecord.getString("Date")));
      escapeFire.setYear(Integer.parseInt(escapeRecord.getString("Year")));
      escapeFire.setMonth(escapeDate.get(Calendar.MONTH));
      escapeFire.setDay(escapeDate.get(Calendar.DAY_OF_MONTH));
    } catch (ParseException e) {
      log.warn("Date is Invalid.");
    }

    return escapeFire;
  }

}
