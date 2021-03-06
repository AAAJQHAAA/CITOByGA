package org.apache.jmeter.functions;

import java.net.URLDecoder;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.jmeter.functions.*;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;

/**
 * @author mstover
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 */
public abstract class AbstractFunction implements Function {

	/**
	 * @see Function#execute(SampleResult, Sampler)
	 */
	abstract public String execute(SampleResult previousResult, Sampler currentSampler) 
			throws InvalidVariableException;

	/**
	 * @see Function#setParameters(String)
	 */
	abstract public void setParameters(String parameters) throws InvalidVariableException;

	/**
	 * @see Function#getReferenceKey()
	 */
	abstract public String getReferenceKey();	
	
	/**
	 * Provides a convenient way to parse the given argument string into a collection of
	 * individual arguments.  Takes care of splitting the string based on commas, generates
	 * blank strings for values between adjacent commas, and decodes the string using URLDecoder.
	 */
	protected Collection parseArguments(String params)
	{
		StringTokenizer tk = new StringTokenizer(params,",",true);
		List arguments = new LinkedList();
		String previous = "";
		while(tk.hasMoreTokens())
		{
			String arg = tk.nextToken();
			if(arg.equals(",") && previous.equals(","))
			{
				arguments.add("");
			}
			else if(!arg.equals(","))
			{
				arguments.add(URLDecoder.decode(arg));
			}
			previous = arg;
		}
		return arguments;
	}

}
