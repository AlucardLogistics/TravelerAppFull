// Generated code from Butter Knife. Do not modify!
package com.example.sadic.travelerapp.ui.payment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.sadic.travelerapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RecepitActivity_ViewBinding implements Unbinder {
  private RecepitActivity target;

  private View view2131230769;

  @UiThread
  public RecepitActivity_ViewBinding(RecepitActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public RecepitActivity_ViewBinding(final RecepitActivity target, View source) {
    this.target = target;

    View view;
    target.tvId = Utils.findRequiredViewAsType(source, R.id.tvId, "field 'tvId'", TextView.class);
    target.tvAmount = Utils.findRequiredViewAsType(source, R.id.tvAmount, "field 'tvAmount'", TextView.class);
    target.tvStatus = Utils.findRequiredViewAsType(source, R.id.tvStatus, "field 'tvStatus'", TextView.class);
    target.ivQRCode = Utils.findRequiredViewAsType(source, R.id.ivQRCode, "field 'ivQRCode'", ImageView.class);
    target.tvRecepit = Utils.findRequiredViewAsType(source, R.id.tvRecepit, "field 'tvRecepit'", TextView.class);
    target.tvName = Utils.findRequiredViewAsType(source, R.id.tvName, "field 'tvName'", TextView.class);
    view = Utils.findRequiredView(source, R.id.btGoSearch, "field 'btGoSearch' and method 'onViewClicked'");
    target.btGoSearch = Utils.castView(view, R.id.btGoSearch, "field 'btGoSearch'", Button.class);
    view2131230769 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
    target.tvDeparture = Utils.findRequiredViewAsType(source, R.id.tvDeparture, "field 'tvDeparture'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    RecepitActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvId = null;
    target.tvAmount = null;
    target.tvStatus = null;
    target.ivQRCode = null;
    target.tvRecepit = null;
    target.tvName = null;
    target.btGoSearch = null;
    target.tvDeparture = null;

    view2131230769.setOnClickListener(null);
    view2131230769 = null;
  }
}
