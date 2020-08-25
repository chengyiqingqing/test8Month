package com.meitu.testviewbyid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.meitu.testviewbyid.target.OnViewClick;
import com.meitu.testviewbyid.target.ViewById;
import com.meitu.testviewbyid.target.ViewUtils;

public class MainActivity extends AppCompatActivity {

    @ViewById(R.id.test_textView)
    TextView test_textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "" + test_textView.getText().toString(), Toast.LENGTH_SHORT).show();
    }

    @OnViewClick(R.id.test_textView)
    public void onTestClick(View view){
        Toast.makeText(this, "haha " + test_textView.getText().toString(), Toast.LENGTH_SHORT).show();
    }

}
