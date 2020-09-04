package pl.wiktor.learning.api.dto.response.file;

public class FileView {
    private String id;
    private String fileName;
    private String fileType;

    public FileView(String id, String fileName, String fileType) {
        this.id = id;
        this.fileName = fileName;
        this.fileType = fileType;
    }

    public FileView() {
    }

    public String getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileType() {
        return fileType;
    }
}
