package jp.co.olv.choi.issuerappclonemvvm.Taplayout;

import jp.co.olv.choi.issuerappclonemvvm.realm.PayDetail;

public interface OnItemCheckListener {
    void onItemCheck(PayDetail item);
    void onItemUncheck(PayDetail item);
}