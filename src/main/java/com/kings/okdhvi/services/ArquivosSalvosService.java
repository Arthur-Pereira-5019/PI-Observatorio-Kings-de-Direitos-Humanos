package com.kings.okdhvi.services;

import com.kings.okdhvi.exception.ResourceNotFoundException;
import com.kings.okdhvi.exception.usuario.UnauthorizedActionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class ArquivosSalvosService {
    private final Path rootPath;

    public ArquivosSalvosService(@Value("${file_saving.base_path}") String basePath) {
        this.rootPath = Paths.get(basePath);
    }

    public String salvarArquivo(InputStream inputStream, String nomeOriginal) throws IOException {
        Files.createDirectories(rootPath);
        String extensao = retornarExtensao(nomeOriginal);
        Path filePath = rootPath.resolve(UUID.randomUUID().toString() + "." + extensao);
        try(OutputStream outputStream = Files.newOutputStream(filePath, StandardOpenOption.CREATE_NEW)) {
            StreamUtils.copy(inputStream, outputStream);
        }
        return rootPath.relativize(filePath).toString();
    }

    public Resource buscarArquivo(String caminho) throws IOException {
        Path c = rootPath.resolve(caminho).normalize().toAbsolutePath();
        Path normalizedRoot = rootPath.normalize().toAbsolutePath();

        if(!c.startsWith(normalizedRoot)) {
            throw new UnauthorizedActionException("Acesso negado!");
        }
        if(!Files.exists(c)) {
            throw new ResourceNotFoundException("Arquivo não encontrado!");
        }
        return new UrlResource(c.toUri());
    }

    private String retornarExtensao(String nomeArquivo) {
        int ponto = nomeArquivo.lastIndexOf(".");
        return ponto == -1 ? "" : nomeArquivo.substring(ponto+1);
    }
}
