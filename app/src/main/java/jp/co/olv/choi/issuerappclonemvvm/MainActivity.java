package jp.co.olv.choi.issuerappclonemvvm;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import jp.co.olv.choi.issuerappclonemvvm.Taplayout.MyFragmentPagerAdapter;
import jp.co.olv.choi.issuerappclonemvvm.realm.MyMigration;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ButterKnife連携
        ButterKnife.bind(this);
        // Realm初期化
        Realm.init(this);
        Realm.setDefaultConfiguration(new RealmConfiguration.Builder()
                .schemaVersion(0)
                .migration(new MyMigration())
                .build());

        // ViewPagerとTabLayout初期化
        MyFragmentPagerAdapter fragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(fragmentPagerAdapter);
        // タブ切替時にキーボードが消えるようにする
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                if (getCurrentFocus() != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }

            @Override
            public void onPageSelected(int i) {}

            @Override
            public void onPageScrollStateChanged(int i) {}
        });
        tabLayout.setupWithViewPager(viewPager);

    }
}