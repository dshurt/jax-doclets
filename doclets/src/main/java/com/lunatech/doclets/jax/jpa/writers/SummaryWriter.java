/*
    Copyright 2009-2011 Lunatech Research
    Copyright 2009-2011 StÃ©phane Ã‰pardaud
    
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
package com.lunatech.doclets.jax.jpa.writers;

import com.lunatech.doclets.jax.JAXConfiguration;
import com.lunatech.doclets.jax.Utils;
import com.lunatech.doclets.jax.jpa.model.JPAClass;
import com.lunatech.doclets.jax.jpa.model.Registry;
import com.sun.javadoc.Doc;
import com.sun.tools.doclets.formats.html.HtmlDocletWriter;
import com.sun.tools.doclets.formats.html.markup.ContentBuilder;
import com.sun.tools.doclets.internal.toolkit.util.DocPath;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SummaryWriter extends com.lunatech.doclets.jax.writers.DocletWriter {

  private Registry registry;

  public SummaryWriter(JAXConfiguration configuration, Registry registry) {
    super(configuration, getWriter(configuration));
    this.registry = registry;
  }

  private static HtmlDocletWriter getWriter(JAXConfiguration configuration) {
    try {
      return new HtmlDocletWriter(configuration.parentConfiguration, DocPath.create("index.html"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void write() throws IOException {
    printHeader();
    printMenu("Overview");
    List<JPAClass> classes = new ArrayList<JPAClass>(registry.getJPAClasses());
    Collections.sort(classes);
    printClasses(classes);
    tag("hr");
    printMenu("Overview");
    printFooter();
    //writer.flush();
    writer.close();
  }

  private void printClasses(Collection<JPAClass> classes) {
    tag("hr");
    open("table class='info'");
    around("caption class='TableCaption'", "Elements");
    open("tbody");
    open("tr");
    around("th class='TableHeader'", "Name");
    around("th class='TableHeader'", "Description");
    close("tr");
    for (JPAClass klass : classes) {
      open("tr");
      open("td");
      around("a href='" + writer.path + Utils.classToPath(klass) + "/" + klass.getShortClassName() + ".html'", klass.getName());
      close("td");
      open("td");
      Doc javaDoc = klass.getJavaDoc();
      if (javaDoc != null && javaDoc.firstSentenceTags() != null)
        writer.addSummaryComment(javaDoc, new ContentBuilder());
      close("td");
      close("tr");

    }
    close("tbody");
    close("table");
  }

  protected void printHeader() {
    printHeader("Overview of XML elements");
  }

  @Override
  protected void printTopMenu(String selected) {
    open("table", "tbody", "tr");
    printMenuItem("Overview", writer.path + "index.html", selected);
    printOtherMenuItems(selected);
    close("tr", "tbody", "table");
  }

  protected void printOtherMenuItems(String selected) {
    printMenuItem("Graph", writer.path + "graph.html", selected);
  }
}
