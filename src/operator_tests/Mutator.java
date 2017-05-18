package operator_tests;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Random;

import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;

/**
 * Mutator does the modifying, so it extends the visitor class. 
 */
public class Mutator extends MethodVisitor implements Opcodes {
	private final MethodNode methodNode; //< When the code is viewed as a tree, the method being visited is a node. 
	private static final String CLASSFILE = "/home/ian/eclipse_projects/operator_tests/bin/operator_tests/Test.class";
	private static final int SEED = 1;
	private static final Random rand = new Random(SEED);
	private int linenumber; //< Line number to mutate. 
	
	public Mutator(final MethodVisitor mv, final int access, final String name,
            final String desc, final String signature, final String[] exceptions) {
		super(ASM5, mv);
		this.methodNode = new MethodNode(access, name, desc, signature, exceptions);
		this.linenumber = 10;
		
		// Figure out how to properly iterate over and print out instructions...
		System.out.println(this.methodNode.instructions);
	}
	
	public static void main(String[] args) throws Exception {
		FileInputStream in = new FileInputStream(CLASSFILE);
		
		ClassReader reader = new ClassReader(in);
		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
		ClassVisitor visitor = new ClassVisitorAdapter(writer);
		reader.accept(visitor, 0);
		
		FileOutputStream out = new FileOutputStream(CLASSFILE + 1);
		out.write(writer.toByteArray());
		out.close();
	}
}

/**
 * Normally, ClassVisitor.visitMethod returns a MethodVisitor, but we want a Mutator in order to change some bytecode. 
 */
class ClassVisitorAdapter extends ClassVisitor implements Opcodes {
	public ClassVisitorAdapter(final ClassVisitor visitor) {
		super(ASM5, visitor);
	}
	
	/**
	 * Override visitMethod to return a Mutator. 
	 * @return null | MethodVisitor mv, actually a Mutator.
	 */
	@Override
	public MethodVisitor visitMethod(final int access, final String name,
            final String desc, final String signature, final String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        return mv == null ? null : new Mutator(mv, access, name, desc, signature, exceptions);
	}
}