package com.example.charityapp.config;

import com.example.charityapp.job.CartDeletionJob;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class ScheduledJobConfig {

  private final CartDeletionJob cartDeletionJob;

  public ScheduledJobConfig(CartDeletionJob cartDeletionJob) {
    this.cartDeletionJob = cartDeletionJob;
  }

  @Scheduled(fixedRate = 300_000)
  public void runCartDeletionJob() {
    cartDeletionJob.run();
  }
}
