package com.example.batchprocessing.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "constants")
public class Constants {
	private String actorFilePath;
	private String backupPath;

	public String getActorFilePath() {
		return actorFilePath;
	}

	public void setActorFilePath(String actorFilePath) {
		this.actorFilePath = actorFilePath;
	}

	public String getBackupPath() {
		return backupPath;
	}

	public void setBackupPath(String backupPath) {
		this.backupPath = backupPath;
	}
}
