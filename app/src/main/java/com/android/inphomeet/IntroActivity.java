package com.android.inphomeet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    private OnboardingAdapter onboardingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        setupOnboardingitems();

        ViewPager2 onboardingViewPager = findViewById(R.id.onboardingViewPager);
        onboardingViewPager.setAdapter(onboardingAdapter);
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
}
//Free and Powerfull  Free platform for all without any ads or limits
//Safe and Private   Safe and secure with Encypted data which is never disclosed
//Limit Range Share  User can share their Location's Limited Range which can help user to notify about their circle
//Cloud-based Your chats are synced and cloud based which will save your data
//