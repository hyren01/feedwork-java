package fd.ng.core.yaml.lineproc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * YamlLines default implementation.
 */
public class DefaultYamlLinesProcessor extends AbstractYamlLinesProcessor {

    /**
     * YamlFactory lines.
     */
    private Collection<YamlLine> lines;

    /**
     * Ctor.
     * @param lines YamlFactory lines collection.
     */
    public DefaultYamlLinesProcessor(final Collection<YamlLine> lines) {
        this.lines = lines;
    }

    /**
     * Returns an iterator over these YamlFactory lines.
     * It <b>only</b> iterates over the lines which are at the same
     * level of indentation with the first!
     * @return Iterator over these yaml lines.
     */
    @Override
    public Iterator<YamlLine> iterator() {
        Iterator<YamlLine> iterator = this.lines.iterator();
        if (iterator.hasNext()) {
            final List<YamlLine> sameIndentation = new ArrayList<>();
            final YamlLine first = iterator.next();
            sameIndentation.add(first); // 把该级别的第一行存下来
            while (iterator.hasNext()) {
                YamlLine current = iterator.next(); // 取下一行
                if(current.indentation() == first.indentation()) {  // 把与第一行缩进一样的行都存下来
                    sameIndentation.add(current);
                }
            }
            iterator = sameIndentation.iterator(); // 返回的iterator是相同级别（相同缩进）的所有行
        }
        return iterator;
    }

    @Override
    public AbstractYamlLinesProcessor nested(final int after) {
        final List<YamlLine> nestedLines = new ArrayList<>();
        YamlLine start = null;
        for(final YamlLine line : this.lines) {
            if(line.number() == after) {
                start = line;
            }
            if(line.number() > after) {
                if(line.indentation() > start.indentation()) {
                    nestedLines.add(line);
                } else {
                    break;
                }
            }
        }
        return new DefaultYamlLinesProcessor(nestedLines);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (final YamlLine line : this.lines) {
            builder.append(line.toString()).append("\n");
        }
        return builder.toString();
    }

    @Override
    public int count() {
        return this.lines.size();
    }

    @Override
    public String indent(final int indentation) {
        final StringBuilder indented = new StringBuilder();
        final Iterator<YamlLine> linesIt = this.lines.iterator();
        if(linesIt.hasNext()) {
            final YamlLine first = linesIt.next();
            if (first.indentation() == indentation) {
                indented.append(first.toString()).append("\n");
                while (linesIt.hasNext()) {
                    indented.append(linesIt.next().toString()).append("\n");
                }
            } else {
                final int offset = indentation - first.indentation();
                for (final YamlLine line : this.lines) {
                    int correct = line.indentation() + offset;
                    while (correct > 0) {
                        indented.append(" ");
                        correct--;
                    }
                    indented.append(line.trimmed()).append("\n");
                }
            }
        }
        return indented.toString();
    }


}
