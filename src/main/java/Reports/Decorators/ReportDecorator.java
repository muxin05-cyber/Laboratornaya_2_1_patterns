package Reports.Decorators;

import Reports.Builders.ReportComponent;


public abstract class ReportDecorator implements ReportComponent {

    protected ReportComponent wrapped;

    public ReportDecorator(ReportComponent wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public String build() {
        return wrapped.build();
    }

    protected String safe(String value) {
        return value != null && !value.isEmpty() ? value : "не указано";
    }

    protected String safeNumber(int value) {
        return value > 0 ? String.valueOf(value) : "не указан";
    }
}