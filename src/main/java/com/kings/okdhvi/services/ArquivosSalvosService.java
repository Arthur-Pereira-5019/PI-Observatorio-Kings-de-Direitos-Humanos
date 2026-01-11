package com.kings.okdhvi.services;

import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

public class ArquivosSalvosService {
    @Value("file_saving.base_path")
    private String basePath;

    @Value("file_saving.allowed-mime")
    private List<String> mimes;

    private final Path rootPath;

    public ArquivosSalvosService(Path rootPath) {
        this.rootPath = Paths.get(basePath);
    }

    public String salvarArquivo(InputStream inputStream, String nomeOriginal) throws IOException {
        LocalDate today = LocalDate.now();
        Path
    }
}
