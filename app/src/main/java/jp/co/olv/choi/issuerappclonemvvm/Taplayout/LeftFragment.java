package jp.co.olv.choi.issuerappclonemvvm.Taplayout;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.co.olv.choi.issuerappclonemvvm.PayDetailViewModel;
import jp.co.olv.choi.issuerappclonemvvm.R;

public class LeftFragment extends Fragment {

    private PayDetailViewModel payDetailViewModel;

    @BindView(R.id.parameter)
    EditText editText;
    @BindView(R.id.executeButton)
    Button executeButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_left, container, false);
        // ButterKnife連携
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // ViewModelのインスタンス生成
        payDetailViewModel = ViewModelProviders.of(this).get(PayDetailViewModel.class);

        // パラメータ入力欄の改行キー押下の動作追加
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // 改行キー押下後、キーボード表示をオフにし、API実行ボタンを押下する
                if(event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    executeButton.performClick();
                    return true;
                }
                // 改行キー押下でなければ、通常のキー押下時の処理をする
                return false;
            }
        });
    }

    // パラメータを指定してAPI実行
    @OnClick(R.id.executeButton) void executeApiWithParameter() {

        String postId = editText.getText().toString();
        if (!postId.isEmpty()) {
            RightFragment rightFragment = (RightFragment)getFragmentManager().getFragments().get(1);
            payDetailViewModel.getAll(Integer.parseInt(postId), rightFragment.getCurrentSelectedItems());

            // 表示をRecyclerViewのあるタブに切り替える
            TabLayout tabLayout = ButterKnife.findById(getActivity(), R.id.tabs);
            tabLayout.getTabAt(1).select();
        }
    }

    // RecyclerViewのあるタブでチェックした項目を削除
    @OnClick(R.id.removeButtonInLeftTap) void removeCheckedItem() {
        RightFragment rightFragment = (RightFragment)getFragmentManager().getFragments().get(1);
        payDetailViewModel.delete(rightFragment.getCurrentSelectedItems());

        // 表示をRecyclerViewのあるタブに切り替える
        TabLayout tabLayout = ButterKnife.findById(getActivity(), R.id.tabs);
        tabLayout.getTabAt(1).select();
    }
}