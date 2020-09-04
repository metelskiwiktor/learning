package pl.wiktor.learning.api.dto.response.document;

import java.util.ArrayList;
import java.util.List;

public class TimetableView {
    private List<ThingsPerDay> thingsPerDays;
    private List<LightDocumentView> overdueDocuments;

    public TimetableView(List<ThingsPerDay> thingsPerDays, List<LightDocumentView> overdueDocuments) {
        this.thingsPerDays = thingsPerDays;
        this.overdueDocuments = overdueDocuments;
    }

    public void addThingPerDays(ThingsPerDay thingsPerDay){
        thingsPerDays.add(thingsPerDay);
    }

    public List<ThingsPerDay> getThingsPerDays() {
        return thingsPerDays;
    }

    public List<LightDocumentView> getOverdueDocuments() {
        return overdueDocuments;
    }
}
