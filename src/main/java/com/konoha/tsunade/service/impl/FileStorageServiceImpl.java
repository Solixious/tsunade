package com.konoha.tsunade.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.konoha.tsunade.exceptions.FileStorageException;
import com.konoha.tsunade.exceptions.MyFileNotFoundException;
import com.konoha.tsunade.service.FileStorageService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileStorageServiceImpl implements FileStorageService {

	private Path fileStorageLocation;

	public FileStorageServiceImpl() throws FileStorageException {
		super();
		this.fileStorageLocation = Paths.get("./img").toAbsolutePath().normalize();
		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception e) {
			log.error("Could not create the directory where file will be stored.");
			throw new FileStorageException("Could not create the directory where file will be stored.", e);
		}
	}

	public String storeFile(final MultipartFile file, final String suggestedFileName) throws FileStorageException {
		final String fileName = StringUtils.cleanPath(suggestedFileName);
		try {
			if (fileName.contains("..")) {
				log.error("File name contains invalid path sequence {}", fileName);
				throw new FileStorageException("File name contains invalid path sequence " + fileName);
			}
			final Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			return fileName;
		} catch (IOException e) {
			log.error("Could not store file {}.", fileName);
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", e);
		}
	}

	public Resource loadFileAsResource(final String fileName) throws MyFileNotFoundException {
		try {
			final Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
			final Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists())
				return resource;
			else {
				log.error("File not found: {}", fileName);
				throw new MyFileNotFoundException("File not found " + fileName);
			}
		} catch (MalformedURLException e) {
			log.error("File not found: p{}", fileName);
			throw new MyFileNotFoundException("File not found " + fileName, e);
		}
	}
}
