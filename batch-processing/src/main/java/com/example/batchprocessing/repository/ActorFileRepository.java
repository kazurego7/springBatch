package com.example.batchprocessing.repository;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.batchprocessing.entity.ActorFileEntity;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

/**
 * アクターをファイルから取得するリポジトリ
 *
 */
@Repository
public class ActorFileRepository {
	public List<ActorFileEntity> readCSVNoHeader(Path path) throws IOException {
		CsvMapper csvMapper = new CsvMapper();
		CsvSchema csvSchema = csvMapper.schemaFor(ActorFileEntity.class).withColumnSeparator('\t');

		MappingIterator<ActorFileEntity> iter = csvMapper.readerFor(ActorFileEntity.class).with(csvSchema)
				.readValues(path.toFile());

		List<ActorFileEntity> fileEntityList = iter.readAll();

		return fileEntityList;
	}
}
