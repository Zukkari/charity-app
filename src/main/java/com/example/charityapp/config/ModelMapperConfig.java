package com.example.charityapp.config;

import com.example.charityapp.module.CharityModelMapperModule;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

  @Bean
  public ModelMapper modelMapper(CharityModelMapperModule modelMapperModule) {
    var mapper = new ModelMapper();
    mapper.registerModule(modelMapperModule);
    return mapper;
  }
}
