# LaTeX extension

<MavenBadge :artifact="'ext-latex'" />

This is an extension that will help you display LaTeX formulas in your markdown.
Syntax is pretty simple: pre-fix and post-fix your latex with `$$` (double dollar sign).
`$$` should be the first characters in a line.

```markdown
$$
\\text{A long division \\longdiv{12345}{13}
$$
```

```markdown
$$\\text{A long division \\longdiv{12345}{13}$$
```

```java
Markwon.builder(context)
    .use(ImagesPlugin.create(context))
    .use(JLatexMathPlugin.create(new Config(textSize))
    .build();
```

This extension uses [jlatexmath-android](https://github.com/noties/jlatexmath-android) artifact to create LaTeX drawable. Then it
registers special `latex` image scheme handler and uses `AsyncDrawableLoader` to display
final result

## Config

```java
public static class Config {

    protected final float textSize;

    protected Drawable background;

    @JLatexMathDrawable.Align
    protected int align = JLatexMathDrawable.ALIGN_CENTER;

    protected boolean fitCanvas = true;

    protected int padding;
}
```