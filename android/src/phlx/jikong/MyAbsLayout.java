package phlx.jikong;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public class MyAbsLayout extends ViewGroup 
{
    public MyAbsLayout(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();

        int maxHeight = 0;
        int maxWidth = 0;

        // Find out how big everyone wants to be
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        // Find rightmost and bottom-most child
        for (int i = 0; i < count; i++) 
        {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE) 
            {
                int childRight;
                int childBottom;

                MyAbsLayout.LayoutParams lp
                        = (MyAbsLayout.LayoutParams) child.getLayoutParams();

                childRight = lp.x + child.getMeasuredWidth();
                childBottom = lp.y + child.getMeasuredHeight();

                maxWidth = Math.max(maxWidth, childRight);
                maxHeight = Math.max(maxHeight, childBottom);
            }
        }

        maxWidth += this.getPaddingLeft() + getPaddingRight();
        maxHeight += this.getPaddingTop() + this.getPaddingBottom();
        
        // Account for padding too
        //maxWidth += mPaddingLeft + mPaddingRight;
        //maxHeight += mPaddingTop + mPaddingBottom;

        // Check against minimum height and width
        maxHeight = Math.max(maxHeight, getSuggestedMinimumHeight());
        maxWidth = Math.max(maxWidth, getSuggestedMinimumWidth());
        
        setMeasuredDimension(resolveSize(maxWidth, widthMeasureSpec),
                resolveSize(maxHeight, heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) 
    {
        int count = getChildCount();

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE) {

                MyAbsLayout.LayoutParams lp =
                        (MyAbsLayout.LayoutParams) child.getLayoutParams();

                //int childLeft = mPaddingLeft + lp.x;
                //int childTop = mPaddingTop + lp.y;
                
                int childLeft = getPaddingLeft() + lp.x;
                int childTop = getPaddingTop() + lp.y;
                child.layout(childLeft, childTop,
                        childLeft + child.getMeasuredWidth(),
                        childTop + child.getMeasuredHeight());

            }
        }
    }
    
   
    // Override to allow type-checking of LayoutParams. 
    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof MyAbsLayout.LayoutParams;
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    /**
     * Per-child layout information associated with MyAbsLayout.
     * See
     * {@link android.R.styleable#AbsoluteLayout_Layout Absolute Layout Attributes}
     * for a list of all child view attributes that this class supports.
     */
    public static class LayoutParams extends ViewGroup.LayoutParams {
        /**
         * The horizontal, or X, location of the child within the view group.
         */
        public int x;
        /**
         * The vertical, or Y, location of the child within the view group.
         */
        public int y;

        /**
         * Creates a new set of layout parameters with the specified width,
         * height and location.
         *
         * @param width the width, either {@link #MATCH_PARENT},
                  {@link #WRAP_CONTENT} or a fixed size in pixels
         * @param height the height, either {@link #MATCH_PARENT},
                  {@link #WRAP_CONTENT} or a fixed size in pixels
         * @param x the X location of the child
         * @param y the Y location of the child
         */
        public LayoutParams(int width, int height, int x, int y) {
            super(width, height);
            this.x = x;
            this.y = y;
        }
                
        /**
         * {@inheritDoc}
         */
        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }
}
