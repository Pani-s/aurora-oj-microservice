package com.pani.auroraojjudgeservice.textDiff;

import com.github.difflib.text.DiffRow;
import com.github.difflib.text.DiffRowGenerator;
import org.assertj.core.util.diff.Delta;
import org.assertj.core.util.diff.DiffUtils;
import org.assertj.core.util.diff.Patch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Pani
 * @date Created in 2024/4/5 10:09
 * @description
 */
public class DiffExample {
    public static void main(String[] args) {
        List<String> originalLines = new ArrayList<>();
        originalLines.add("qqqqqqqqqqq");
        List<String> revisedLines = new ArrayList<>();
        revisedLines.add("qqqqqqqaqqq");

        // 计算差异
        Patch<String> patch = DiffUtils.diff(originalLines, revisedLines);
        for (Delta<String> delta : patch.getDeltas()) {
            System.out.println(delta);
        }

        DiffRowGenerator generator = DiffRowGenerator.create()
                .showInlineDiffs(true)
                .inlineDiffByWord(false)
                .oldTag(f -> "~")
                //introduce markdown style for strikethrough
                .newTag(f -> "**")
                //introduce markdown style for bold
                .build();
//        List<DiffRow> rows = generator.generateDiffRows(
//                Arrays.asList("This is a test senctence.", "This is the second line.", "And here is the finish."),
//                Arrays.asList("This is a test for diffutils.", "This is the second line."));
        List<DiffRow> rows = generator.generateDiffRows(
                Collections.singletonList("This is a test senctence.\nafffefeqf4w11"),
                Collections.singletonList("This is a test for diffutils.\nafffefeqf4w22"));

        System.out.println("|original|new|");
        System.out.println("|--------|---|");
        for (DiffRow row : rows) {
            System.out.println("|" + row.getOldLine() + "|" + row.getNewLine() + "|");
        }

    }
}

