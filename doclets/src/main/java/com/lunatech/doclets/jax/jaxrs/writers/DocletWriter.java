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
package com.lunatech.doclets.jax.jaxrs.writers;

import com.lunatech.doclets.jax.JAXConfiguration;
import com.lunatech.doclets.jax.jaxrs.JAXRSConfiguration;
import com.lunatech.doclets.jax.jaxrs.JAXRSDoclet;
import com.lunatech.doclets.jax.jaxrs.model.JAXRSApplication;
import com.lunatech.doclets.jax.jaxrs.model.Resource;
import com.sun.tools.doclets.formats.html.HtmlDocletWriter;

public class DocletWriter extends com.lunatech.doclets.jax.writers.DocletWriter {

  protected Resource resource;

  protected JAXRSDoclet doclet;

  protected JAXRSApplication application;

  public DocletWriter(JAXConfiguration configuration, HtmlDocletWriter writer, JAXRSApplication application, Resource resource,
      JAXRSDoclet doclet) {
    super(configuration, writer);
    this.resource = resource;
    this.doclet = doclet;
    this.application = application;
  }

  public Resource getResource() {
    return resource;
  }

  public JAXRSApplication getApplication() {
    return application;
  }

  protected void printOtherMenuItems(String selected) {
    printMenuItem("Index", writer.path + "overview-index.html", selected);
    if (getJAXRSConfiguration().enablePojoJsonDataObjects) {
    	printMenuItem("Data objects", writer.path + "objects-index.html", selected);
    }
    printMenuItem("Root resource", writer.path + "index.html", selected);
  }

  protected final void printPrelude(final String title, final String selected) {
    printHeader(title);
    printMenu(selected, "top");
  }

  protected final void printPostlude(String selected) {
    printMenu(selected, "bottom");
    printFooter();
  }

  public JAXRSConfiguration getJAXRSConfiguration(){
    return (JAXRSConfiguration) configuration;
  }
}
