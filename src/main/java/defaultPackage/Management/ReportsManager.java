package defaultPackage.Management;

import defaultPackage.Missions_data.MissionEntity;
import defaultPackage.Parsers.Pair;
import defaultPackage.Reports.Builders.ReportBuilder;
import defaultPackage.Reports.Builders.ReportDirector;
import defaultPackage.Reports.Builders.ReportComponent;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class ReportsManager {

    private String currentReportType;
    private final HashMap<String, String> reportTypes;

    public ReportsManager() {
        this.reportTypes = getAvailableReportTypes();
        setReportBuilderByName("Brief");
    }

    public String setReportBuilderByName(String reportName) {
        String className = reportTypes.get(reportName);
        if (className == null) {
            return "Неизвестный тип отчёта: " + reportName;
        }
        this.currentReportType = reportName;
        return null;
    }

    public Pair<String, String> getReport(MissionEntity mission) {
        if (mission == null) {
            return new Pair<>(null, "Миссия не загружена");
        }

        String className = reportTypes.get(currentReportType);
        if (className == null) {
            return new Pair<>(null, "Неизвестный тип отчёта: " + currentReportType);
        }
        Pair<ReportDirector, String> pair = createDirector(mission, className);
        if (pair.getFirst() == null) {
            return new Pair<>(null, pair.getSecond());
        }

        ReportComponent report = buildReportByType(pair.getFirst());
        if (report == null) {
            return new Pair<>(null, "Не удалось построить отчёт типа: " + currentReportType);
        }
        return new Pair<>(report.build(), "");
    }

    private Pair<ReportDirector, String> createDirector(MissionEntity mission, String className) {
        try {
            Class<?> clazz = Class.forName(className);
            if (!ReportBuilder.class.isAssignableFrom(clazz)) {
                return new Pair<>(null, "Класс " + className + " не реализует ReportBuilder");
            }
            ReportBuilder builder = (ReportBuilder) clazz.getConstructor(MissionEntity.class).newInstance(mission);
            return new Pair<>(new ReportDirector(builder), "");
        } catch (Exception e) {
            return new Pair<>(null, "Ошибка создания билдера: " + e.getMessage());
        }
    }

    private ReportComponent buildReportByType(ReportDirector director) {
        try {
            String methodName = "construct" + currentReportType + "Report";
            Method method = ReportDirector.class.getMethod(methodName);
            return (ReportComponent) method.invoke(director);
        } catch (Exception e) {
            return null;
        }
    }

    private HashMap<String, String> getAvailableReportTypes() {
        HashMap<String, String> types = new HashMap<>();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("ReportTypes.txt")) {
            if (is != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty()) continue;
                    String[] parts = line.split("=");
                    if (parts.length == 2) {
                        types.put(parts[0].trim(), parts[1].trim());
                    }
                }
            }
        } catch (Exception ignored) {}

        if (types.isEmpty()) {
            types.put("Brief", "defaultPackage.Reports.Builders.BriefReportBuilder");
            types.put("Detailed", "defaultPackage.Reports.Builders.DetailedReportBuilder");
            types.put("Risk", "defaultPackage.Reports.Builders.RiskReportBuilder");
            types.put("Statistics", "defaultPackage.Reports.Builders.StatisticsReportBuilder");
        }
        return types;
    }

    public String[] getReportTypes() {
        return reportTypes.keySet().toArray(new String[0]);
    }
}