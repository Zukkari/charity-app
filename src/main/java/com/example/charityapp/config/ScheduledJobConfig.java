package com.example.charityapp.config;

import com.example.charityapp.events.EventPushJob;
import com.example.charityapp.job.CartDeletionJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class ScheduledJobConfig {

  private final EventPushJob eventPushJob;
  private final CartDeletionJob cartDeletionJob;

  @Autowired
  public ScheduledJobConfig(EventPushJob eventPushJob, CartDeletionJob cartDeletionJob) {
    this.eventPushJob = eventPushJob;
    this.cartDeletionJob = cartDeletionJob;
  }

  @Scheduled(fixedRate = 300_000)
  public void runCartDeletionJob() {
    cartDeletionJob.run();
  }

  @Scheduled(fixedRate = 2000)
  public void runPublishEvents() {
    eventPushJob.run();
  }
}
