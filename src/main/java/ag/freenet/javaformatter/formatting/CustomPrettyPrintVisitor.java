//#***************************************************************************
//# freenet Technologie und Prozesse Source File: CustomPrettyPrintVisitor.java
//# Copyright (c) 1996-2023 by freenet DLS GmbH
//# All rights reserved.
//#***************************************************************************
package ag.freenet.javaformatter.formatting;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.printer.PrettyPrintVisitor;
import com.github.javaparser.printer.configuration.PrettyPrinterConfiguration;


/********************************************************************
 * DOCME
 *
 * @author angel
 */
public class CustomPrettyPrintVisitor extends PrettyPrintVisitor
{
	//~ Instance fields --------------------------------------------------------------------------------------------------------

	private final CompilationUnit ast;
	private final CompilationUnit cst;

	//~ Constructors -----------------------------------------------------------------------------------------------------------

	/***************************************
	 * Erstellt eine neue MyPrettyPrintVisitor Instanz.
	 *
	 * @param prettyPrinterConfiguration DOCME
	 * @param ast                        DOCME
	 * @param cst                        DOCME
	 */
	public CustomPrettyPrintVisitor(PrettyPrinterConfiguration prettyPrinterConfiguration, CompilationUnit ast,
			CompilationUnit cst)
	{
		super(prettyPrinterConfiguration);

		this.ast = ast;
		this.cst = cst;
	}

	//~ Methods ----------------------------------------------------------------------------------------------------------------

	// ToDo:
	// Hier müssen alle Werte, die erhalten bleiben sollen in den AST (aus dem CST) integriert werden

	@Override
	public void visit(MethodDeclaration n, Void arg)
	{
		super.visit(n, arg); // This will print the method declaration as usual.

		// ToDo: JUST AN EXAMPLE
		printer.println("/* THAT'S JUST NONESENSE */");
	}
}
