package pl.wiktor.learning.domain.file;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.wiktor.learning.api.dto.response.file.FileView;
import pl.wiktor.learning.api.exception.DomainException;
import pl.wiktor.learning.api.exception.ExceptionCode;

@Service
public class FileService {
    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public FileView upload(MultipartFile multipartFile){
        File file;
        try {
            file = new File(
                    multipartFile.getName(),
                    multipartFile.getContentType(),
                    multipartFile.getBytes()
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new DomainException(ExceptionCode.FILE_PROCESS_FAILED, multipartFile.getName());
        }

        fileRepository.save(file);

        return new FileView(
                file.getId(),
                file.getFileName(),
                file.getFileType()
        );
    }

    public File getFileById(String id) {
        return fileRepository.findById(id)
                .orElseThrow(() -> new DomainException(ExceptionCode.RESOURCE_NOT_FOUND, ExceptionCode.Resource.FILE.getPolish(), id));
    }
}
