
import java.util.*;

import org.antlr.v4.runtime.misc.NotNull;

public class EpsilonListenerImplementation extends EpsilonBaseListener{

	List<String> ind = new ArrayList<String>();	
	int mainCnt = 0;
	int lineCount = 1;
	
	@Override 
	public void enterStart(@NotNull EpsilonParser.StartContext ctx) {
		if(mainCnt<1){
			ind.add("");
			mainCnt++;
		}
	}
	
	@Override
	public void exitStart(@NotNull EpsilonParser.StartContext ctx) {
		ind.add("EXIT ");
	}
	
	@Override 
	public void exitPrint(@NotNull EpsilonParser.PrintContext ctx) { 
		lineCount++;
		ind.add("PRINT ");
	}
	
	@Override 
	public void enterDefinitionDeclaration(@NotNull EpsilonParser.DefinitionDeclarationContext ctx) { 
		lineCount++;
		ind.add("DEFN " + ctx.IDENTIFIER());
		if(ctx.definitionParameters().identifierDeclaration() != null){
			for(EpsilonParser.IdentifierDeclarationContext obj : ctx.definitionParameters().identifierDeclaration()){
				System.out.println(obj.IDENTIFIER());
				lineCount++;
				ind.add("SAVE " + obj.IDENTIFIER());
			}
		}		
	}
	
	@Override 
	public void exitDefinitionDeclaration(@NotNull EpsilonParser.DefinitionDeclarationContext ctx) {
		lineCount++;
		ind.add("EXITDEFN ");
	}
	
	@Override 
	public void exitDefinitionInvocation(@NotNull EpsilonParser.DefinitionInvocationContext ctx) {
		lineCount++;
		System.out.println(ctx.IDENTIFIER());
		ind.add("INVOKE " + ctx.IDENTIFIER());
	}
	
	@Override 
	public void enterDataType(@NotNull EpsilonParser.DataTypeContext ctx) {
		lineCount++;
		if(ctx.NUMERIC() != null){
			ind.add("PUSH " + ctx.NUMERIC());
		}
		else if (ctx.BOOL()!=null) {
			ind.add("PUSH " + ctx.BOOL());
		}
		else{
			ind.add("PUSH " + ctx.IDENTIFIER());
		}
	}
	
	@Override 
	public void exitDefinitionReturn(@NotNull EpsilonParser.DefinitionReturnContext ctx) {
		lineCount++;
		if(ctx.IDENTIFIER()!=null){
			ind.add("RETURN " + ctx.IDENTIFIER());
		}
		else if (ctx.NUMERIC()!=null) {
			ind.add("RETURN " + ctx.NUMERIC());
		}
		else if (ctx.BOOL() != null) {
			ind.add("RETURN " + ctx.BOOL());
		}
		else
		{
			ind.add("SAVE temp");
			lineCount++;
			ind.add("RETURN temp");
		}
	}
	
	


	
	
	
}