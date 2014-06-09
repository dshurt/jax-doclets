package com.lunatech.doclets.jax.jaxrs;

import java.util.Collections;
import java.util.Enumeration;
import java.util.ResourceBundle;

/**
 *
 * @author dshurtleff
 */
public class JAXRSResource
	extends ResourceBundle
{
		
	
	@Override
	protected Object handleGetObject(String key)
	{
		//TODO: Translate		
		return key;
	}

	@Override
	public Enumeration<String> getKeys()
	{
		return Collections.enumeration(keySet());
	}
	

	
}
