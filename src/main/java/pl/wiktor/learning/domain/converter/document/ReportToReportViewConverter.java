package pl.wiktor.learning.domain.converter.document;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.wiktor.learning.api.dto.response.document.ReportView;
import pl.wiktor.learning.domain.document.Report;

@Component
public class ReportToReportViewConverter implements Converter<Report, ReportView> {

    @Override
    public ReportView convert(Report report) {
        return new ReportView(
                report.getId(),
                report.getTitle(),
                report.getContent(),
                report.getCreatedAt()
        );
    }
}
