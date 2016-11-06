package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.tree.variables.VariableDeclaration;

public abstract class ClosureWriter extends VariableWriter
{
	public abstract Closure node();
	
	public StringBuilder generateSource(StringBuilder builder)
	{
		return generateSourceFragment(builder);
	}
	
	public StringBuilder generateSourceFunctionReference(StringBuilder builder)
	{
		if (node().getMethodDeclaration().isVirtual() && !node().isVirtualTypeKnown())
		{
			Accessible root = node().getRootReferenceNode();
			
			getWriter(root).generateArgumentReference(builder, node()).append("->").append(VTable.IDENTIFIER).append("->");
			
			VirtualMethodDeclaration virtual = node().getMethodDeclaration().getVirtualMethod();
			
			builder.append(getWriter(virtual).generateVirtualMethodName());
		}
		else
		{
			VariableDeclaration d = node().getDeclaration();
			
			builder.append('&').append(getWriter(d).generateSourceName());
		}
		
		return builder;
	}
	
	public StringBuilder generateClosureInstanceReference()
	{
		return generateClosureInstanceReference(new StringBuilder());
	}
	
	public StringBuilder generateClosureInstanceReference(StringBuilder builder)
	{
		return getWriter(node().getClosureDeclaration()).generateClosureInstanceReference(builder, node());
	}
	
	public StringBuilder generateClosureContextReference()
	{
		return generateClosureContextReference(new StringBuilder());
	}
	
	public StringBuilder generateClosureContextReference(StringBuilder builder)
	{
		return getWriter(node().getClosureDeclaration()).generateClosureContextReference(builder, node().getMethodDeclaration());
	}
	
	public StringBuilder generateSourceFragment(StringBuilder builder)
	{
		ClosureDeclaration decl = node().getClosureDeclaration();
		ClosureDeclaration base = decl;
		
		MethodCall call = node().getMethodCall();
		
		NovaMethodDeclaration method = call.getNovaMethod();
		
		if (method != null && !call.isVirtualTypeKnown())
		{
			base = (ClosureDeclaration)method.getVirtualMethod().getParameterList().getParameter(decl.getIndex());
		}
		
		getWriter(base).generateTypeCast(builder);
		
		generateSourceFunctionReference(builder);
		
		builder.append(", ");
		
		getWriter(decl).generateArguments(builder, node(), node().getMethodDeclaration());
		
		return builder;
	}
}