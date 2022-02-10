package com.trang.QuanLyNhanVien.Service;

import java.nio.file.Path;
import java.util.stream.Stream;

import javax.annotation.Resource;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
	void init();

	void store(MultipartFile file);

	Stream<Path> loadAll();

	Path load(String filename);

	Resource loadAsResource(String filename);

	void deleteAll();

}
