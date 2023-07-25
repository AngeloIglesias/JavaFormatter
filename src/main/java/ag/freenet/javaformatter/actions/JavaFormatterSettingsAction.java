//#***************************************************************************
//# freenet Technologie und Prozesse Source File: JavaFormatterSettingsAction.java
//# Copyright (c) 1996-2023 by freenet DLS GmbH
//# All rights reserved.
//#***************************************************************************
package ag.freenet.javaformatter.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.DialogBuilder;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.components.JBCheckBox;

import ag.freenet.javaformatter.formatting.JavaFormatter;


/********************************************************************
 * DOCME
 *
 * @author angel
 */
public class JavaFormatterSettingsAction extends AnAction
{
	//~ Methods ----------------------------------------------------------------------------------------------------------------

	@Override
	public void actionPerformed(AnActionEvent e)
	{
		DialogBuilder builder = new DialogBuilder();

		JBCheckBox checkBox = new JBCheckBox("Enable Java Formatting", JavaFormatter.getInstance().isEnabled());
		builder.setCenterPanel(checkBox);
		builder.setTitle("Java Formatter Settings");
		builder.addOkAction();
		builder.setOkOperation(() ->
			{
				JavaFormatter.getInstance().setEnabled(checkBox.isSelected());
				builder.getDialogWrapper().close(DialogWrapper.OK_EXIT_CODE);
			});

		builder.show();
	}
}
