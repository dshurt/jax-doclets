/*
    Copyright 2009-2011 Lunatech Research
    Copyright 2009-2011 Stéphane Épardaud
    
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
import com.lunatech.doclets.jax.jpa.model.JPAClass;
import com.lunatech.doclets.jax.jpa.model.Registry;
import com.sun.tools.doclets.formats.html.HtmlDocletWriter;
import com.sun.tools.doclets.internal.toolkit.util.DocPath;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class PackageListWriter extends com.lunatech.doclets.jax.writers.DocletWriter {

  private Registry registry;

  public PackageListWriter(JAXConfiguration configuration, Registry registry) {
    super(configuration, getWriter(configuration));
    this.registry = registry;
  }

  private static HtmlDocletWriter getWriter(JAXConfiguration configuration) {
    try {
      return new HtmlDocletWriter(configuration.parentConfiguration, DocPath.create("package-list"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void write() throws IOException {
    Set<String> packages = new HashSet<String>();
    for (JPAClass klass : registry.getJPAClasses()) {
      packages.add(klass.getPackageName());
    }
    for (String packageName : packages)
      print(packageName + "\n");
    //writer.flush();
    writer.close();
  }

  @Override
  protected void printTopMenu(String selected) {
    open("table", "tbody", "tr");
    printMenuItem("Overview", writer.path + "index.html", selected);
    printOtherMenuItems(selected);
    close("tr", "tbody", "table");
  }
}
