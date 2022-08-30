package tt432.millennium.client.markdown;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;
import icyllis.modernui.graphics.drawable.ImageDrawable;
import icyllis.modernui.graphics.font.FontPaint;
import icyllis.modernui.text.SpannableString;
import icyllis.modernui.text.Spanned;
import icyllis.modernui.text.style.StyleSpan;
import icyllis.modernui.view.ViewGroup;
import icyllis.modernui.widget.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import tt432.millennium.common.packs.SimpleMarkdownResourceReloadListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static icyllis.modernui.view.View.dp;

/**
 * @author DustW
 */
public class MarkdownManager extends SimpleMarkdownResourceReloadListener {
    public static final MarkdownManager INSTANCE = new MarkdownManager();

    Map<ResourceLocation, Document> unprocessed = new HashMap<>();

    private MarkdownManager() {
        super("markdown");
    }

    @Override
    protected void apply(Map<ResourceLocation, String> pObject, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        MutableDataSet options = new MutableDataSet();
        Parser parser = Parser.builder(options).build();
        pObject.forEach((r, s) -> unprocessed.put(r, parser.parse(s.replace("__", "**"))));
    }

    public ScrollView get(ResourceLocation fileName) {
        ScrollView view = new ScrollView();
        LinearLayout layout = new LinearLayout();
        layout.setOrientation(LinearLayout.VERTICAL);
        iterateNode(unprocessed.get(fileName), 0, layout);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int m = dp(10);
        params.setMargins(m, m, m, m);
        view.addView(layout, params);

        startContent = false;

        return view;
    }

    static void iterateNode(Node node, int depth, LinearLayout view) {
        processorNode(node, depth, view);

        depth++;
        Node child = Node.AST_ADAPTER.getFirstChild(node);

        while (child != null) {
            iterateNode(child, depth, view);
            child = Node.AST_ADAPTER.getNext(child);
        }
    }

    static record Style(Object o, int start, int end, int flag) {}

    static Node lastNode;
    static List<Style> styles = new ArrayList<>();
    static String paragraph = "";
    static boolean startContent;
    static LinearLayout in = new LinearLayout();

    static void processorNode(Node node, int depth, LinearLayout view) {
        if (depth == 1) {
            if (node instanceof Heading) {
                lastNode = node;
                view.addView(heading(node));
                return;
            }

            if (lastNode != node && startContent) {
                lastNode = node;
                paragraph += SOFT_LINE_BREAK;
                TextView textView = new TextView();
                SpannableString sps = new SpannableString(paragraph);
                styles.forEach(s -> sps.setSpan(s.o, s.start, s.end, s.flag));
                textView.setText(sps);
                in.addView(textView);

                styles.clear();
                paragraph = "";

                view.addView(in);
                in = new LinearLayout();
            }
        } else if (depth == 2) {
            if (startContent) {
                if (lastNode instanceof Heading) {
                    return;
                }

                if (node instanceof Text) {
                    paragraph += text(node);
                } else if (node instanceof StrongEmphasis) {
                    int s = paragraph.length();
                    paragraph += strongEmphasis(node);
                    int e = paragraph.length();
                    styles.add(new Style(new StyleSpan(FontPaint.BOLD), s, e, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE));
                } else if (node instanceof SoftLineBreak) {
                    paragraph += SOFT_LINE_BREAK;
                } else if (node instanceof Image) {
                    TextView textView = new TextView();
                    textView.setText(paragraph);
                    in.addView(textView);
                    paragraph = "";

                    ImageView imageView = new ImageView();
                    imageView.setImageDrawable(image(node));
                    view.addView(imageView);
                }
            }
            else if (node instanceof RefNode i && i.getReference().toString().equals("content")) {
                startContent = true;
            }
        }
    }

    static String strongEmphasis(Node node) {
        Node text = node.getFirstChild();
        if (text != null)
            return text(text);
        return "";
    }

    static String text(Node node) {
        return node.getChars().toString();
    }

    public static final String SOFT_LINE_BREAK = "\n";

    static ImageDrawable image(Node node) {
        ResourceLocation name = new ResourceLocation(((LinkNodeBase) node).getUrl().toString());
        String path = name.getPath();

        if (path.startsWith("textures/"))
            path = path.substring(9);

        return new ImageDrawable(name.getNamespace(), path);
    }

    static TextView heading(Node node) {
        TextView textView = new TextView();
        textView.setTextSize(textView.getTextSize() * (1 + ((7 - ((Heading) node).getLevel()) * .1F)));
        textView.setText(((Heading) node).getText());
        return textView;
    }
}
