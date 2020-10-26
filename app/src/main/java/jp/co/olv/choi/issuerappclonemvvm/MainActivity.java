package jp.co.olv.choi.issuerappclonemvvm;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import jp.co.olv.choi.issuerappclonemvvm.Taplayout.MyFragmentPagerAdapter;
import jp.co.olv.choi.issuerappclonemvvm.realm.MyMigration;

public class MainActivity extends AppCompatActivity {

    private  PayDetailViewModel payDetailViewModel;

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

        MyFragmentPagerAdapter fragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(fragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}