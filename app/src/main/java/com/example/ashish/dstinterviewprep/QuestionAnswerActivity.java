package com.example.ashish.dstinterviewprep;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QuestionAnswerActivity extends AppCompatActivity {
    TextView questionAnswer;
    Button next, previous;
    ImageView refresh;
    LinearLayout adLayout;
    String url = "https://learncodeonline.in/api/android/datastructure.json";
    List<QuestionsAnswers> questionsAnswersList = new ArrayList<>();
    ProgressDialog pd;
    int position = 0;
    String webUrl = "https://courses.learncodeonline.in";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_answer);
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        adLayout = findViewById(R.id.adv_layout_ques);
        questionAnswer = findViewById(R.id.question_answer);
        next = findViewById(R.id.next);
        previous = findViewById(R.id.previous);
        refresh = findViewById(R.id.refresh);
        pd = new ProgressDialog(this);
        pd.setMessage("Please Wait...");
        pd.setTitle("Loading..");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loadData();
        adLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWebsite(webUrl);
            }
        });
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position++;
                if (position >= questionsAnswersList.size()) {
                    position = questionsAnswersList.size() - 1;
                    showQuestionAnswers(position);
                } else {
                    showQuestionAnswers(position);
                }
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position--;
                if (position < 0) {
                    position = 0;
                    showQuestionAnswers(position);
                } else {

                    showQuestionAnswers(position);
                }
            }
        });
    }

    void showQuestionAnswers(int position) {
        int questionNumber = position + 1;
        String question = questionsAnswersList.get(position).getQuestions();
        String answer = questionsAnswersList.get(position).getAnswers();
        String quesAns = "Question no. " + questionNumber +
                "\n\n" + question + "\n\n\n" + answer;
        questionAnswer.setText(quesAns);
        questionAnswer.setGravity(Gravity.CENTER);
    }

    private void showWebsite(String url) {

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    private void loadData() {

        pd.show();
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        refresh.setVisibility(View.GONE);
                        adLayout.setVisibility(View.VISIBLE);

                        // Display the first 500 characters of the response string.
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray array = object.getJSONArray("questions");
                            for (int i = 0; i < array.length(); i++) {

                                JSONObject jo = array.getJSONObject(i);

                                QuestionsAnswers questionsAnswers = new QuestionsAnswers(
                                        jo.getString("question"),
                                        jo.getString("Answer"));
                                questionsAnswersList.add(questionsAnswers);
                            }
                            pd.dismiss();
                            showQuestionAnswers(0);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(QuestionAnswerActivity.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();
                questionAnswer.setText(error.toString());
                questionAnswer.setGravity(Gravity.CENTER);
                pd.dismiss();
                refresh.setVisibility(View.VISIBLE);
                adLayout.setVisibility(View.GONE);

            }
        });
        RequestQueue queue = Volley.newRequestQueue(QuestionAnswerActivity.this);
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

}
