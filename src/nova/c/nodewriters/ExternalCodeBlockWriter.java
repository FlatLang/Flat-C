package nova.c.nodewriters;

import net.fathomsoft.nova.tree.ExternalCodeBlock;

public abstract class ExternalCodeBlockWriter extends NodeWriter
{
	public abstract ExternalCodeBlock node();
	
	@Override
	public StringBuilder generateSource(StringBuilder builder)
	{
		return builder.append(node().joinContents(x -> getWriter(x).generateSourceFragment().toString())).append('\n');
	}
}