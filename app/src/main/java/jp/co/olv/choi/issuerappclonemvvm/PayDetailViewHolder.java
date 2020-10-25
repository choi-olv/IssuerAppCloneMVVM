package jp.co.olv.choi.issuerappclonemvvm;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PayDetailViewHolder extends ViewHolder {

    @BindView(R.id.detail)
    public TextView detailView;

    public PayDetailViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}