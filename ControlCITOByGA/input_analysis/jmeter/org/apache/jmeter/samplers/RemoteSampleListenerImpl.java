/*
 * ====================================================================
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2001 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in
 * the documentation and/or other materials provided with the
 * distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 * if any, must include the following acknowledgment:
 * "This product includes software developed by the
 * Apache Software Foundation (http://www.apache.org/)."
 * Alternately, this acknowledgment may appear in the software itself,
 * if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "Apache" and "Apache Software Foundation" and
 * "Apache JMeter" must not be used to endorse or promote products * derived from this software without prior written permission. For
 * written permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache",
 * "Apache JMeter", nor may "Apache" appear in their name, without
 * prior written permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 */
 package org.apache.jmeter.samplers;

import java.rmi.RemoteException;

import org.apache.jmeter.testelement.TestListener;

/************************************************************
 *  !ToDo (Class description)
 *
 *@author     $Author: dohy $
 *@created    $Date: 2004/12/06 20:02:07 $
 *@version    $Revision: 1.1.1.1 $
 ***********************************************************/
public class RemoteSampleListenerImpl
		 extends java.rmi.server.UnicastRemoteObject
		 implements RemoteSampleListener,SampleListener,TestListener
{
	TestListener testListener;
	SampleListener sampleListener;

	/************************************************************
	 *  !ToDo (Constructor description)
	 *
	 *@param  listeners            !ToDo (Parameter description)
	 *@exception  RemoteException  !ToDo (Exception description)
	 ***********************************************************/
	public RemoteSampleListenerImpl(Object listener) throws RemoteException
	{
		super();
		if(listener instanceof TestListener)
		{
			testListener = (TestListener)listener;
		}
		if(listener instanceof SampleListener)
		{
			sampleListener = (SampleListener)listener;
		}
	}

	public void testStarted()
	{
		if(testListener != null)
		{
			testListener.testStarted();
		}
	}

	public void testStarted(String host)
	{
		if(testListener != null)
		{
			testListener.testStarted(host);
		}
	}

	public void testEnded()
	{
		if(testListener != null)
		{
			testListener.testEnded();
		}
	}

	public void testEnded(String host)
	{
		if(testListener != null)
		{
			testListener.testEnded(host);
		}
	}

	/************************************************************
	 *  !ToDo (Method description)
	 *
	 *@param  e  !ToDo (Parameter description)
	 ***********************************************************/
	public void sampleOccurred(SampleEvent e)
	{
		if (sampleListener != null)
		{
			sampleListener.sampleOccurred(e);
		}
	}

	/************************************************************
	 *  A sample has started.
	 *
	 *@param  e  !ToDo (Parameter description)
	 ***********************************************************/
	public void sampleStarted(SampleEvent e)
	{
		if (sampleListener != null)
		{
			sampleListener.sampleStarted(e);
		}
	}

	/************************************************************
	 *  A sample has stopped.
	 *
	 *@param  e  !ToDo (Parameter description)
	 ***********************************************************/
	public void sampleStopped(SampleEvent e)
	{
		if (sampleListener != null)
		{
			sampleListener.sampleStopped(e);
		}
	}
}
