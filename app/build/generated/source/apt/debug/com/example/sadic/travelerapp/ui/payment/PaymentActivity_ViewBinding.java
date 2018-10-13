// Generated code from Butter Knife. Do not modify!
package com.example.sadic.travelerapp.ui.payment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.sadic.travelerapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PaymentActivity_ViewBinding implements Unbinder {
  private PaymentActivity target;

  @UiThread
  public PaymentActivity_ViewBinding(PaymentActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public PaymentActivity_ViewBinding(PaymentActivity target, View source) {
    this.target = target;

    target.textView2 = Utils.findRequiredViewAsType(source, R.id.textView2, "field 'textView2'", TextView.class);
    target.etEmail = Utils.findRequiredViewAsType(source, R.id.etEmail, "field 'etEmail'", EditText.class);
    target.etPaswword = Utils.findRequiredViewAsType(source, R.id.etPaswword, "field 'etPaswword'", EditText.class);
    target.etMobile = Utils.findRequiredViewAsType(source, R.id.etMobile, "field 'etMobile'", EditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PaymentActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.textView2 = null;
    target.etEmail = null;
    target.etPaswword = null;
    target.etMobile = null;
  }
}
