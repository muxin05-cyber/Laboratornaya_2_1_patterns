package defaultPackage.service;

import defaultPackage.Management.ParsersManagement;
import defaultPackage.Management.ReportsManager;
import defaultPackage.Missions_data.*;
import defaultPackage.repository.MissionRepository;
import defaultPackage.Parsers.Pair;
import defaultPackage.validator.MissionValidator;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class MissionService {
    private final MissionRepository missionRepository;
    private final ParsersManagement parsersManagement;
    private final ReportsManager reportsManager;
    private final MissionValidator validator;

    public MissionService(MissionRepository missionRepository) {
        this.missionRepository = missionRepository;
        this.parsersManagement = new ParsersManagement();
        this.reportsManager = new ReportsManager();
        this.validator = new MissionValidator();
    }

    public List<String> validateMission(MissionEntity mission) {
        return validator.validateWithWarnings(mission);
    }


    public MissionEntity saveMission(MissionEntity mission) {
        mission.setUpdatedAt(LocalDateTime.now());
        if (mission.getMissionId() != null && !mission.getMissionId().isBlank()) {
            Optional<MissionEntity> existing = missionRepository.findByMissionId(mission.getMissionId());
            if (existing.isPresent()) {
                MissionEntity ex = existing.get();
                updateEntity(ex, mission);
                ex.setUpdatedAt(LocalDateTime.now());
                return missionRepository.save(ex);
            }
        }
        return missionRepository.save(mission);
    }

    public MissionEntity importMission(MultipartFile file) throws IOException {
        String name = file.getOriginalFilename();
        String ext = "";
        if (name != null && name.contains(".")) ext = name.substring(name.lastIndexOf("."));
        Path temp = Files.createTempFile("mission_", ext);
        file.transferTo(temp.toFile());
        Pair<MissionEntity, String> result = parsersManagement.FileProcessing(temp.toString());
        Files.deleteIfExists(temp);
        if (result.getFirst() == null) throw new IOException(result.getSecond());
        return saveMission(result.getFirst());
    }

    @Transactional(readOnly = true)
    public List<MissionEntity> getAllMissions() {
        return missionRepository.findAllByOrderByUpdatedAtDesc();
    }

    @Transactional(readOnly = true)
    public MissionEntity getMissionById(String id) {
        return missionRepository.findByMissionId(id)
                .orElseThrow(() -> new RuntimeException("Миссия не найдена: " + id));
    }

    public void deleteMission(String id) {
        missionRepository.delete(getMissionById(id));
    }

    public String generateReport(String missionId, String reportType) {
        MissionEntity e = getMissionById(missionId);
        Hibernate.initialize(e.getSorcerers());
        Hibernate.initialize(e.getTechniques());
        Hibernate.initialize(e.getOperationTimeline());
        String error = reportsManager.setReportBuilderByName(reportType);
        if (error != null) throw new IllegalArgumentException(error);

        Pair<String, String> result = reportsManager.getReport(e);
        if (result.getFirst() == null) throw new RuntimeException(result.getSecond());

        return result.getFirst();
    }

    @Transactional(readOnly = true)
    public long getMissionCount() {
        return missionRepository.count();
    }

    private boolean isEmpty(String s) { return s == null || s.isBlank(); }

   /* private void updateEntity(MissionEntity target, MissionEntity source) {
        if (source.getDate() != null) target.setDate(source.getDate());
        if (source.getLocation() != null) target.setLocation(source.getLocation());
        if (source.getOutcome() != null) target.setOutcome(source.getOutcome());
        target.setDamageCost(source.getDamageCost());
        if (source.getNotes() != null) target.setNotes(source.getNotes());
        if (source.getComment() != null) target.setComment(source.getComment());
        if (source.getCurse() != null) target.setCurse(source.getCurse());
        if (source.getEconomicAssessment() != null) target.setEconomicAssessment(source.getEconomicAssessment());
        if (source.getEnemyActivity() != null) target.setEnemyActivity(source.getEnemyActivity());
        if (source.getEnvironment() != null) target.setEnvironment(source.getEnvironment());
        if (source.getCivilianImpact() != null) target.setCivilianImpact(source.getCivilianImpact());
        if (source.getOperationTags() != null) target.setOperationTags(source.getOperationTags());
        if (source.getSupportUnits() != null) target.setSupportUnits(source.getSupportUnits());
        if (source.getRecommendations() != null) target.setRecommendations(source.getRecommendations());
        if (source.getArtifactsRecovered() != null) target.setArtifactsRecovered(source.getArtifactsRecovered());
        if (source.getEvacuationZones() != null) target.setEvacuationZones(source.getEvacuationZones());
        if (source.getStatusEffects() != null) target.setStatusEffects(source.getStatusEffects());

        if (source.getSorcerers() != null) {
            List<SorcererEntity> newList = new ArrayList<>();
            source.getSorcerers().forEach(s -> { s.setMission(target); newList.add(s); });
            target.getSorcerers().clear();
            target.getSorcerers().addAll(newList);
        }
        if (source.getTechniques() != null) {
            List<TechniqueEntity> newList = new ArrayList<>();
            source.getTechniques().forEach(t -> { t.setMission(target); newList.add(t); });
            target.getTechniques().clear();
            target.getTechniques().addAll(newList);
        }
        if (source.getOperationTimeline() != null) {
            List<OperationEventEntity> newList = new ArrayList<>();
            source.getOperationTimeline().forEach(e -> { e.setMission(target); newList.add(e); });
            target.getOperationTimeline().clear();
            target.getOperationTimeline().addAll(newList);
        }
    }*/

    private void updateEntity(MissionEntity target, MissionEntity source) {
        target.setDate(source.getDate());
        target.setLocation(source.getLocation());
        target.setOutcome(source.getOutcome());
        target.setDamageCost(source.getDamageCost());
        target.setNotes(source.getNotes());            // затрёт, если null
        target.setComment(source.getComment());          // затрёт, если null
        target.setCurse(source.getCurse());              // затрёт, если null
        target.setEconomicAssessment(source.getEconomicAssessment());
        target.setEnemyActivity(source.getEnemyActivity());
        target.setEnvironment(source.getEnvironment());
        target.setCivilianImpact(source.getCivilianImpact());
        target.setOperationTags(source.getOperationTags());
        target.setSupportUnits(source.getSupportUnits());
        target.setRecommendations(source.getRecommendations());
        target.setArtifactsRecovered(source.getArtifactsRecovered());
        target.setEvacuationZones(source.getEvacuationZones());
        target.setStatusEffects(source.getStatusEffects());

        // Коллекции — только если не null
        if (source.getSorcerers() != null) {
            target.getSorcerers().clear();
            source.getSorcerers().forEach(s -> { s.setMission(target); target.getSorcerers().add(s); });
        }
        if (source.getTechniques() != null) {
            target.getTechniques().clear();
            source.getTechniques().forEach(t -> { t.setMission(target); target.getTechniques().add(t); });
        }
        if (source.getOperationTimeline() != null) {
            target.getOperationTimeline().clear();
            source.getOperationTimeline().forEach(e -> { e.setMission(target); target.getOperationTimeline().add(e); });
        }
    }
}