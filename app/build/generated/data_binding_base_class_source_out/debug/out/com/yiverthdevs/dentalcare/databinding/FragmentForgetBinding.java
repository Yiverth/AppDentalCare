// Generated by view binder compiler. Do not edit!
package com.yiverthdevs.dentalcare.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.yiverthdevs.dentalcare.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentForgetBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final MaterialCardView forgetFragmentCard;

  @NonNull
  public final TextInputEditText forgetFragmentEmail;

  @NonNull
  public final TextInputLayout forgetFragmentEmailLayout;

  @NonNull
  public final MaterialButton forgetFragmentSignupButton;

  @NonNull
  public final MaterialTextView forgetFragmentTextInfo;

  @NonNull
  public final MaterialTextView forgetFragmentTextSignup;

  private FragmentForgetBinding(@NonNull ConstraintLayout rootView,
      @NonNull MaterialCardView forgetFragmentCard, @NonNull TextInputEditText forgetFragmentEmail,
      @NonNull TextInputLayout forgetFragmentEmailLayout,
      @NonNull MaterialButton forgetFragmentSignupButton,
      @NonNull MaterialTextView forgetFragmentTextInfo,
      @NonNull MaterialTextView forgetFragmentTextSignup) {
    this.rootView = rootView;
    this.forgetFragmentCard = forgetFragmentCard;
    this.forgetFragmentEmail = forgetFragmentEmail;
    this.forgetFragmentEmailLayout = forgetFragmentEmailLayout;
    this.forgetFragmentSignupButton = forgetFragmentSignupButton;
    this.forgetFragmentTextInfo = forgetFragmentTextInfo;
    this.forgetFragmentTextSignup = forgetFragmentTextSignup;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentForgetBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentForgetBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_forget, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentForgetBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.forget_fragment_card;
      MaterialCardView forgetFragmentCard = ViewBindings.findChildViewById(rootView, id);
      if (forgetFragmentCard == null) {
        break missingId;
      }

      id = R.id.forget_fragment_email;
      TextInputEditText forgetFragmentEmail = ViewBindings.findChildViewById(rootView, id);
      if (forgetFragmentEmail == null) {
        break missingId;
      }

      id = R.id.forget_fragment_email_layout;
      TextInputLayout forgetFragmentEmailLayout = ViewBindings.findChildViewById(rootView, id);
      if (forgetFragmentEmailLayout == null) {
        break missingId;
      }

      id = R.id.forget_fragment_signup_button;
      MaterialButton forgetFragmentSignupButton = ViewBindings.findChildViewById(rootView, id);
      if (forgetFragmentSignupButton == null) {
        break missingId;
      }

      id = R.id.forget_fragment_text_info;
      MaterialTextView forgetFragmentTextInfo = ViewBindings.findChildViewById(rootView, id);
      if (forgetFragmentTextInfo == null) {
        break missingId;
      }

      id = R.id.forget_fragment_text_signup;
      MaterialTextView forgetFragmentTextSignup = ViewBindings.findChildViewById(rootView, id);
      if (forgetFragmentTextSignup == null) {
        break missingId;
      }

      return new FragmentForgetBinding((ConstraintLayout) rootView, forgetFragmentCard,
          forgetFragmentEmail, forgetFragmentEmailLayout, forgetFragmentSignupButton,
          forgetFragmentTextInfo, forgetFragmentTextSignup);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
