package pl.wiktor.learning.domain.converter.file;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.wiktor.learning.api.dto.response.file.FileView;
import pl.wiktor.learning.domain.file.File;

@Component
public class FileToFileViewConverter implements Converter<File, FileView> {

    @Override
    public FileView convert(File file) {
        return new FileView(
                file.getId(),
                file.getFileName(),
                file.getFileType()
        );
    }
}
