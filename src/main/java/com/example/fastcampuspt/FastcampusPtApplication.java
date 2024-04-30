package com.example.fastcampuspt;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.config.Task;
import org.springframework.transaction.PlatformTransactionManager;

@SpringBootApplication
public class FastcampusPtApplication {

    //스텝을 통해서 잡을 구현
    //강의와는 다르게 migration 되었으므로 일부 다름.
    //강의와 다르게 이렇게 실행하면 잡이 자동으로 실행이 안됨.
    // 아래 링크 참고한바 @EnableBatchProcesesing이 있는 경우
    // 추가로 autoConfiguration 참고하여
    //@AutoConfiguration: 이 어노테이션은 Spring Boot에서 제공하지 않으며, 이 코드가 예시를 위해 사용한 것 같습니다. 일반적으로는 @Configuration 어노테이션이 사용되어, 해당 클래스가 구성(Configuration) 클래스임을 나타냅니다. 실제로는 @AutoConfiguration 대신 @SpringBootApplication 또는 @EnableAutoConfiguration을 사용할 수 있습니다.
    //@ConditionalOnClass: 이 클래스가 클래스패스에 존재할 때만 이 구성 클래스를 적용하라는 조건을 명시합니다. 여기서는 JobLauncher, DataSource, DatabasePopulator 클래스가 필요합니다.
    //@ConditionalOnBean: 지정된 타입의 빈(Bean)이 이미 컨텍스트에 존재할 때만 구성을 적용합니다. 이 경우에는 DataSource와 PlatformTransactionManager 빈이 필요합니다.
    //@ConditionalOnMissingBean: 지정된 타입의 빈이 컨텍스트에 존재하지 않을 때만 구성을 적용합니다. 여기서는 DefaultBatchConfiguration 타입 또는 EnableBatchProcessing 어노테이션이 붙은 빈이 없을 때만 적용됩니다.
    //@EnableConfigurationProperties: 특정 프로퍼티 클래스(BatchProperties 클래스)를 사용하여 구성 가능한 프로퍼티들을 활성화합니다. 이 어노테이션을 사용함으로써, 애플리케이션의 프로퍼티 파일에서 해당 프로퍼티 클래스에 정의된 프로퍼티들을 쉽게 바인딩할 수 있습니다.
    //@Import: 다른 구성 클래스(DatabaseInitializationDependencyConfigurer)를 현재의 구성 클래스에 명시적으로 추가합니다. 이를 통해 추가된 구성 클래스의 빈들이 현재 컨텍스트에 포함되도록 합니다.
    // 참고
    // 출처 : https://stackoverflow.com/questions/75626317/spring-batch-5-does-not-start-job-automatically


    @Bean
    public Job passJob(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager){
        return new JobBuilder("passJob", jobRepository)
            .start(passStep(jobRepository,platformTransactionManager))
            .build();
    }

    @Bean
    public Step passStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager){
        return new StepBuilder("passStep", jobRepository)
            .tasklet(((contribution, chunkContext) -> {
                System.out.println("Execute PassStep");
                return RepeatStatus.FINISHED;
            }), platformTransactionManager)
            .build();
    }


    public static void main(String[] args) {
        SpringApplication.run(FastcampusPtApplication.class, args);
    }

}
