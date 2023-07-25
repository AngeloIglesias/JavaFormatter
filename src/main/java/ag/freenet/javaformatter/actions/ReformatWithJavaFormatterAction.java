//#***************************************************************************
//# freenet Technologie und Prozesse Source File: ReformatWithJavaFormatterAction.java
//# Copyright (c) 1996-2023 by freenet DLS GmbH
//# All rights reserved.
//#***************************************************************************
package ag.freenet.javaformatter.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;

import ag.freenet.javaformatter.formatting.JavaFormatter;


/********************************************************************
 * Erstellt eine neue Menüaktion unter "Code -> Reformat with JavaFormatter".
 */
public class ReformatWithJavaFormatterAction extends AnAction
{
	//~ Methods ----------------------------------------------------------------------------------------------------------------

	@Override
	public void actionPerformed(AnActionEvent e)
	{
		// Get the current project, editor and file
		Project project = e.getProject();

		if (project == null)
		{
			return;
		}

		Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();

		if (editor == null)
		{
			return;
		}

		PsiFile file = PsiDocumentManager.getInstance(project).getPsiFile(editor.getDocument());

		if (file == null)
		{
			return;
		}

		// Format the file using JavaFormatter
		JavaFormatter formatter = JavaFormatter.getInstance();
		formatter.format(file);
	}
}
