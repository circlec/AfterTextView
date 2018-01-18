package com.zc.aftertextview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView tv = findViewById(R.id.tv);
//        final TextView viewAfter = findViewById(R.id.tv1);
        final RelativeLayout rl = findViewById(R.id.rl_content);
        final ImageView viewAfter = findViewById(R.id.iv);
        setAfterText(tv, viewAfter, rl);
        tv.setText("测试用文字多几个！测试用文字多几个！测试用文字多几个！测试用文字多几个！测试用文字多几个！测试用文字多几个！测试用文字多几个！");
    }

    private void setAfterText(final TextView tv, final View viewAfter, final RelativeLayout rl) {
        ViewTreeObserver viewTreeObserver = tv.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                RelativeLayout.LayoutParams lastLayoutParams = (RelativeLayout.LayoutParams) viewAfter.getLayoutParams();
                int topMargin = lastLayoutParams.topMargin;
                int leftMargin = lastLayoutParams.leftMargin;
                if (topMargin != 0 || leftMargin != 0) return;
                Layout layout = tv.getLayout();
                int lineCount = tv.getLineCount();
                if (lineCount < 1) return;
                int tvHeight = tv.getHeight();
                int lineHeight = tvHeight / lineCount;
                int viewAfterHeight = viewAfter.getHeight();
                int spaceHeight = (lineCount - 1) * lineHeight + (lineHeight - viewAfterHeight) / 2;
                int lastLineWidth = (int) layout.getLineWidth(lineCount - 1);
                int afterWidth = viewAfter.getWidth();
                int rlWidth = rl.getWidth();
                int emptyWidth = rlWidth - lastLineWidth;
                if (emptyWidth > afterWidth) {
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) viewAfter.getLayoutParams();
                    layoutParams.setMargins(lastLineWidth, spaceHeight, 0, 0);
                    viewAfter.setLayoutParams(layoutParams);
                } else {
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) viewAfter.getLayoutParams();
                    layoutParams.setMargins(0, tvHeight, 0, 0);
                    viewAfter.setLayoutParams(layoutParams);
                }
            }
        });
    }
}
