package defaultPackage.controller;

import defaultPackage.Missions_data.MissionEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import defaultPackage.service.MissionService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/missions")
@Tag(name = "Архив миссий", description = "API для работы с миссиями")
public class MissionController {

    private final MissionService missionService;

    public MissionController(MissionService missionService) {
        this.missionService = missionService;
    }

    @GetMapping
    @Operation(summary = "Получить список всех миссий")
    public List<MissionEntity> getAllMissions() {
        return missionService.getAllMissions();
    }

    @GetMapping("/{missionId}")
    @Operation(summary = "Получить миссию по ID")
    public ResponseEntity<?> getMission(@PathVariable String missionId) {
        try {
            return ResponseEntity.ok(missionService.getMissionById(missionId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping
    @Operation(summary = "Сохранить миссию (JSON/XML)")
    public ResponseEntity<?> createMission(@RequestBody MissionEntity mission) {
        try {
            return ResponseEntity.ok(missionService.saveMission(mission));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/validate")
    @Operation(summary = "Проверить миссию без сохранения")
    public ResponseEntity<?> validateMission(@RequestBody MissionEntity mission) {
        List<String> warnings = missionService.validateMission(mission);
        return ResponseEntity.ok(Map.of("warnings", warnings));
    }

    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Импортировать миссию из файла")
    public ResponseEntity<?> importMission(@RequestParam("file") MultipartFile file) {
        try {
            MissionEntity mission = missionService.importMission(file);
            List<String> warnings = missionService.validateMission(mission);
            if (warnings.isEmpty()) {
                return ResponseEntity.ok(Map.of("status", "ok", "mission", mission));
            }
            return ResponseEntity.ok(Map.of("status", "warning", "warnings", warnings, "mission", mission));
        } catch (IOException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("status", "error", "message", e.getMessage()));
        }
    }

    @DeleteMapping("/{missionId}")
    @Operation(summary = "Удалить миссию")
    public ResponseEntity<?> deleteMission(@PathVariable String missionId) {
        try {
            missionService.deleteMission(missionId);
            return ResponseEntity.ok(Map.of("status", "ok"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping(value = "/{missionId}/report", produces = MediaType.TEXT_PLAIN_VALUE)
    @Operation(summary = "Сгенерировать отчёт")
    public ResponseEntity<?> generateReport(
            @PathVariable String missionId,
            @RequestParam(defaultValue = "Brief") String reportType) {
        try {
            return ResponseEntity.ok(missionService.generateReport(missionId, reportType));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/{missionId}/report/download", produces = MediaType.TEXT_PLAIN_VALUE)
    @Operation(summary = "Скачать отчёт файлом")
    public ResponseEntity<byte[]> downloadReport(
            @PathVariable String missionId,
            @RequestParam(defaultValue = "Brief") String reportType) {
        String report = missionService.generateReport(missionId, reportType);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"mission_" + missionId + ".txt\"")
                .body(report.getBytes(StandardCharsets.UTF_8));
    }

    @GetMapping("/stats")
    @Operation(summary = "Статистика архива")
    public ResponseEntity<Map<String, Long>> getStats() {
        return ResponseEntity.ok(Map.of("totalMissions", missionService.getMissionCount()));
    }
}