/*
    Copyright 2009 Lunatech Research
    
    This file is part of jax-doclets.

    jax-doclets is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    jax-doclets is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with jax-doclets.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.lunatech.doclets.jax.jaxb;

import com.lunatech.doclets.jax.JAXDoclet;
import com.lunatech.doclets.jax.Utils;
import com.lunatech.doclets.jax.jaxb.model.JAXBClass;
import com.lunatech.doclets.jax.jaxb.model.JAXBMember;
import com.lunatech.doclets.jax.jaxb.model.Registry;
import com.lunatech.doclets.jax.jaxb.writers.PackageListWriter;
import com.lunatech.doclets.jax.jaxb.writers.SummaryWriter;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.DocErrorReporter;
import com.sun.javadoc.LanguageVersion;
import com.sun.javadoc.RootDoc;
import com.sun.javadoc.Type;
import com.sun.tools.doclets.formats.html.ConfigurationImpl;
import com.sun.tools.doclets.formats.html.HtmlDoclet;
import com.sun.tools.doclets.internal.toolkit.AbstractDoclet;
import com.sun.tools.doclets.internal.toolkit.Configuration;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.bind.annotation.XmlRootElement;

public class JAXBDoclet extends JAXDoclet<JAXBConfiguration> {

  private static final Class<?>[] jaxbAnnotations = new Class<?>[] { XmlRootElement.class };

  public static int optionLength(final String option) {
    if ("-matchingjaxbnamesonly".equals(option)
        || "-jsonconvention".equals(option)) {
      return 2;
    }
    if ("-disablejaxbmethodoutput".equals(option)
        || "-disablejsontypename".equals(option)
        || "-disablexmlexample".equals(option)
        || "-disablejsonexample".equals(option)) {
      return 1;
    }
    return HtmlDoclet.optionLength(option);
  }

  public static boolean validOptions(final String[][] options, final DocErrorReporter reporter) {
    if (!HtmlDoclet.validOptions(options, reporter)) {
      return false;
    }
    String value = Utils.getOption(options, "-matchingjaxbnamesonly");
    try {
      if(value != null)
        Pattern.compile(value);
    } catch (Throwable t) {
      reporter.printError("Invalid pattern for '-matchingjaxbnamesonly' option: '"+value+"' (not a valid regex)");
      return false;
    }

    String jsonConvention = Utils.getOption(options, "-jsonconvention");
    if(jsonConvention != null 
        && !jsonConvention.equals("mapped")
        && !jsonConvention.equals("jettison")
        && !jsonConvention.equals("badgerfish")){
      reporter.printError("Unknown JSON convention: "+jsonConvention+" (must be one of 'jettison' (default), 'badgerfish', 'mapped')");
      return false;
    }
    return true;
  }

  public static LanguageVersion languageVersion() {
    return AbstractDoclet.languageVersion();
  }

  private List<JAXBClass> jaxbClasses = new LinkedList<JAXBClass>();

  private Registry registry = new Registry();

  public JAXBDoclet(RootDoc rootDoc) throws Configuration.Fault {
    super(rootDoc);
  }

  @Override
  protected JAXBConfiguration makeConfiguration(ConfigurationImpl configuration) {
    return new JAXBConfiguration(configuration);
  }

  public static boolean start(final RootDoc rootDoc) throws Configuration.Fault, IOException {
    new JAXBDoclet(rootDoc).start();
    return true;
  }

  private void start() throws IOException {
    final ClassDoc[] classes = conf.parentConfiguration.root.classes();
    for (final ClassDoc klass : classes) {
      if (Utils.findAnnotatedClass(klass, jaxbAnnotations) != null) {
        handleJAXBClass(klass);
      }
    }
    for (final JAXBClass klass : jaxbClasses) {
      // System.err.println("JAXB class: " + klass);
      klass.write(conf);
    }
    new PackageListWriter(conf, registry).write();
    new SummaryWriter(conf, registry).write();
    Utils.copyResources(conf);
  }

  private void handleJAXBClass(final ClassDoc klass) {
    if (!registry.isJAXBClass(klass.qualifiedTypeName()) && !klass.isPrimitive() && !klass.qualifiedTypeName().startsWith("java.")
        && !klass.isEnum()) {
      String fqName = klass.qualifiedTypeName();
      if (conf.onlyOutputJAXBClassPackagesMatching != null) {
        Matcher m = conf.onlyOutputJAXBClassPackagesMatching.matcher(fqName);
        if (!m.matches()) {
          return;
        }
      }
      JAXBClass jaxbClass = new JAXBClass(klass, registry, this);
      jaxbClasses.add(jaxbClass);
      registry.addJAXBClass(jaxbClass);
      // load all used types
      List<JAXBMember> members = jaxbClass.getMembers();
      for (JAXBMember member : members) {
        Type type = member.getJavaType();
        ClassDoc doc = type.asClassDoc();
        if (doc != null) {
          handleJAXBClass(doc);
        }
      }
    }
  }

}
