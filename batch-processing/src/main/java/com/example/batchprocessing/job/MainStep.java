package com.example.batchprocessing.job;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.batchprocessing.entity.ActorFileEntity;
import com.example.batchprocessing.properties.Constants;
import com.example.batchprocessing.repository.ActorFileRepository;
import com.example.batchprocessing.repository.ActorRepository;

/**
 * バッチのメイン処理のクラス
 * 
 */
@Component
public class MainStep implements Tasklet {

	private static final Logger logger = LoggerFactory.getLogger(MainStep.class);

	@Autowired
	Constants constants;
	@Autowired
	ActorFileRepository actorFileRepository;
	@Autowired
	ActorRepository actorRepository;

	/**
	 * バッチのメイン処理
	 */
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
		logger.info("called.");

		try {
			// ファイルパスの初期化
			Path actorFileDirPath = Paths.get(constants.getActorFilePath());
			List<Path> actorFilePathList = Files.list(actorFileDirPath).toList();
			Path backupDirPath = Paths.get(constants.getBackupPath());

			for (Path actorFilePath : actorFilePathList) {
				logger.info("file read: " + actorFilePath.toAbsolutePath().toString());
				// ファイル読み込み
				List<ActorFileEntity> actorFileLists = actorFileRepository.readCSVNoHeader(actorFilePath);
				Timestamp sysTimestamp = new Timestamp(System.currentTimeMillis());

				// DB書き込み
				for (ActorFileEntity actorFileEntity : actorFileLists) {
					actorRepository.updateTimestamp(sysTimestamp, Integer.valueOf(actorFileEntity.actorId));
				}

				// バックアップファイルへの移動
				Path backupFilePath = backupDirPath.resolve(actorFilePath.getFileName());
				logger.info("file move: " + backupFilePath.toAbsolutePath().toString());
				Files.move(actorFilePath, backupFilePath);
			}
		} catch (IOException e) {
			// エラーログ出力
			logger.error(e.getMessage(), e);
			return RepeatStatus.FINISHED;
		}

		logger.info("finished.");
		return RepeatStatus.FINISHED;
	}
}
