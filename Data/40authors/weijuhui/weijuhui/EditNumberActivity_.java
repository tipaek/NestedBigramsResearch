//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package com.weparty.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.RelativeLayout;
import com.weparty.R.id;
import com.weparty.R.layout;

public final class EditNumberActivity_
    extends EditNumberActivity
{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(layout.act_edit_phone);
    }

    private void init_(Bundle savedInstanceState) {
        injectExtras_();
    }

    private void afterSetContentView_() {
        title_phone_title = ((RelativeLayout) findViewById(id.title_phone_title));
        number_dt = ((EditText) findViewById(id.number_dt));
        {
            View view = findViewById(id.title_back);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        EditNumberActivity_.this.back();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.title_pos);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        EditNumberActivity_.this.pos();
                    }

                }
                );
            }
        }
        afterViews();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        afterSetContentView_();
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
        super.setContentView(view, params);
        afterSetContentView_();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        afterSetContentView_();
    }

    public static EditNumberActivity_.IntentBuilder_ intent(Context context) {
        return new EditNumberActivity_.IntentBuilder_(context);
    }

    @SuppressWarnings("unchecked")
    private<T >T cast_(Object object) {
        return ((T) object);
    }

    private void injectExtras_() {
        Intent intent_ = getIntent();
        Bundle extras_ = intent_.getExtras();
        if (extras_!= null) {
            if (extras_.containsKey("value")) {
                try {
                    value = cast_(extras_.get("value"));
                } catch (ClassCastException e) {
                    Log.e("EditNumberActivity_", "Could not cast extra to expected type, the field is left to its default value", e);
                }
            }
            if (extras_.containsKey("flag")) {
                try {
                    flag = ((Integer) extras_.get("flag"));
                } catch (ClassCastException e) {
                    Log.e("EditNumberActivity_", "Could not cast extra to expected type, the field is left to its default value", e);
                }
            }
        }
    }

    @Override
    public void setIntent(Intent newIntent) {
        super.setIntent(newIntent);
        injectExtras_();
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, EditNumberActivity_.class);
        }

        public Intent get() {
            return intent_;
        }

        public EditNumberActivity_.IntentBuilder_ flags(int flags) {
            intent_.setFlags(flags);
            return this;
        }

        public void start() {
            context_.startActivity(intent_);
        }

        public void startForResult(int requestCode) {
            if (context_ instanceof Activity) {
                ((Activity) context_).startActivityForResult(intent_, requestCode);
            } else {
                context_.startActivity(intent_);
            }
        }

        public EditNumberActivity_.IntentBuilder_ value(String value) {
            intent_.putExtra("value", value);
            return this;
        }

        public EditNumberActivity_.IntentBuilder_ flag(int flag) {
            intent_.putExtra("flag", flag);
            return this;
        }

    }

}
