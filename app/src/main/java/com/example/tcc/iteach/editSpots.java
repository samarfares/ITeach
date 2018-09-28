package com.example.tcc.iteach;


import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class editSpots extends AppCompatActivity implements View.OnClickListener {
    private boolean individual6;
    private boolean individual7;
    private boolean individual8;
    private boolean individual9;
    private boolean individual10;
    private boolean individual11;
    private boolean individual12;
    private boolean individual13;
    private boolean individual14;
    private boolean individual15;
    private boolean individual16;
    private boolean individual17;
    private boolean individual18;
    private boolean individual19;
    private boolean individual20;
    private boolean individual21;
    private boolean individual22;
    private boolean individual23;


    private Button button6g ;
    private Button button6i;
    private Button button7g ;
    private Button button7i;
    private Button button8g;
    private Button button8i ;
    private Button button9g;
    private Button button9i;
    private Button button10g ;
    private Button button10i ;
    private Button button11g ;
    private Button button11i ;
    private Button button12g ;
    private Button button12i ;
    private Button button13g ;
    private Button button13i ;
    private Button button14g ;
    private Button button14i ;
    private Button button15g ;
    private Button button15i ;
    private Button button16g ;
    private Button button16i ;
    private Button button17g ;
    private Button button17i ;
    private Button button18g ;
    private Button button18i ;
    private Button button19g ;
    private Button button19i ;
    private Button button20g ;
    private Button button20i ;
    private Button button21g ;
    private Button button21i ;
    private Button button22g ;
    private Button button22i ;
    private Button button23g ;
    private Button button23i ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_spots);

        button6g = findViewById(R.id.button_group_6);
        button6i = findViewById(R.id.button_individual_6);
        button7g = findViewById(R.id.button_group_7);
        button7i = findViewById(R.id.button_individual_7);
        button8g = findViewById(R.id.button_group_8);
        button8i = findViewById(R.id.button_individual_8);
        button9g = findViewById(R.id.button_group_9);
        button9i = findViewById(R.id.button_individual_9);
        button10g = findViewById(R.id.button_group_10);
        button10i = findViewById(R.id.button_individual_10);
        button11g = findViewById(R.id.button_group_11);
        button11i = findViewById(R.id.button_individual_11);
        button12g = findViewById(R.id.button_group_12);
        button12i = findViewById(R.id.button_individual_12);
        button13g = findViewById(R.id.button_group_13);
        button13i = findViewById(R.id.button_individual_13);
        button14g = findViewById(R.id.button_group_14);
        button14i = findViewById(R.id.button_individual_14);
        button15g = findViewById(R.id.button_group_15);
        button15i = findViewById(R.id.button_individual_15);
        button16g = findViewById(R.id.button_group_16);
        button16i = findViewById(R.id.button_individual_16);
        button17g = findViewById(R.id.button_group_17);
        button17i = findViewById(R.id.button_individual_17);
        button18g = findViewById(R.id.button_group_18);
        button18i = findViewById(R.id.button_individual_18);
        button19g = findViewById(R.id.button_group_19);
        button19i = findViewById(R.id.button_individual_19);
        button20g = findViewById(R.id.button_group_20);
        button20i = findViewById(R.id.button_individual_20);
        button21g = findViewById(R.id.button_group_21);
        button21i = findViewById(R.id.button_individual_21);
        button22g = findViewById(R.id.button_group_22);
        button22i = findViewById(R.id.button_individual_22);
        button23g = findViewById(R.id.button_group_23);
        button23i = findViewById(R.id.button_individual_23);

        button6g.setOnClickListener(this);
        button6i.setOnClickListener(this);
        button7g.setOnClickListener(this);
        button7i.setOnClickListener( this);
        button8g.setOnClickListener( this);
        button8i.setOnClickListener( this);
        button9g.setOnClickListener(this);
        button9i.setOnClickListener( this);
        button10g.setOnClickListener( this);
        button10i.setOnClickListener( this);
        button11g.setOnClickListener( this);
        button11i.setOnClickListener( this);
        button12g.setOnClickListener( this);
        button12i.setOnClickListener( this);
        button13g.setOnClickListener( this);
        button13i.setOnClickListener( this);
        button14g.setOnClickListener( this);
        button14i.setOnClickListener( this);
        button15g.setOnClickListener( this);
        button15i.setOnClickListener( this);
        button16g.setOnClickListener( this);
        button16i.setOnClickListener( this);
        button17g.setOnClickListener(this);
        button17i.setOnClickListener( this);
        button18g.setOnClickListener( this);
        button18i.setOnClickListener( this);
        button19g.setOnClickListener( this);
        button19i.setOnClickListener( this);
        button20g.setOnClickListener( this);
        button20i.setOnClickListener( this);
        button21g.setOnClickListener( this);
        button21i.setOnClickListener(this);
        button22g.setOnClickListener( this);
        button22i.setOnClickListener( this);
        button23g.setOnClickListener( this);
        button23i.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == button6g) {
            button6g.setBackgroundColor(Color.GRAY);
            button6i.setBackgroundResource(android.R.drawable.btn_default);
            individual6 = false;
        }
        if (v == button6i) {
            button6i.setBackgroundColor(Color.GRAY);
            button6g.setBackgroundResource(android.R.drawable.btn_default);
            individual6 = true;
        }
        if (v == button7g) {
            button7g.setBackgroundColor(Color.GRAY);
            button7i.setBackgroundResource(android.R.drawable.btn_default);
            individual7 = false;
        }
        if (v == button7i) {
            button7i.setBackgroundColor(Color.GRAY);
            button7g.setBackgroundResource(android.R.drawable.btn_default);
            individual7 = true;
        }
        if (v == button8g) {
            button8g.setBackgroundColor(Color.GRAY);
            button8i.setBackgroundResource(android.R.drawable.btn_default);
            individual8 = false;
        }
        if (v == button8i) {
            button8i.setBackgroundColor(Color.GRAY);
            button8g.setBackgroundResource(android.R.drawable.btn_default);
            individual8 = true;
        }
        if (v == button9g) {
            button9g.setBackgroundColor(Color.GRAY);
            button9i.setBackgroundResource(android.R.drawable.btn_default);
            individual9 = false;
        }
        if (v == button9i) {
            button9i.setBackgroundColor(Color.GRAY);
            button9g.setBackgroundResource(android.R.drawable.btn_default);
            individual9 = true ;
        }
        if (v == button10g) {
            button10g.setBackgroundColor(Color.GRAY);
            button10i.setBackgroundResource(android.R.drawable.btn_default);
            individual10 = false;
        }
        if (v == button10i) {
            button10i.setBackgroundColor(Color.GRAY);
            button10g.setBackgroundResource(android.R.drawable.btn_default);
            individual10 =true;
        }
        if (v == button11g) {
            button11g.setBackgroundColor(Color.GRAY);
            button11i.setBackgroundResource(android.R.drawable.btn_default);
            individual11 = false;
        }
        if (v == button11i) {
            button11i.setBackgroundColor(Color.GRAY);
            button11g.setBackgroundResource(android.R.drawable.btn_default);
            individual11 = true;
        }
        if (v == button12g) {
            button12g.setBackgroundColor(Color.GRAY);
            button12i.setBackgroundResource(android.R.drawable.btn_default);
            individual12 = false;
        }
        if (v == button12i) {
            button12i.setBackgroundColor(Color.GRAY);
            button12g.setBackgroundResource(android.R.drawable.btn_default);
            individual12=true;
        }
        if (v == button13g) {
            button13g.setBackgroundColor(Color.GRAY);
            button13i.setBackgroundResource(android.R.drawable.btn_default);
            individual13 = false;
        }
        if (v == button13i) {
            button13i.setBackgroundColor(Color.GRAY);
            button13g.setBackgroundResource(android.R.drawable.btn_default);
            individual13 = true;
        }
        if (v == button14g) {
            button14g.setBackgroundColor(Color.GRAY);
            button14i.setBackgroundResource(android.R.drawable.btn_default);
            individual14 = false;
        }
        if (v == button14i) {
            button14i.setBackgroundColor(Color.GRAY);
            button14g.setBackgroundResource(android.R.drawable.btn_default);
            individual14 = true;
        }
        if (v == button15g) {
            button15g.setBackgroundColor(Color.GRAY);
            button15i.setBackgroundResource(android.R.drawable.btn_default);
            individual15 = false;
        }
        if (v == button15i) {
            button15i.setBackgroundColor(Color.GRAY);
            button15g.setBackgroundResource(android.R.drawable.btn_default);
            individual15 = true;
        }
        if (v == button16g) {
            button16g.setBackgroundColor(Color.GRAY);
            button16i.setBackgroundResource(android.R.drawable.btn_default);
            individual16 = false;
        }
        if (v == button16i) {
            button16i.setBackgroundColor(Color.GRAY);
            button16g.setBackgroundResource(android.R.drawable.btn_default);
            individual16 = true;
        }
        if (v == button17g) {
            button17g.setBackgroundColor(Color.GRAY);
            button17i.setBackgroundResource(android.R.drawable.btn_default);
            individual17 = false;
        }
        if (v == button17i) {
            button17i.setBackgroundColor(Color.GRAY);
            button17g.setBackgroundResource(android.R.drawable.btn_default);
            individual17 = true;
        }
        if (v == button18g) {
            button18g.setBackgroundColor(Color.GRAY);
            button18i.setBackgroundResource(android.R.drawable.btn_default);
            individual18 = false;
        }
        if (v == button18i) {
            button18i.setBackgroundColor(Color.GRAY);
            button18g.setBackgroundResource(android.R.drawable.btn_default);
            individual18 = true;
        }
        if (v == button19g) {
            button19g.setBackgroundColor(Color.GRAY);
            button19i.setBackgroundResource(android.R.drawable.btn_default);
            individual19 = false;
        }
        if (v == button19i) {
            button19i.setBackgroundColor(Color.GRAY);
            button19g.setBackgroundResource(android.R.drawable.btn_default);
            individual19 = true;
        }
        if (v == button20g) {
            button20g.setBackgroundColor(Color.GRAY);
            button20i.setBackgroundResource(android.R.drawable.btn_default);
            individual20 = false;
        }
        if (v == button20i) {
            button20i.setBackgroundColor(Color.GRAY);
            button20g.setBackgroundResource(android.R.drawable.btn_default);
            individual20 = true;
        }
        if (v == button21g) {
            button21g.setBackgroundColor(Color.GRAY);
            button21i.setBackgroundResource(android.R.drawable.btn_default);
            individual21 = false;
        }
        if (v == button21i) {
            button21i.setBackgroundColor(Color.GRAY);
            button21g.setBackgroundResource(android.R.drawable.btn_default);
            individual21 = true;
        }
        if (v == button22g) {
            button22g.setBackgroundColor(Color.GRAY);
            button22i.setBackgroundResource(android.R.drawable.btn_default);
            individual22 = false;
        }
        if (v == button22i) {
            button22i.setBackgroundColor(Color.GRAY);
            button22g.setBackgroundResource(android.R.drawable.btn_default);
            individual22 = true;
        }
        if (v == button23g) {
            button23g.setBackgroundColor(Color.GRAY);
            button23i.setBackgroundResource(android.R.drawable.btn_default);
            individual23 = false;
        }
        if (v == button23i) {
            button23i.setBackgroundColor(Color.GRAY);
            button23g.setBackgroundResource(android.R.drawable.btn_default);
            individual23 = true;
        }

    }
}
