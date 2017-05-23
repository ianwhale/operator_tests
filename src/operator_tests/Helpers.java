package operator_tests;


import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodNode;
import java.util.HashMap;
import java.util.Map;

public class Helpers {
    /**
     * Cloning a method requires the labels to be copied (if they have any).
     * @author https://github.com/NOVA-Team/NOVA-Core
     *         ExE-Boss@github.com, calclavia@github.com
     * @param method MethodNode
     * @return labelMap Map
     */
    public static Map<LabelNode, LabelNode> cloneLabels(MethodNode method) {
        HashMap<LabelNode, LabelNode> labelMap = new HashMap<LabelNode, LabelNode>();
        for (AbstractInsnNode node = method.instructions.getFirst(); node != null; node = node.getNext()) {
            if (node.getType() == AbstractInsnNode.LABEL) {
                labelMap.put((LabelNode)node, new LabelNode());
            }
        }

        return labelMap;
    }
}
