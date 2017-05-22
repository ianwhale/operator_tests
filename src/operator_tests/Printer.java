package operator_tests;

import java.io.FileInputStream;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.util.List;
import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.util.*;


public class Printer {
	
	private static final String CLASSFILE = "/home/ian/eclipse_projects/operator_tests/bin/operator_tests/Test.class";
	
	public static void main(String[] args) throws Exception {
		FileInputStream in = new FileInputStream(CLASSFILE);

		ClassReader reader = new ClassReader(in);
		ClassNode classNode = new ClassNode();
		reader.accept(classNode, 0);

		List<MethodNode> methods = classNode.methods;
		for (MethodNode method : methods) {
			InsnList insnList = method.instructions;
			System.out.println(method.name);
			for (int i = 0; i < insnList.size(); i++) {
				System.out.print(insnToString(insnList.get(i)));
			}
		}
	}
		
	private static String insnToString(AbstractInsnNode insn) {
		insn.accept(methodPrinter);
		StringWriter writer = new StringWriter();
		printer.print(new PrintWriter(writer));
		printer.getText().clear();
		return writer.toString();
	}
		
	private static Textifier printer = new Textifier();
	private static TraceMethodVisitor methodPrinter = new TraceMethodVisitor(printer);

}
