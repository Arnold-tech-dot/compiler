package boa.datagen.util.python3;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;


import boa.datagen.util.python3.*;
import boa.datagen.util.python3.Python3Parser.And_exprContext;
import boa.datagen.util.python3.Python3Parser.And_testContext;
import boa.datagen.util.python3.Python3Parser.AnnassignContext;
import boa.datagen.util.python3.Python3Parser.ArglistContext;
import boa.datagen.util.python3.Python3Parser.ArgumentContext;
import boa.datagen.util.python3.Python3Parser.Arith_exprContext;
import boa.datagen.util.python3.Python3Parser.Assert_stmtContext;
import boa.datagen.util.python3.Python3Parser.Async_funcdefContext;
import boa.datagen.util.python3.Python3Parser.Async_stmtContext;
import boa.datagen.util.python3.Python3Parser.AtomContext;
import boa.datagen.util.python3.Python3Parser.Atom_exprContext;
import boa.datagen.util.python3.Python3Parser.AugassignContext;
import boa.datagen.util.python3.Python3Parser.Break_stmtContext;
import boa.datagen.util.python3.Python3Parser.CalldefContext;
import boa.datagen.util.python3.Python3Parser.ClassdefContext;
import boa.datagen.util.python3.Python3Parser.Comp_forContext;
import boa.datagen.util.python3.Python3Parser.Comp_ifContext;
import boa.datagen.util.python3.Python3Parser.Comp_iterContext;
import boa.datagen.util.python3.Python3Parser.Comp_opContext;
import boa.datagen.util.python3.Python3Parser.ComparisonContext;
import boa.datagen.util.python3.Python3Parser.Compound_stmtContext;
import boa.datagen.util.python3.Python3Parser.Continue_stmtContext;
import boa.datagen.util.python3.Python3Parser.DecoratedContext;
import boa.datagen.util.python3.Python3Parser.DecoratorContext;
import boa.datagen.util.python3.Python3Parser.DecoratorsContext;
import boa.datagen.util.python3.Python3Parser.Del_stmtContext;
import boa.datagen.util.python3.Python3Parser.DictorsetmakerContext;
import boa.datagen.util.python3.Python3Parser.Dotted_as_nameContext;
import boa.datagen.util.python3.Python3Parser.Dotted_as_namesContext;
import boa.datagen.util.python3.Python3Parser.Dotted_nameContext;
import boa.datagen.util.python3.Python3Parser.Encoding_declContext;
import boa.datagen.util.python3.Python3Parser.Eval_inputContext;
import boa.datagen.util.python3.Python3Parser.Except_clauseContext;
import boa.datagen.util.python3.Python3Parser.ExprContext;
import boa.datagen.util.python3.Python3Parser.Expr_stmtContext;
import boa.datagen.util.python3.Python3Parser.ExprlistContext;
import boa.datagen.util.python3.Python3Parser.FactorContext;
import boa.datagen.util.python3.Python3Parser.File_inputContext;
import boa.datagen.util.python3.Python3Parser.Flow_stmtContext;
import boa.datagen.util.python3.Python3Parser.For_stmtContext;
import boa.datagen.util.python3.Python3Parser.FuncdefContext;
import boa.datagen.util.python3.Python3Parser.Global_stmtContext;
import boa.datagen.util.python3.Python3Parser.If_stmtContext;
import boa.datagen.util.python3.Python3Parser.Import_as_nameContext;
import boa.datagen.util.python3.Python3Parser.Import_as_namesContext;
import boa.datagen.util.python3.Python3Parser.Import_fromContext;
import boa.datagen.util.python3.Python3Parser.Import_nameContext;
import boa.datagen.util.python3.Python3Parser.Import_stmtContext;
import boa.datagen.util.python3.Python3Parser.LambdefContext;
import boa.datagen.util.python3.Python3Parser.Lambdef_nocondContext;
import boa.datagen.util.python3.Python3Parser.Nonlocal_stmtContext;
import boa.datagen.util.python3.Python3Parser.Not_testContext;
import boa.datagen.util.python3.Python3Parser.Or_testContext;
import boa.datagen.util.python3.Python3Parser.ParametersContext;
import boa.datagen.util.python3.Python3Parser.Pass_stmtContext;
import boa.datagen.util.python3.Python3Parser.PowerContext;
import boa.datagen.util.python3.Python3Parser.Raise_stmtContext;
import boa.datagen.util.python3.Python3Parser.Return_stmtContext;
import boa.datagen.util.python3.Python3Parser.Shift_exprContext;
import boa.datagen.util.python3.Python3Parser.Simple_stmtContext;
import boa.datagen.util.python3.Python3Parser.Single_inputContext;
import boa.datagen.util.python3.Python3Parser.SliceopContext;
import boa.datagen.util.python3.Python3Parser.Small_stmtContext;
import boa.datagen.util.python3.Python3Parser.Star_exprContext;
import boa.datagen.util.python3.Python3Parser.StmtContext;
import boa.datagen.util.python3.Python3Parser.SubscriptContext;
import boa.datagen.util.python3.Python3Parser.SubscriptlistContext;
import boa.datagen.util.python3.Python3Parser.SuiteContext;
import boa.datagen.util.python3.Python3Parser.TermContext;
import boa.datagen.util.python3.Python3Parser.TestContext;
import boa.datagen.util.python3.Python3Parser.Test_nocondContext;
import boa.datagen.util.python3.Python3Parser.TestlistContext;
import boa.datagen.util.python3.Python3Parser.Testlist_compContext;
import boa.datagen.util.python3.Python3Parser.Testlist_star_exprContext;
import boa.datagen.util.python3.Python3Parser.TfpdefContext;
import boa.datagen.util.python3.Python3Parser.TrailerContext;
import boa.datagen.util.python3.Python3Parser.Try_stmtContext;
import boa.datagen.util.python3.Python3Parser.TypedargslistContext;
import boa.datagen.util.python3.Python3Parser.VarargslistContext;
import boa.datagen.util.python3.Python3Parser.VfpdefContext;
import boa.datagen.util.python3.Python3Parser.While_stmtContext;
import boa.datagen.util.python3.Python3Parser.With_itemContext;
import boa.datagen.util.python3.Python3Parser.With_stmtContext;
import boa.datagen.util.python3.Python3Parser.Xor_exprContext;
import boa.datagen.util.python3.Python3Parser.Yield_argContext;
import boa.datagen.util.python3.Python3Parser.Yield_exprContext;
import boa.datagen.util.python3.Python3Parser.Yield_stmtContext;
import boa.types.Ast.Method;
import boa.types.Ast.Namespace;
import boa.types.Ast.PositionInfo;
import boa.types.Ast.Variable;
public class Python3Visitor implements Python3Listener{
	Python3Parser parser;
	Python3Lexer lexer;
	
	private String src = null;
	
	private PositionInfo.Builder pos = null;
	private Namespace.Builder b = Namespace.newBuilder();
	private List<boa.types.Ast.Comment> comments = new ArrayList<boa.types.Ast.Comment>();
	private List<String> imports = new ArrayList<String>();
	private Stack<boa.types.Ast.Expression> expressions = new Stack<boa.types.Ast.Expression>();
	protected Stack<List<boa.types.Ast.Variable>> fields = new Stack<List<boa.types.Ast.Variable>>();
	private Stack<List<boa.types.Ast.Method>> methods = new Stack<List<boa.types.Ast.Method>>();
	private Stack<List<boa.types.Ast.Statement>> statements = new Stack<List<boa.types.Ast.Statement>>();

	
	public Namespace getNamespaces() {
		return b.build();
	}
	
	public Python3Visitor(String src) {
		this.src = src;
	}
	public Python3Visitor() {
	}
	private static String readFile(File file, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(file.toPath());
        return new String(encoded, encoding);
    }

    public Python3Parser parsefile(File file) throws IOException {
        String code = readFile(file, Charset.forName("UTF-8"));
        return parse(code);
    }
    
    public Python3Parser parse(String code) {
    	lexer = new Python3Lexer(new ANTLRInputStream(code));

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        parser = new Python3Parser(tokens);

        return parser;
    }
    
	public void visit(String source) {
        parser = parse(source);
        System.out.println("Entered visitor");
		try {
			ParseTreeWalker.DEFAULT.walk(this, parser.file_input());
		}
		catch(Exception e) {
			System.out.println("Error");
			e.printStackTrace();
		}
	}
	
	public void visit(File file) {
		try {
			parser = parsefile(file);
			ParseTreeWalker.DEFAULT.walk(this, parser.file_input());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void enterEveryRule(ParserRuleContext arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitEveryRule(ParserRuleContext arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitErrorNode(ErrorNode arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitTerminal(TerminalNode arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterSingle_input(Single_inputContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitSingle_input(Single_inputContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterFile_input(File_inputContext ctx) {
		// TODO Auto-generated method stub
		String pkg = "";
		b.setName(pkg);
	}

	@Override
	public void exitFile_input(File_inputContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterEval_input(Eval_inputContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitEval_input(Eval_inputContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterDecorator(DecoratorContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitDecorator(DecoratorContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterDecorators(DecoratorsContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitDecorators(DecoratorsContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterDecorated(DecoratedContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitDecorated(DecoratedContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterAsync_funcdef(Async_funcdefContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitAsync_funcdef(Async_funcdefContext ctx) {
		// TODO Auto-generated method stub
		
	}

	Method.Builder mb;
	@Override
	public void enterFuncdef(FuncdefContext ctx) {
		// TODO Auto-generated method stub
		
		methods.push(new ArrayList<boa.types.Ast.Method>());
		mb = Method.newBuilder();
		b.setName(ctx.NAME().getText());
		
		
	}

	
	@Override
	public void exitFuncdef(FuncdefContext ctx) {
		// TODO Auto-generated method stub
		for (boa.types.Ast.Method m : methods.pop())
			b.addMethods(m);
		
	}

	
	
	@Override
	public void enterParameters(ParametersContext ctx) {
		// TODO Auto-generated method stub
		
		
		
	}

	@Override
	public void exitParameters(ParametersContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterTypedargslist(TypedargslistContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitTypedargslist(TypedargslistContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterTfpdef(TfpdefContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitTfpdef(TfpdefContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterVarargslist(VarargslistContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitVarargslist(VarargslistContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterVfpdef(VfpdefContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitVfpdef(VfpdefContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterStmt(StmtContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitStmt(StmtContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterSimple_stmt(Simple_stmtContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitSimple_stmt(Simple_stmtContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterSmall_stmt(Small_stmtContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitSmall_stmt(Small_stmtContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterExpr_stmt(Expr_stmtContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitExpr_stmt(Expr_stmtContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterAnnassign(AnnassignContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitAnnassign(AnnassignContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterTestlist_star_expr(Testlist_star_exprContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitTestlist_star_expr(Testlist_star_exprContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterAugassign(AugassignContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitAugassign(AugassignContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterDel_stmt(Del_stmtContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitDel_stmt(Del_stmtContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterPass_stmt(Pass_stmtContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitPass_stmt(Pass_stmtContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterFlow_stmt(Flow_stmtContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitFlow_stmt(Flow_stmtContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterBreak_stmt(Break_stmtContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitBreak_stmt(Break_stmtContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterContinue_stmt(Continue_stmtContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitContinue_stmt(Continue_stmtContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterReturn_stmt(Return_stmtContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitReturn_stmt(Return_stmtContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterYield_stmt(Yield_stmtContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitYield_stmt(Yield_stmtContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterRaise_stmt(Raise_stmtContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitRaise_stmt(Raise_stmtContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterImport_stmt(Import_stmtContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitImport_stmt(Import_stmtContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterImport_name(Import_nameContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitImport_name(Import_nameContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterImport_from(Import_fromContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitImport_from(Import_fromContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterImport_as_name(Import_as_nameContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitImport_as_name(Import_as_nameContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterDotted_as_name(Dotted_as_nameContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitDotted_as_name(Dotted_as_nameContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterImport_as_names(Import_as_namesContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitImport_as_names(Import_as_namesContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterDotted_as_names(Dotted_as_namesContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitDotted_as_names(Dotted_as_namesContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterDotted_name(Dotted_nameContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitDotted_name(Dotted_nameContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterGlobal_stmt(Global_stmtContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitGlobal_stmt(Global_stmtContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterNonlocal_stmt(Nonlocal_stmtContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitNonlocal_stmt(Nonlocal_stmtContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterAssert_stmt(Assert_stmtContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitAssert_stmt(Assert_stmtContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterCompound_stmt(Compound_stmtContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitCompound_stmt(Compound_stmtContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterAsync_stmt(Async_stmtContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitAsync_stmt(Async_stmtContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterIf_stmt(If_stmtContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitIf_stmt(If_stmtContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterWhile_stmt(While_stmtContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitWhile_stmt(While_stmtContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterFor_stmt(For_stmtContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitFor_stmt(For_stmtContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterTry_stmt(Try_stmtContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitTry_stmt(Try_stmtContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterWith_stmt(With_stmtContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitWith_stmt(With_stmtContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterWith_item(With_itemContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitWith_item(With_itemContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterExcept_clause(Except_clauseContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitExcept_clause(Except_clauseContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterSuite(SuiteContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitSuite(SuiteContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterTest(TestContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitTest(TestContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterTest_nocond(Test_nocondContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitTest_nocond(Test_nocondContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterLambdef(LambdefContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitLambdef(LambdefContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterLambdef_nocond(Lambdef_nocondContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitLambdef_nocond(Lambdef_nocondContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterOr_test(Or_testContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitOr_test(Or_testContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterAnd_test(And_testContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitAnd_test(And_testContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterNot_test(Not_testContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitNot_test(Not_testContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterComparison(ComparisonContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitComparison(ComparisonContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterComp_op(Comp_opContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitComp_op(Comp_opContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterStar_expr(Star_exprContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitStar_expr(Star_exprContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterExpr(ExprContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitExpr(ExprContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterXor_expr(Xor_exprContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitXor_expr(Xor_exprContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterAnd_expr(And_exprContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitAnd_expr(And_exprContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterShift_expr(Shift_exprContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitShift_expr(Shift_exprContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterArith_expr(Arith_exprContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitArith_expr(Arith_exprContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterTerm(TermContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitTerm(TermContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterFactor(FactorContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitFactor(FactorContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterPower(PowerContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitPower(PowerContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterAtom_expr(Atom_exprContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitAtom_expr(Atom_exprContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterAtom(AtomContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitAtom(AtomContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterTestlist_comp(Testlist_compContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitTestlist_comp(Testlist_compContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterTrailer(TrailerContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitTrailer(TrailerContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterSubscriptlist(SubscriptlistContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitSubscriptlist(SubscriptlistContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterSubscript(SubscriptContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitSubscript(SubscriptContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterSliceop(SliceopContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitSliceop(SliceopContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterExprlist(ExprlistContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitExprlist(ExprlistContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterTestlist(TestlistContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitTestlist(TestlistContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterDictorsetmaker(DictorsetmakerContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitDictorsetmaker(DictorsetmakerContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterClassdef(ClassdefContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitClassdef(ClassdefContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterArglist(ArglistContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitArglist(ArglistContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterArgument(ArgumentContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitArgument(ArgumentContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterComp_iter(Comp_iterContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitComp_iter(Comp_iterContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterComp_for(Comp_forContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitComp_for(Comp_forContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterComp_if(Comp_ifContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitComp_if(Comp_ifContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterEncoding_decl(Encoding_declContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitEncoding_decl(Encoding_declContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterYield_expr(Yield_exprContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitYield_expr(Yield_exprContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterYield_arg(Yield_argContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitYield_arg(Yield_argContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterCalldef(CalldefContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitCalldef(CalldefContext ctx) {
		// TODO Auto-generated method stub
		
	}
}