package operator_tests;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

import java.io.FileInputStream;
import java.io.IOException;

public class Copier {

    private static final String CLASSFILE = "/home/ian/gi/operator_tests/out/production/operator_tests/operator_tests/Test.class";

    /**
     * Test cloning an entire class then writing it out.
     * @param args String[]
     */
    public static void main(String[] args) throws IOException {
        FileInputStream in = new FileInputStream(CLASSFILE);

        ClassReader reader = new ClassReader(in);
        ClassNode visitor = new ClassNode();
        reader.accept(visitor, 0);

        ClassNode copy = Helpers.cloneClass(visitor);
    }
}
