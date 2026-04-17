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
        if (value != null && !value.isEmpty()) {
            return value;
        } else {
            return "не указано";
        }
    }

    protected String safeNumber(int value) {
        if (value > 0) {
            return String.valueOf(value);
        } else {
            return "не указан";
        }
    }
}