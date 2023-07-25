//#***************************************************************************
//# freenet Technologie und Prozesse Source File: JavaFormatter.java
//# Copyright (c) 1996-2023 by freenet DLS GmbH
//# All rights reserved.
//#***************************************************************************
package ag.freenet.javaformatter.formatting;

import com.github.javaparser.ParseProblemException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.printer.lexicalpreservation.LexicalPreservingPrinter;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.psi.PsiFile;

import static ag.freenet.javaformatter.formatting.FormattingRules.addCommentsToPublicMethods;


/********************************************************************
 * Class for reformatting the code
 */
public class JavaFormatter
{
	//~ Static fields/initializers ---------------------------------------------------------------------------------------------

	private static JavaFormatter instance = null;

	//~ Instance fields --------------------------------------------------------------------------------------------------------

	private boolean enabled = true;

	//~ Constructors -----------------------------------------------------------------------------------------------------------

	// Private constructor to prevent instantiation
	/***************************************
	 * Erstellt eine neue JavaFormatter Instanz.
	 */
	private JavaFormatter()
	{
	}

	//~ Methods ----------------------------------------------------------------------------------------------------------------

	// Method to get the singleton instance
	public static synchronized JavaFormatter getInstance()
	{
		if (instance == null)
		{
			instance = new JavaFormatter();
		}

		return instance;
	}

	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

	/***************************************
	 * DOCME
	 *
	 * @param file DOCME
	 */
	public void format(PsiFile file)
	{
		if (!enabled)
		{
			return;
		}

		CompilationUnit cu;

		System.out.println(file.getText()); // DEBUG

		// Parse the file with JavaParser
		try
		{
			// Versuchen, die Formatierung auszuführen
			cu = StaticJavaParser.parse(file.getText());
		}
		catch (ParseProblemException e)
		{
			// Wrong Java Code as Input
			System.err.println("Fehler beim Parsen des Codes: " + e.getMessage());
			return;
		}

		// Enable lexical preserving printing (has to happen before manipulating the tree):
		LexicalPreservingPrinter.setup(cu);

		applyFormattingRules(cu);

		// Print the formatted code
		String print = LexicalPreservingPrinter.print(cu);

		// Replace the file text with the formatted code
		// Use WriteCommandAction to ensure that this is run within a write action
		WriteCommandAction.runWriteCommandAction(file.getProject(), () -> file.getViewProvider().getDocument().setText(print));
	}

	private static void applyFormattingRules(CompilationUnit cu)
	{
		// Apply formatting rules
		addCommentsToPublicMethods(cu);


		return;
	}

	public boolean isEnabled()
	{
		return enabled;
	}
}
