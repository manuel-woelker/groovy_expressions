package org.eclipse.core.internal.expressions;

import groovy.lang.GroovyShell;

import java.util.Arrays;
import java.util.List;

import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.ExpressionInfo;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

public class GroovyExpression  extends Expression {

	private final String value;
	private List<String> using;
	private GroovyShell groovyShell;

	public GroovyExpression(IConfigurationElement element){
		value = element.getValue();
		using = Arrays.asList(element.getAttribute("using").split(","));
		groovyShell = new GroovyShell();
	}	
	
	public EvaluationResult evaluate(IEvaluationContext context)
			throws CoreException {
		
		Object result = null;
		try {
			for(String var: using) {
				groovyShell.setVariable(var, context.getVariable(var));				
			}
			result = groovyShell.evaluate(value);			
		} catch (Exception e) {
//			e.printStackTrace();
		}
		
		return Boolean.TRUE.equals(result)?EvaluationResult.TRUE:EvaluationResult.FALSE;
	}

	public void collectExpressionInfo(ExpressionInfo info) {
		for(String var: using) {		
			info.addVariableNameAccess(var);
		}		
	}
	
	
	
	

}
