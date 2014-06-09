package com.lunatech.doclets.jax;

import com.sun.tools.doclets.formats.html.ConfigurationImpl;
import com.sun.tools.doclets.internal.toolkit.Configuration;

public class JAXConfiguration {

  public ConfigurationImpl parentConfiguration;

  public JAXConfiguration(ConfigurationImpl conf) {
    this.parentConfiguration = conf;
  }

  public void setOptions() throws Configuration.Fault {
    parentConfiguration.setOptions();
  }
}
