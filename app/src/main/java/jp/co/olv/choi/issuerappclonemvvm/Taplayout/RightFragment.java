package jp.co.olv.choi.issuerappclonemvvm.Taplayout;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import jp.co.olv.choi.issuerappclonemvvm.RecyclerView.PayDetailRecyclerViewAdapter;
import jp.co.olv.choi.issuerappclonemvvm.PayDetailViewModel;
import jp.co.olv.choi.issuerappclonemvvm.R;
import jp.co.olv.choi.issuerappclonemvvm.realm.LiveRealmData;
import jp.co.olv.choi.issuerappclonemvvm.realm.PayDetail;

public class RightFragment extends Fragment {

    private PayDetailViewModel payDetailViewModel;

    @BindView(R.id.PayDetailRecyclerView)
    RecyclerView recyclerView;

    public RightFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_right, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        // ViewModelのインスタンス生成
        payDetailViewModel = ViewModelProviders.of(this).get(PayDetailViewModel.class);

        // 支払明細情報の更新を監視
        payDetailViewModel.getAll(2).observe(this, new Observer<RealmResults<PayDetail>>() {
            @Override
            public void onChanged(@Nullable RealmResults<PayDetail> payDetails) {
                Realm realm = Realm.getDefaultInstance();
                List<PayDetail> payDetailList = realm.copyFromRealm(payDetails);
                PayDetailRecyclerViewAdapter recyclerViewAdapter = new PayDetailRecyclerViewAdapter(payDetailList);
                recyclerView.setAdapter(recyclerViewAdapter);
            }
        });

        // RecyclerViewにデータ反映
        LiveRealmData<PayDetail> payDetailsLiveData = payDetailViewModel.getAll(2);
        RealmResults<PayDetail> payDetails = payDetailsLiveData.getResults();
        Realm realm = Realm.getDefaultInstance();
        List<PayDetail> payDetailList = realm.copyFromRealm(payDetails);
        PayDetailRecyclerViewAdapter recyclerViewAdapter = new PayDetailRecyclerViewAdapter(payDetailList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);

        super.onViewCreated(view, savedInstanceState);
    }
}