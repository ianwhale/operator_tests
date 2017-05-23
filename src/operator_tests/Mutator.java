package operator_tests;

import java.io.File;
import java.util.List;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Random;
import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;

public class Mutator implements Opcodes {
    private static final String CLASSFILE = "/home/ian/gi/operator_tests/out/production/operator_tests/operator_tests/Test.class";
    private static final File FILEOBJ = new File(CLASSFILE);
    private static final Random rand = new Random();

    /**
     * Simulates "mutation".
     * The only change for more randomness would be to choose a random instruction.
     *      -- This would require a little more work to handle all instructions.
     *      -- However, if we only want to mutate in existing code in the file, this would be much easier (InsnNode implements Clone).
     */
    public static void main(String[] args) throws Exception {
        FileInputStream in = new FileInputStream(CLASSFILE);

		ClassReader reader = new ClassReader(in);
		ClassNode visitor = new ClassNode();
		reader.accept(visitor, 0);

		List<MethodNode> methods = visitor.methods;
        for (MethodNode method : methods) {
            if (! method.name.equals("<init>")) { // Ignore constructor (nothing going on).
                Mutator.insertion(method);
//                switch(rand.nextInt(3)) {
//                    case 0:
//                        Mutator.insertion(method);
//                    case 1:
//                        Mutator.deletion(method);
//                    case 2:
//                        Mutator.replacement(method);
//                }
            }
        }

        ClassWriter writer = new ClassWriter(0);
        visitor.accept(writer);

		FileOutputStream out = new FileOutputStream("./out/experiments/" + FILEOBJ.getName());
		out.write(writer.toByteArray());
		out.close();
    }

    /**
     * Insertion mutation, insert an instruction after a random index.
     * @param method MethodNode
     */
    private static void insertion(MethodNode method) {
        Mutator.insertion(method, rand.nextInt(method.instructions.size()));
    }

    /**
     * Perform an insertion after a specific position.
     *
     * TODO:: Choose a random instruction (consider how to choose args for multi-argument instructions).
     *
     * @param method MethodNode
     * @param position int
     */
    public static void insertion(MethodNode method, int position) {
        method.instructions.insert(method.instructions.get(position), new InsnNode(ICONST_3));
    }

    /**
     * Deletes an instruction at a random position.
     * @param method MethodNode
     */
    public static void deletion(MethodNode method) {
        Mutator.deletion(method, rand.nextInt(method.instructions.size()));
    }

    /**
     * Deletes an instruction at the given position.
     * @param method MethodNode
     * @param position int
     */
    public static void deletion(MethodNode method, int position) {
        method.instructions.remove(method.instructions.get(position));
    }

    /**
     * Replace a random instruction with an instruction found in some other part of the method.
     * @param method MethodNode
     */
    public static void replacement(MethodNode method) {
        Mutator.replacement(method, rand.nextInt(method.instructions.size()));
    }

    /**
     * Replace an instruction at the given position with an instruction found randomly somewhere else inside method.
     * @param method MethodNode
     * @param position int
     */
    public static void replacement(MethodNode method, int position) {
        AbstractInsnNode instruction = method.instructions.get(rand.nextInt(method.instructions.size()));
        Mutator.replacement(method, position, instruction);
    }

    /**
     * Replace an instruction at the given position with the given instruction (does not have to be from inside method).
     *      -- Unknown interaction: instruction passed in is a pointer to the original rather than a clone.
     * @param method MethodNode
     * @param position int
     * @param instruction AbstractInsnNode, should be cloned (?)
     */
    public static void replacement(MethodNode method, int position, AbstractInsnNode instruction) {
        method.instructions.set(method.instructions.get(position), instruction);
    }
}