package Reports.Builders;

public class EmptyReport implements ReportComponent {

    @Override
    public String build() {
        return "";
    }
}