package operator_tests;

import java.io.File;
import java.util.List;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Random;

import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;

public class Mutator implements Opcodes {
    private static final String CLASSFILE = "/home/ian/eclipse_projects/operator_tests/bin/operator_tests/Test.class";
    private static final File FILEOBJ = new File(CLASSFILE);
    private static final Random rand = new Random();

    /**
     * Simulates an insertion "mutation".
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
                // Insert an instruction at a random place.
                int position = rand.nextInt(method.instructions.size());
                method.instructions.insert(method.instructions.get(position), new InsnNode(ICONST_3)); // ICONST_3 means "push a 3 on the stack".
            }
        }

        ClassWriter writer = new ClassWriter(0);
        visitor.accept(writer);

		FileOutputStream out = new FileOutputStream("./" + FILEOBJ.getName());
		out.write(writer.toByteArray());
		out.close();

		Runtime.getRuntime().exec("java Test");
    }
}