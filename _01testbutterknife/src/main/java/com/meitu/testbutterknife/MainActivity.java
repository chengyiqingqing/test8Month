package com.meitu.testbutterknife;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.text_bindView)
    TextView textBindView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 1.首先将ButterKnife注入,这行代码要在setContentView()之后执行。
        ButterKnife.setDebug(true);
        ButterKnife.bind(this);
    }

    // 3.
    @OnClick(R.id.text_bindView)
    public void ontext_bindViewClick() {
        Toast.makeText(this, "点击1", Toast.LENGTH_SHORT).show();
    }


}
