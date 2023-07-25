//#***************************************************************************
//# freenet Technologie und Prozesse Source File: ComentRemovingVisitorExample.java
//# Copyright (c) 1996-2023 by freenet DLS GmbH
//# All rights reserved.
//#***************************************************************************
package ag.freenet.javaformatter.formatting;

/********************************************************************
 * DOCME
 *
 * @author angel
 */
public class ComentRemovingVisitorExample
{
	//~ Methods ----------------------------------------------------------------------------------------------------------------

	/***************************************
	 * DOCME
	 *
	 * @param  node DOCME
	 *
	 * @return DOCME
	 */
	public static String print(Node node)
	{
		CommentRemovingVisitor visitor = new CommentRemovingVisitor();
		final NodeText nodeText = getOrCreateNodeText(node);
		nodeText.getElements().forEach(element -> element.accept(visitor));
		return visitor.toString();
	}
}
