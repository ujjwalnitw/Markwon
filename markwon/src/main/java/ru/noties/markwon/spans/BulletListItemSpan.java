package ru.noties.markwon.spans;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.text.style.LeadingMarginSpan;

import ru.noties.markwon.utils.LeadingMarginUtils;

public class BulletListItemSpan implements LeadingMarginSpan {

    private MarkwonTheme theme;

    private final Paint paint = ObjectsPool.paint();
    private final RectF circle = ObjectsPool.rectF();
    private final Rect rectangle = ObjectsPool.rect();

    private final int level;

    public BulletListItemSpan(
            @NonNull MarkwonTheme theme,
            @IntRange(from = 0) int level) {
        this.theme = theme;
        this.level = level;
    }

    @Override
    public int getLeadingMargin(boolean first) {
        return theme.getBlockMargin();
    }

    @Override
    public void drawLeadingMargin(Canvas c, Paint p, int x, int dir, int top, int baseline, int bottom, CharSequence text, int start, int end, boolean first, Layout layout) {

        // if there was a line break, we don't need to draw anything
        if (!first
                || !LeadingMarginUtils.selfStart(start, text, this)) {
            return;
        }

        paint.set(p);

        theme.applyListItemStyle(paint);

        final int save = c.save();
        try {

            final int width = theme.getBlockMargin();

            // @since 1.0.6 we no longer rely on (bottom-top) calculation in order to detect line height
            // it lead to bad rendering as first & last lines received different results even
            // if text size is the same (first line received greater amount and bottom line -> less)
            final int textLineHeight = (int) (paint.descent() - paint.ascent() + .5F);

            final int side = theme.getBulletWidth(textLineHeight);

            final int marginLeft = (width - side) / 2;

            // in order to support RTL
            final int l;
            final int r;
            {
                final int left = x + (dir * marginLeft);
                final int right = left + (dir * side);
                l = Math.min(left, right);
                r = Math.max(left, right);
            }

            final int t = baseline + (int) ((paint.descent() + paint.ascent()) / 2.F + .5F) - (side / 2);
            final int b = t + side;

            if (level == 0
                    || level == 1) {

                circle.set(l, t, r, b);

                final Paint.Style style = level == 0
                        ? Paint.Style.FILL
                        : Paint.Style.STROKE;
                paint.setStyle(style);

                c.drawOval(circle, paint);
            } else {

                rectangle.set(l, t, r, b);

                paint.setStyle(Paint.Style.FILL);

                c.drawRect(rectangle, paint);
            }

        } finally {
            c.restoreToCount(save);
        }
    }
}
