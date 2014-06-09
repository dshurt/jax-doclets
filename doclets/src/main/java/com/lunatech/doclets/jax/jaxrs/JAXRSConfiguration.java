package com.lunatech.doclets.jax.jaxrs;

import com.lunatech.doclets.jax.JAXConfiguration;
import com.lunatech.doclets.jax.Utils;
import com.sun.tools.doclets.formats.html.ConfigurationImpl;
import com.sun.tools.doclets.internal.toolkit.Configuration;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class JAXRSConfiguration extends JAXConfiguration {

  public String jaxrscontext;

  public boolean enableHTTPExample;

  public boolean enableJavaScriptExample;
  
  public List<Pattern> pathExcludeFilters = new ArrayList<Pattern>();

  public boolean enablePojoJsonDataObjects ;

  public Pattern onlyOutputPojosMatching;

  public Pattern onlyOutputResourcesMatching;

  public JAXRSConfiguration(ConfigurationImpl conf) {
    super(conf);
  }

  public void setOptions() throws Configuration.Fault {
    super.setOptions();
    String[][] options = parentConfiguration.root.options();
    jaxrscontext = Utils.getOption(options, "-jaxrscontext");
    enableHTTPExample = !Utils.hasOption(options, "-disablehttpexample");
    enableJavaScriptExample = !Utils.hasOption(options, "-disablejavascriptexample");
    List<String> excludeFilters = Utils.getOptions(options, "-pathexcludefilter");
    for(String excludeFilter : excludeFilters){
      pathExcludeFilters.add(Pattern.compile(excludeFilter));
    }
  }

}
