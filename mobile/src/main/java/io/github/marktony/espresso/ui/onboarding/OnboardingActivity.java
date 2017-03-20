package io.github.marktony.espresso.ui.onboarding;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import io.github.marktony.espresso.R;
import io.github.marktony.espresso.mvp.packages.MainActivity;

public class OnboardingActivity extends AppCompatActivity {

    private OnboardingPagerAdapter pagerAdapter;

    private ViewPager viewPager;
    private AppCompatButton buttonFinish;
    private ImageButton buttonPre;
    private ImageButton buttonNext;
    private ImageView[] indicators;

    private int bgColors[];

    private int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        initViews();

        initData();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int colorUpdate = (Integer) new ArgbEvaluator().evaluate(positionOffset, bgColors[position], bgColors[position == 2 ? position : position + 1]);
                viewPager.setBackgroundColor(colorUpdate);
            }

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
                updateIndicators(position);
                viewPager.setBackgroundColor(bgColors[position]);
                buttonPre.setVisibility(position == 0 ? View.GONE : View.VISIBLE);
                buttonNext.setVisibility(position == 2 ? View.GONE : View.VISIBLE);
                buttonFinish.setVisibility(position == 2 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        buttonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OnboardingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPosition += 1;
                viewPager.setCurrentItem(currentPosition, true);
            }
        });

        buttonPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPosition -= 1;
                viewPager.setCurrentItem(currentPosition, true);
            }
        });

    }

    private void initViews() {
        pagerAdapter = new OnboardingPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(pagerAdapter);
        buttonFinish = (AppCompatButton) findViewById(R.id.buttonFinish);
        buttonNext = (ImageButton) findViewById(R.id.imageButtonNext);
        buttonPre = (ImageButton) findViewById(R.id.imageButtonPre);
        indicators = new ImageView[] {(ImageView) findViewById(R.id.imageViewIndicator0),
                (ImageView) findViewById(R.id.imageViewIndicator1),
                (ImageView) findViewById(R.id.imageViewIndicator2)};
    }

    private void initData() {
        bgColors = new int[]{ContextCompat.getColor(this, R.color.colorPrimary),
                ContextCompat.getColor(this, R.color.cyan_500),
                ContextCompat.getColor(this, R.color.light_blue_500)};
    }

    private void updateIndicators(int position) {
        for (int i = 0; i < indicators.length; i++) {
            indicators[i].setBackgroundResource(
                    i == position ? R.drawable.onboarding_indicator_selected : R.drawable.onboarding_indicator_unselected
            );
        }
    }

}