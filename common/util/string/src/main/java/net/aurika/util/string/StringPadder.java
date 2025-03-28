package net.aurika.util.string;

import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * A class that pads words and componenets in a sentence to have the same spacing.
 * 1. (Bob) (is) (going) (to) (work).
 * 2. (Jack) (is) (not going) (to) (the hospital).
 * <p>
 * Padding: (Note: the following docs spacing will not show correctly in preview mode)
 * <p>
 * 1. Bob  is going     to work.
 * 2. Jack is not going to the hospital.
 */
public final class StringPadder {

  private final @NotNull List<Sentence> sentences = new ArrayList<>();
  private int expectedSentenceWordCount = -1;

  public StringPadder() {
  }

  public @NotNull StringPadder pad(@NotNull Object @NotNull ... words) {
    Objects.requireNonNull(words);
    if (words.length == 0) {
      throw new IllegalArgumentException("Cannot pad sentence with no words");
    } else if (this.expectedSentenceWordCount != -1 && words.length != this.expectedSentenceWordCount) {
      String var10000 = "Expected sentence word count " + this.expectedSentenceWordCount + " but this one is " + words.length + " (" +
          Arrays.toString(words) + ')';
      throw new IllegalArgumentException(var10000);
    } else {
      this.expectedSentenceWordCount = words.length;
      Collection<String> destination$iv$iv = new ArrayList<>(words.length);
      int var7 = 0;

      for (int var8 = words.length; var7 < var8; ++var7) {
        Object item$iv$iv = words[var7];
        destination$iv$iv.add(item$iv$iv.toString());
      }

      List<String> var14 = CollectionsKt.toMutableList(destination$iv$iv);
      this.sentences.add(new Sentence(var14));
      return this;
    }
  }

  public @NotNull List<String> getPadded() {
    if (this.expectedSentenceWordCount == -1) {
      String var16 = "No sentences added to pad (" + this.sentences + ')';
      throw new IllegalArgumentException(var16);
    } else {
      int i = 0;

      for (int var2 = this.expectedSentenceWordCount; i < var2; ++i) {
        ArrayList<String> words = new ArrayList<>(this.sentences.size());

        for (Sentence sentence : this.sentences) {
          words.add(sentence.getWords().get(i));
        }

        Iterator<String> iterator$iv = words.iterator();
        if (!iterator$iv.hasNext()) {
          throw new NoSuchElementException();
        }

        String maxElem$iv = iterator$iv.next();
        String var10000;
        if (!iterator$iv.hasNext()) {
          var10000 = maxElem$iv;
        } else {
          String it = maxElem$iv;
          int maxValue$iv = it.length();

          while (true) {
            String e$iv = iterator$iv.next();
            int v$iv = e$iv.length();
            if (maxValue$iv < v$iv) {
              maxElem$iv = e$iv;
              maxValue$iv = v$iv;
            }

            if (!iterator$iv.hasNext()) {
              var10000 = maxElem$iv;
              break;
            }
          }
        }

        int maxLength = var10000.length();

        for (Sentence sentence : this.sentences) {
          String word = sentence.getWords().get(i);
          if (word.length() < maxLength) {
            String padded = StringsKt.padEnd(word, maxLength, ' ');
            sentence.getWords().set(i, padded);
          }
        }
      }

      List<Sentence> $this$map$iv = this.sentences;
      List<String> destination$iv$iv = new ArrayList<>($this$map$iv.size());

      for (Sentence it : $this$map$iv) {
        destination$iv$iv.add(CollectionsKt.joinToString(it.getWords(), "", "", "", -1, "...", null));
      }

      return destination$iv$iv;
    }
  }

  @NotNull
  public String getPaddedString(@NotNull String separator) {
    Objects.requireNonNull(separator);
    return CollectionsKt.joinToString(this.getPadded(), separator, "", "", -1, "...", null);
  }

  /**
   * @deprecated
   */
  @Deprecated
  @NotNull
  public String toString() {
    throw new UnsupportedOperationException("Use getPadded() instead");
  }

  private static final class Sentence {

    @NotNull
    private List<String> words;

    public Sentence(@NotNull List<String> words) {
      Objects.requireNonNull(words);
      this.words = words;
    }

    @NotNull
    public List<String> getWords() {
      return this.words;
    }

    public void setWords(@NotNull List<String> var1) {
      Objects.requireNonNull(var1);
      this.words = var1;
    }

  }

}
