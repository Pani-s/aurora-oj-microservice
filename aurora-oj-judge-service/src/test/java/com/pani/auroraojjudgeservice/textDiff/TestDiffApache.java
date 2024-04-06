package com.pani.auroraojjudgeservice.textDiff;

import org.apache.commons.text.diff.EditScript;
import org.apache.commons.text.diff.StringsComparator;

/**
 * @author Pani
 * @date Created in 2024/4/5 10:00
 * @description
 */
public class TestDiffApache {
    public static void main(String[] args) {
        StringsComparator cmp = new StringsComparator("ABCFGH", "ABCFGH");
        EditScript<Character> script = cmp.getScript();
        int mod = script.getModifications();
        System.out.println(mod);
    }
}
