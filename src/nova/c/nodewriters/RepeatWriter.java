package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;

public abstract class RepeatWriter extends LoopWriter
{
	public abstract Repeat node();
	
	public StringBuilder generateSource(StringBuilder builder)
	{
		Value value = node().getValueNode();
		
		if (value != null)
		{
			builder.append("for (").append(node().getName()).append(" = 0; ").append(node().getName()).append(" < ").append(value.getTarget().generateSourceFragment()).append("; ").append(node().getName()).append("++)\n");
		}
		else
		{
			builder.append("for (;;)\n");
		}
		
		Scope scope = node().getScope();
		
		scope.getTarget().generateSource(builder);
		
		return builder;
	}
}