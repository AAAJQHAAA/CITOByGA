package org.apache.jmeter.control.gui;
import java.util.Arrays;
import java.util.Collection;

import javax.swing.JPopupMenu;
import org.apache.jmeter.gui.AbstractJMeterGuiComponent;
import org.apache.jmeter.gui.util.MenuFactory;
import org.apache.jmeter.testelement.TestElement;

/****************************************
 * Title: JMeter Description: Copyright: Copyright (c) 2000 Company: Apache
 *
 *@author    Michael Stover
 *@created   $Date: 2004/12/06 20:02:03 $
 *@version   1.0
 ***************************************/

public abstract class AbstractControllerGui extends AbstractJMeterGuiComponent
{

	/****************************************
	 * !ToDoo (Method description)
	 *
	 *@return   !ToDo (Return description)
	 ***************************************/
	public Collection getMenuCategories()
	{
		return Arrays.asList(new String[]{MenuFactory.CONTROLLERS});
	}

	/****************************************
	 * !ToDo (Method description)
	 *
	 *@return   !ToDo (Return description)
	 ***************************************/
	public JPopupMenu createPopupMenu()
	{
		return MenuFactory.getDefaultControllerMenu();
	}

	}
