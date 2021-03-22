package com.example.charityapp.config;

import com.example.charityapp.events.EventPushJob;
import com.example.charityapp.job.CartDeletionJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
@EnableScheduling
public class ScheduledJobConfig implements SchedulingConfigurer {

  private final EventPushJob eventPushJob;
  private final CartDeletionJob cartDeletionJob;

  @Autowired
  public ScheduledJobConfig(EventPushJob eventPushJob, CartDeletionJob cartDeletionJob) {
    this.eventPushJob = eventPushJob;
    this.cartDeletionJob = cartDeletionJob;
  }

  @Override
  public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
    scheduledTaskRegistrar.addFixedRateTask(eventPushJob, 3000);
    scheduledTaskRegistrar.addFixedRateTask(cartDeletionJob, 300_00);
  }
}
