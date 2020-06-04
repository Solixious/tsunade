package com.konoha.tsunade.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.konoha.tsunade.constants.UrlMapping;
import com.konoha.tsunade.exceptions.MyFileNotFoundException;
import com.konoha.tsunade.service.FileStorageService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class FileController {

	@Autowired
	private FileStorageService fileStorageService;

	@GetMapping(value = UrlMapping.DOWNLOAD_FILE)
	public ResponseEntity<Resource> downloadFile(@PathVariable final String fileName, final HttpServletRequest request)
			throws MyFileNotFoundException {
		final Resource resource = fileStorageService.loadFileAsResource(fileName);
		String contentType = null;

		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException e) {
			log.error("Could not determine the type of file.");
		}

		if (contentType == null)
			contentType = "application/octet-stream";

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
}
