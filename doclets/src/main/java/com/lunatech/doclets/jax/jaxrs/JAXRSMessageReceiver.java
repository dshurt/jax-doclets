/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lunatech.doclets.jax.jaxrs;

import com.sun.tools.doclets.internal.toolkit.Configuration;
import com.sun.tools.doclets.internal.toolkit.util.MessageRetriever;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 *
 * @author dshurtleff
 */
public class JAXRSMessageReceiver
	extends MessageRetriever
{
	private ResourceBundle messageRB;
	
	public JAXRSMessageReceiver(ResourceBundle rb)
	{
		super(rb);
	}
	
	public JAXRSMessageReceiver(Configuration configuration, ResourceBundle rb)
	{
		super(configuration, null);
		this.messageRB = rb;
	}	

	@Override
	public String getText(String key, Object... args) throws MissingResourceException
	{
		String message = messageRB.getString(key);
		return MessageFormat.format(message, args);
	}
}
