package com.example.charityapp.controller;

import com.example.charityapp.events.EventEmitter;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/events")
@OpenAPIDefinition(
    tags = @Tag(name = "Events", description = "This API streams events produced by the system"),
    info = @Info(version = "V1", title = "Event stream API"))
@CrossOrigin
public class EventStreamController {

  private final EventEmitter eventEmitter;

  @Autowired
  public EventStreamController(EventEmitter eventEmitter) {
    this.eventEmitter = eventEmitter;
  }

  @Operation(
      method = "GET",
      description = "Subscribe to event stream produced by the system",
      summary =
          "This stream contains events produced by the system. Example of an event: 'item has been booked'",
      tags = "Events")
  @GetMapping("/stream")
  public SseEmitter streamEvents() {
    var sseEmitter = new SseEmitter();
    eventEmitter.register(sseEmitter);
    return sseEmitter;
  }
}
