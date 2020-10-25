package jp.co.olv.choi.issuerappclonemvvm.web;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestApiTask extends AsyncTask<Integer, Void, List<commentsResponse>> {

    @Override
    protected List<commentsResponse> doInBackground(Integer... postId){
        RequestApiService apiService = HttpClient.getRequestApiService();

        apiService.getCommentsByPostId(postId[0]).enqueue(new Callback<List<commentsResponse>>() {
            @Override
            public void onResponse(Call<List<commentsResponse>> call, Response<List<commentsResponse>> response) {
                if (response.isSuccessful()) {
                    Log.d("succeeded", response.toString());
                } else {
                    Log.e("failed", response.toString());
                }
            }

            @Override
            public void onFailure(Call<List<commentsResponse>> call, Throwable t) {
            }
        });

        try {
            return apiService.getCommentsByPostId(postId[0]).execute().body();
        } catch (IOException e) {
        }
        return null;
    }
}