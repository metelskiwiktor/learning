package pl.wiktor.learning.api;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.wiktor.learning.api.dto.request.document.CreateDocument;
import pl.wiktor.learning.api.dto.request.document.ReviewAnswer;
import pl.wiktor.learning.api.dto.request.document.UploadAnswer;
import pl.wiktor.learning.api.dto.request.document.UploadReport;
import pl.wiktor.learning.api.dto.response.document.LightDocumentView;
import pl.wiktor.learning.api.dto.response.document.DocumentView;
import pl.wiktor.learning.api.dto.response.document.ReportView;
import pl.wiktor.learning.api.dto.response.document.TimetableView;
import pl.wiktor.learning.domain.document.DocumentService;
//import pl.wiktor.learning.infrastructure.security.SecurityContextUtils;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@RestController
@RequestMapping("/document")
public class DocumentController {
    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PostMapping("/create")
    public DocumentView createDocument(@RequestBody CreateDocument createDocument){
        return documentService.createDocument(
                createDocument.getTitle(),
                createDocument.getFileId(),
                createDocument.getFromUserId(),
                createDocument.getToUserId(),
                createDocument.getStartAt(),
                createDocument.getEndAt()
        );
    }

    @PatchMapping("/{documentId}/answer")
    public void uploadAnswer(@PathVariable String documentId, @RequestBody UploadAnswer uploadAnswer){
        documentService.uploadAnswer(
                documentId,
                uploadAnswer.getFileId()
        );
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PatchMapping("/{answerId}/review")
    public void reviewAnswer(@PathVariable String answerId, @RequestBody ReviewAnswer reviewAnswer){
        documentService.reviewAnswer(
                answerId,
                reviewAnswer.getAdministrationNote(),
                reviewAnswer.getAnswerStatus()
        );
    }

    @PatchMapping("/{documentId}/report")
    public ReportView uploadReport(@PathVariable String documentId, @RequestBody UploadReport uploadReport){
        return documentService.uploadReport(
                documentId,
                uploadReport.getContent(),
                uploadReport.getTitle()
        );
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @GetMapping("/{documentId}/reports")
    public List<ReportView> getReports(@PathVariable String documentId){
        return documentService.getReports(documentId);
    }

    @GetMapping("/{documentId}")
    public DocumentView getDocument(@PathVariable String documentId){
        return documentService.getDocument(documentId);
    }

    @GetMapping("/light/all")
    public List<LightDocumentView> getLightDocuments(
            @RequestParam(value = "from-user", required = false) String fromUser,
            @RequestParam(value = "to-user", required = false) String toUser,
            @RequestParam(value = "from-date") @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate fromDate,
            @RequestParam(value = "to-date") @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate toDate){
        return documentService.getLightDocuments(fromUser, toUser, fromDate, toDate);
    }

    @GetMapping("/all")
    public List<DocumentView> getDocuments(
            @RequestParam(value = "from-user", required = false) String fromUser,
            @RequestParam(value = "to-user", required = false) String toUser,
            @RequestParam(value = "from-date") @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate fromDate,
            @RequestParam(value = "to-date") @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate toDate){
        return documentService.getAllDocuments(fromUser, toUser, fromDate, toDate);
    }

    @PostMapping("/timetable/{userId}")
    public TimetableView getTimetable(@PathVariable String userId, @RequestParam Month month, @RequestParam int year){
        return documentService.getTimetable(userId, month, year);
    }
}
