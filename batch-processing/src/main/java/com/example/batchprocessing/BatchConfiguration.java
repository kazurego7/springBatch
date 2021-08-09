package com.example.batchprocessing;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.batchprocessing.job.MainStep;

/**
 * バッチの実行構成
 *
 */
@Configuration
@EnableAutoConfiguration
@EnableBatchProcessing
public class BatchConfiguration extends DefaultBatchConfigurer {
	@Autowired
	private MainStep mainStep;
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Override
	public void setDataSource(DataSource dataSource) {
		// データソースが存在する場合でもデータソースを設定しないようにオーバーライドする
		// initializeは、（データベースの代わりに）マップベースのJobRepositoryを使用する
	}

	/**
	 * ジョブ内のステップの定義.
	 * 
	 * @return
	 */
	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1").tasklet(mainStep).build();
	}

	/**
	 * ジョブの定義.
	 * 
	 * @return
	 */
	@Bean
	public Job job(Step step1) throws Exception {
		return jobBuilderFactory.get("job").incrementer(new RunIdIncrementer()).start(step1).build();
	}
}
