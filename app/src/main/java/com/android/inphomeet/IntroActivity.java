package com.android.inphomeet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {
 FirebaseAuth fAuth;
    private OnboardingAdapter onboardingAdapter;
    private LinearLayout layoutOnboardingIndicators;
    private MaterialButton buttonOnboardingAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        fAuth = FirebaseAuth.getInstance();
        if(fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
            finish();
        }
        layoutOnboardingIndicators = findViewById(R.id.layoutOnboadingIndicators);
        setupOnboardingitems();

        final ViewPager2 onboardingViewPager = findViewById(R.id.onboardingViewPager);
        onboardingViewPager.setAdapter(onboardingAdapter);

        buttonOnboardingAction =findViewById(R.id.buttonOnboardingAction);
            setupOnboardingIndicators();
            setCurrentOnboardingIndicator(0);
            onboardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    setCurrentOnboardingIndicator(position);
                }
            });

            buttonOnboardingAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onboardingViewPager.getCurrentItem() + 1 < onboardingAdapter.getItemCount()){
                        onboardingViewPager.setCurrentItem(onboardingViewPager.getCurrentItem() + 1);
                    }else {
                        startActivity(new Intent(getApplicationContext(),WelcomeActivity.class));
                        finish();
                    }
                }
            });



    }
    private void setupOnboardingitems(){
        List<Onboardingitem> onboardingitems =new ArrayList<>();

        Onboardingitem itemfreepower = new Onboardingitem();
        itemfreepower.setTitle("Free and Powerfull");
        itemfreepower.setDescription("Free platform for all without any ads or limits.");
        itemfreepower.setImage(R.drawable.safesecure);

        Onboardingitem itemsafeprivate = new Onboardingitem();
        itemsafeprivate.setTitle("Safe and Private");
        itemsafeprivate.setDescription("Safe and secure with Encypted data which is never disclosed.");
        itemsafeprivate.setImage(R.drawable.dataleak);

        Onboardingitem itemslimitrange = new Onboardingitem();
        itemslimitrange.setTitle("Limit Range Share");
        itemslimitrange.setDescription("User can share their location's limited range which can help user to notify about their circle.");
        itemslimitrange.setImage(R.drawable.loacationshare);

        Onboardingitem itemcloudbased = new Onboardingitem();
        itemcloudbased.setTitle("Cloud-based");
        itemcloudbased.setDescription("Your chats are synced and cloud based which will save your data.");
        itemcloudbased.setImage(R.drawable.cloudbased);

        onboardingitems.add(itemfreepower);
        onboardingitems.add(itemsafeprivate);
        onboardingitems.add(itemslimitrange);
        onboardingitems.add(itemcloudbased);

        onboardingAdapter =new OnboardingAdapter(onboardingitems);

    }

    private void setupOnboardingIndicators(){
        ImageView[] indicators = new ImageView[onboardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8,0,8,0);
        for(int i = 0; i<indicators.length; i++){
            indicators[i]= new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.onboarding_indicator_active
            ));
            indicators[i].setLayoutParams(layoutParams);
            layoutOnboardingIndicators.addView(indicators[i]);
        }
    }
    @SuppressLint("SetTextI18n")
    private void setCurrentOnboardingIndicator(int index){
        int childCount = layoutOnboardingIndicators.getChildCount();
        for(int i=0; i < childCount;i++){
            ImageView imageView = (ImageView) layoutOnboardingIndicators.getChildAt(i);
            if(i==index){
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_active)
                );
            } else {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_inactive)
                );
            }
        }
        if(index == onboardingAdapter.getItemCount() - 1){
            buttonOnboardingAction.setText("Start");
        }else if(index == onboardingAdapter.getItemCount() -2) {
            buttonOnboardingAction.setText("Go");
        }
        else if(index == onboardingAdapter.getItemCount() -3) {
            buttonOnboardingAction.setText("Set");
        }else {
            buttonOnboardingAction.setText("Get");
        }
    }
}
////Free and Powerfull  Free platform for all without any ads or limits//
////Safe and Private   Safe and secure with Encypted data which is never disclosed//
////Limit Range Share  User can share their Location's Limited Range which can help user to notify about their circle//
////Cloud-based Your chats are synced and cloud based which will save your data//