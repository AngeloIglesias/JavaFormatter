//#***************************************************************************
//# freenet Technologie und Prozesse Source File: CommentRemovingVisitor.java
//# Copyright (c) 1996-2023 by freenet DLS GmbH
//# All rights reserved.
//#***************************************************************************
package ag.freenet.javaformatter.formatting; // #***************************************************************************

//# freenet Technologie und Prozesse Source File: ag.freenet.javaformatter.formatting.extern.CommentRemovingVisitor.java
//# Copyright (c) 1996-2023 by freenet DLS GmbH
//# All rights reserved.
//#***************************************************************************


/********************************************************************
 * DOCME
 *
 * @author angel
 */
public class CommentRemovingVisitor extends LexicalPreservingVisitor
{
	//~ Methods ----------------------------------------------------------------------------------------------------------------

	@Override
	public void visit(ChildTextElement child)
	{
		if (child.isComment())
		{
			Comment comment = (Comment) child.getChild();
			comment.setContent("REMOVED COMMENT");
		}
		else
		{
			super.visit(child);
		}
	}

	@Override
	public void visit(TokenTextElement token)
	{
		if (token.isComment())
		{
			token.getToken().setText("REMOVED COMMENT");
		}
		else
		{
			super.visit(token);
		}
	}
}
