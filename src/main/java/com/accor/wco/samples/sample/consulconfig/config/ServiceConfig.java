package com.accor.wco.samples.sample.consulconfig.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServiceConfig {
	  @Value("${my.comment}")
	  private String comment;
	  public String getComment(){
		    return comment;
	  }
}
