package tt432.millennium.client.markdown;

import com.mojang.logging.LogUtils;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataKeyBase;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.slf4j.Logger;

import java.util.Map;

/**
 * @author DustW
 */
public class MarkdownRenderer {
    private static final Logger LOGGER = LogUtils.getLogger();

    public MarkdownRenderer(String markdown) {
        MutableDataSet options = new MutableDataSet();
        Parser parser = Parser.builder(options).build();
        Document document = parser.parse(markdown);
        Map<? extends DataKeyBase<?>, Object> all = document.getAll();
    }

    public static void main(String[] args) {
        String input = """
                ![title]
                超能力入门
                                
                ![brief]
                在通过能力开发机进行学习后，每个人都会根据自己的个人现实被赋予一种能力。你需要不断学习和使用技能，锻炼自身对能力的掌握程度，才能成为强力的超能力者。
                                
                ![content]
                # 学习能力
                                
                你可以通过**能力开发机**或者数据终端的**技能树App**来检查自身的能力学习情况。在技能树中，你可以看到当前的能力等级、当前等级的升级情况以及所有可见技能的学习情况。
                                
                ![技能树](academy:textures/tutorial/skill_tree_ui.png)
                                
                ## 学习技能
                                
                你可以在能力开发机中看到当前有可能学习的所有技能，不同于已经学习的技能，它们被显示为灰色。你的首个技能无须任何其他要求便可习得，但是后续的技能会对你的能力等级、使用的能力开发机、其他技能的熟练度提出一定的前置要求。每次学习技能将会消耗一定的虚能。
                                
                ## 熟练度
                                
                在不断使用技能的过程中，技能的熟练度会缓慢提升。当技能的熟练度超过特定值的时候，可能会触发特定的里程碑，让技能具有更强的效果！
                                
                ## 升级
                                
                在你不断使用能力的过程中，你的最大CP（计算力）值会不断升高，当计算力值达到当前等级的临界点时，你便可以升级到下一等级了。你的升级进度会在能力开发机中被标注出来。升级需要消耗大量的虚能，并且高等级的升级行为可能需要更高级的能力开发机。
                                
                # 超能力使用
                                
                ## 基本
                                
                你可以通过__预设系统__来轻松的管理大量技能的操作，并且随时更换你的技能操作设置。
                                
                你需要通过__能力激活键__ ![key id="ability_activation"] 开启能力使用。开启能力使用后，游戏的UI会发生一定的变化：
                                
                ![能力模式(部分)](academy:textures/tutorial/ability_ui.png)
                                
                右上方的__计算力条__可以让你实时检查你的计算力和过载状态。
                                
                右方的__键位提示__可以让你知道当前各个键位所对应的能力。
                                
                ## 预设系统
                                
                预设系统可以让你管理各个操作键位所对应的能力。一个玩家可以管理4份预设，并且随时在它们之间轮换。
                                
                你可以使用 ![key id="edit_preset"] 键来打开预设编辑界面。
                                
                ![预设编辑界面](academy:textures/tutorial/preset_selection_ui.png)
                                
                在能力激活的状态下，通过使用 ![key id="switch_preset"] 来切换当前预设。你可以看到UI上的键位提示发生相应的变化。
                                
                P.S. 超能力所使用的四个键位可以通过数据终端中的__设置App__来编辑。
                                
                ## CP和过载值
                                
                每次使用技能时，都会消耗一定量的CP（计算力）。计算力值衡量了你大脑因使用能力而产生的疲劳程度。计算力的消耗和恢复都较为缓慢。
                                
                同时，超能力使用也会产生短时间内急剧的负荷，它以__过载__的形式被表现出来，显示在CP条的下方。当过载超过上限时，你将不得不停止使用能力，直到冷却恢复。
                                
                ![过载时的界面](academy:textures/tutorial/overload.png)
                                
                ## 能力重置
                                
                利用高级能力开发机、高压磁增幅线圈和能力诱导因子即可完成能力重置。
                                
                将你想要重置为的能力的诱导因子放在物品栏中，使用高压磁增幅线圈打开高级能力开发机。在出现的控制台界面加载完成后，输入“reset”即可进行重置。
                                
                重置完成后，能力会变为诱导因子所具有的能力，能力等级会降一级，所以能力重置要求你至少应达到异能力者（level 2）。
                                
                """;

        MutableDataSet options = new MutableDataSet();
        Parser parser = Parser.builder(options).build();
        Document document = parser.parse(input);
        iterateNode(document, 0);
    }

    static void iterateNode(Node node, int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append("  ".repeat(Math.max(0, depth)));
        depth++;
        LOGGER.info("{}{}", sb, node);
        Node child = Node.AST_ADAPTER.getFirstChild(node);
        while(child != null) {
            iterateNode(child, depth);
            child = Node.AST_ADAPTER.getNext(child);
        }
    }
}
