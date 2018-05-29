package es.xdesert3agle.crplayerinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import es.xdesert3agle.crplayerinfo.apiclasses.Player;
import es.xdesert3agle.crplayerinfo.util.SectionsPageAdapter;

public class TabContainer extends AppCompatActivity {

    private static final String TAG = "TabContainer";

    private ViewPager mViewPager;

    private SectionsPageAdapter mSectionsPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_container);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mViewPager = findViewById(R.id.container);
        setUpViewPager(mViewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setUpViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        Player player = getIntent().getExtras().getParcelable("playerObj");

        Bundle bundle = new Bundle();
        bundle.putParcelable("playerObj", player);

        PlayerInfo playerInfoFragment = new PlayerInfo();
        playerInfoFragment.setArguments(bundle);

        CardUpgrader cardUpgraderFragment = new CardUpgrader();
        cardUpgraderFragment.setArguments(bundle);

        adapter.addFragment(playerInfoFragment, player.getName());
        adapter.addFragment(cardUpgraderFragment, "Niveles");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(TabContainer.this, PlayerTagInput.class);
        intent.putExtra("fromTabContainer", "asd");
        startActivity(intent);
    }
}