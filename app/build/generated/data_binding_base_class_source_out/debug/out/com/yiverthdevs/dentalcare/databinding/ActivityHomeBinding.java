// Generated by view binder compiler. Do not edit!
package com.yiverthdevs.dentalcare.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.yiverthdevs.dentalcare.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityHomeBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextView TextView;

  @NonNull
  public final ImageView imageVieW;

  private ActivityHomeBinding(@NonNull ConstraintLayout rootView, @NonNull TextView TextView,
      @NonNull ImageView imageVieW) {
    this.rootView = rootView;
    this.TextView = TextView;
    this.imageVieW = imageVieW;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityHomeBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityHomeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_home, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityHomeBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.TextView;
      TextView TextView = ViewBindings.findChildViewById(rootView, id);
      if (TextView == null) {
        break missingId;
      }

      id = R.id.imageVieW;
      ImageView imageVieW = ViewBindings.findChildViewById(rootView, id);
      if (imageVieW == null) {
        break missingId;
      }

      return new ActivityHomeBinding((ConstraintLayout) rootView, TextView, imageVieW);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
