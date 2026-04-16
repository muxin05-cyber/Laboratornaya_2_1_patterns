import Missions_data.Mission;
import Parsers.Pair;
import Reports.Builders.ReportBuilder;
import Reports.Builders.ReportDirector;
import Reports.Builders.ReportComponent;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class ReportsManager {

    private String currentReportType;
    private HashMap<String, String> reportTypes;

    public ReportsManager() {
        this.reportTypes = getAvailableReportTypes();
        setReportBuilderByName("Brief");
    }

    public String setReportBuilderByName(String reportName) {
        String className = reportTypes.get(reportName);
        if (className == null) {
            return "Неизвестный тип отчёта0: " + reportName;
        }
        this.currentReportType = reportName;
        return null;
    }

    public Pair<String, String> getReport(Mission mission) {
        if (mission == null) {
            return new Pair<>(null, "Миссия не загружена");
        }

        if (currentReportType == null) {
            return new Pair<>(null, "Не выбран тип отчёта");
        }

        String className = reportTypes.get(currentReportType);
        if (className == null) {
            return new Pair<>(null, "Неизвестный тип отчёта1: " + currentReportType);
        }

        ReportDirector director = createDirector(mission, className);
        if (director == null) {
            return new Pair<>(null, "Ошибка создания директора");
        }

        ReportComponent report = buildReportByType(director, currentReportType);
        if (report == null) {
            return new Pair<>(null, "Неизвестный тип отчёта2: " + currentReportType);
        }

        return new Pair<>(report.build(), "");
    }

    private ReportDirector createDirector(Mission mission, String className) {
        try {
            Class<?> clazz = Class.forName(className);
            if (!ReportBuilder.class.isAssignableFrom(clazz)) {
                System.err.println("Класс " + className + " не реализует интерфейс ReportBuilder");
                return null;
            }
            java.lang.reflect.Constructor<?> constructor = clazz.getConstructor(Mission.class);
            ReportBuilder builder = (ReportBuilder) constructor.newInstance(mission);
            return new ReportDirector(builder);
        } catch (ClassNotFoundException e) {
            System.err.println("Класс не найден: " + className);
            return null;
        } catch (NoSuchMethodException e) {
            System.err.println("У класса " + className + " нет конструктора с параметром Mission");
            return null;
        } catch (Exception e) {
            System.err.println("Ошибка создания билдера: " + e.getMessage());
            return null;
        }
    }

    private ReportComponent buildReportByType(ReportDirector director, String reportType) {
        try {
            String methodName = "construct" + reportType.replace(" ", "") + "Report";
            Method method = ReportDirector.class.getMethod(methodName);
            return (ReportComponent) method.invoke(director);
        } catch (NoSuchMethodException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    private HashMap<String, String> getAvailableReportTypes() {
        HashMap<String, String> types = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/Reports/ReportTypes.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    types.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            types.put("Brief", "Reports.Builders.BriefReportBuilder");
            types.put("Detailed", "Reports.Builders.DetailedReportBuilder");
            types.put("Risk", "Reports.Builders.RiskReportBuilder");
            types.put("Statistics", "Reports.Builders.StatisticsReportBuilder");
        }
        return types;
    }

    public String[] getReportTypes() {
        return reportTypes.keySet().toArray(new String[0]);
    }
}