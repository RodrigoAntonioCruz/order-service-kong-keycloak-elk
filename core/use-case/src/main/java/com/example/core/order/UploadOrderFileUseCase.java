package com.example.core.order;

import com.example.core.FileOrderLine;
import com.example.core.ports.input.UploadOrderFileInputPort;
import com.example.core.ports.output.SendOrderOutputPort;
import com.example.core.utils.Constants;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.stream.Stream;

import static com.example.core.exception.ExceptionUtil.throwExceptionIf;

@Named
@ApplicationScoped
public class UploadOrderFileUseCase implements UploadOrderFileInputPort {
    private final SendOrderOutputPort sendOrderOutputPort;

    @Inject
    public UploadOrderFileUseCase(final SendOrderOutputPort sendOrderOutputPort) {
        this.sendOrderOutputPort = sendOrderOutputPort;
    }

    @Override
    public void upload(InputStream inputStream, String fileName) {
        throwExceptionIf(!fileName.endsWith(Constants.TXT),
                new IllegalArgumentException(Constants.INVALID_FILE_NAME));

        throwExceptionIf(Objects.isNull(inputStream),
                new IllegalArgumentException(Constants.NOT_NULL_FILE));

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            Stream<String> lines = reader.lines();

            lines.map(line -> {
                throwExceptionIf(Objects.isNull(line) || line.trim().isEmpty(),
                        new IllegalArgumentException(String.format(Constants.INVALID_LINE_FILE, line)));
                try {
                    return FileOrderLine.toFileOrderLine(line);
                } catch (Exception e) {
                    throw new IllegalArgumentException(String.format(Constants.ERROR_CONVERTER_LINE, line, e.getMessage()));
                }
            }).forEach(fileOrderLine -> {
                try {
                    sendOrderOutputPort.send(fileOrderLine);
                } catch (Exception e) {
                    throw new IllegalArgumentException(Constants.ERROR_SEND_FILE, e);
                }
            });

        } catch (IOException e) {
            throw new IllegalArgumentException(Constants.ERROR_READ_FILE, e);
        }
    }
}
