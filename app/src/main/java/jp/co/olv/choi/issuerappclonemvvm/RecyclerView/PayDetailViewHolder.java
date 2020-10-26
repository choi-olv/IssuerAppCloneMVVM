package jp.co.olv.choi.issuerappclonemvvm.RecyclerView;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.co.olv.choi.issuerappclonemvvm.R;

public class PayDetailViewHolder extends ViewHolder {

    @BindView(R.id.detail_item_shop_name)
    TextView shopNameView;
    @BindView(R.id.detail_item_amount)
    TextView amountView;
    @BindView(R.id.detail_item_pay_date)
    TextView payDateView;
    @BindView(R.id.detail_item_pay_count)
    TextView payCountView;

    public PayDetailViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}