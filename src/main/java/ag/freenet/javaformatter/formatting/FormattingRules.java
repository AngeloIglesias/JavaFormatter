//#***************************************************************************
//# freenet Technologie und Prozesse Source File: FormattingRules.java
//# Copyright (c) 1996-2023 by freenet DLS GmbH
//# All rights reserved.
//#***************************************************************************
package ag.freenet.javaformatter.formatting;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.comments.JavadocComment;


/********************************************************************
 * DOCME
 *
 * @author angel
 */
public class FormattingRules
{
	//~ Methods ----------------------------------------------------------------------------------------------------------------

	/***************************************
	 * DOCME
	 *
	 * @param cu DOCME
	 */
	public static void addCommentsToPublicMethods(CompilationUnit cu)
	{
		cu.findAll(MethodDeclaration.class).stream().filter(method -> method.isPublic() && !method.getComment().isPresent())
			.forEach(method -> method.setComment(new JavadocComment("Dr. Me")));
	}
}
