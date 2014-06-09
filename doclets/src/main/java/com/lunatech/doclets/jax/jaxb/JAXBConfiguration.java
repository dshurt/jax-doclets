package com.lunatech.doclets.jax.jaxb;

import com.lunatech.doclets.jax.JAXConfiguration;
import com.lunatech.doclets.jax.Utils;
import com.sun.tools.doclets.formats.html.ConfigurationImpl;
import com.sun.tools.doclets.internal.toolkit.Configuration;
import java.util.regex.Pattern;

public class JAXBConfiguration extends JAXConfiguration {

  public boolean enableJSONTypeName;

  public Pattern onlyOutputJAXBClassPackagesMatching;

  public boolean enableJaxBMethodOutput = true;
  
  public boolean enableJSONExample = true;
  
  public boolean enableXMLExample = true;

  public JSONConvention jsonConvention = JSONConvention.JETTISON_MAPPED;

  
  public JAXBConfiguration(ConfigurationImpl conf) {
    super(conf);
  }

  @Override
  public void setOptions() throws Configuration.Fault {
    super.setOptions();
    String[][] options = parentConfiguration.root.options();
    String pattern = Utils.getOption(options, "-matchingjaxbnamesonly");
    if (pattern != null) {
      onlyOutputJAXBClassPackagesMatching = Pattern.compile(pattern);
    }
    enableJaxBMethodOutput = !Utils.hasOption(options, "-disablejaxbmethodoutput");
    enableJSONTypeName = !Utils.hasOption(options, "-disablejsontypename");
    enableJSONExample = !Utils.hasOption(options, "-disablejsonexample");
    enableXMLExample = !Utils.hasOption(options, "-disablexmlexample");
    
    String jsonConventionLocal = Utils.getOption(options, "-jsonconvention");
    if(jsonConventionLocal == null || "jettison".equals(jsonConventionLocal))
      this.jsonConvention = JSONConvention.JETTISON_MAPPED;
    else if("badgerfish".equals(jsonConventionLocal))
      this.jsonConvention = JSONConvention.BADGERFISH;
    else if("mapped".equals(jsonConventionLocal))
      this.jsonConvention = JSONConvention.MAPPED;
    else{
      parentConfiguration.root.printError("Unknown JSON convention: "+jsonConventionLocal+" (must be one of 'jettison' (default), 'badgerfish', 'mapped')");
    }
  }
}
