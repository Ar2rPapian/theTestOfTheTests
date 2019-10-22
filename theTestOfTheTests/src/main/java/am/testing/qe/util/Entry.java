package am.testing.qe.util;

import static java.lang.String.format;

public class Entry {

    public final String title;
    public final String slug;
    public final String markdownText;
    public final String text;

    private Entry(String title, String slug, String markdownText, String text) {
        this.title = title;
        this.slug = slug;
        this.markdownText = markdownText;
        this.text = text;
    }


    public static Entry provideRandomEntry(String prefix) {
        int currentRandom = (int) (Math.random() * 100000);
        String entryTitle = format("%sEntryTitle%d", prefix, currentRandom);
        String entrySlug = format("%sEntrySlug%d", prefix, currentRandom);
        String textMarkdown = format("%sMarkdownText%d", prefix, currentRandom);
        String text = format("%sText%d", prefix, currentRandom);
        return new Entry(entryTitle, entrySlug, textMarkdown, text);
    }
}
