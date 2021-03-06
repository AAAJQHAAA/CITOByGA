package org.apache.jmeter.samplers.gui;
import java.util.Arrays;
import java.util.Collection;

import javax.swing.JPopupMenu;
import org.apache.jmeter.gui.AbstractJMeterGuiComponent;
import org.apache.jmeter.gui.NamePanel;
import org.apache.jmeter.gui.util.MenuFactory;
import org.apache.jmeter.testelement.TestElement;

/****************************************
 * Title: JMeter Description: Copyright: Copyright (c) 2000 Company: Apache
 *
 *@author    Michael Stover
 *@created   $Date: 2004/12/06 20:02:07 $
 *@version   1.0
 ***************************************/

public abstract class AbstractSamplerGui extends AbstractJMeterGuiComponent
{


	/****************************************
	 * !ToDo (Method description)
	 *
	 *@return   !ToDo (Return description)
	 ***************************************/
	public JPopupMenu createPopupMenu()
	{
		return MenuFactory.getDefaultSamplerMenu();
	}

	/****************************************
	 * !ToDoo (Method description)
	 *
	 *@return   !ToDo (Return description)
	 ***************************************/
	public Collection getMenuCategories()
	{
		return Arrays.asList(new String[]{MenuFactory.SAMPLERS});
	}

	}
