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

public final class FragmentLoginBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final MaterialCardView loginFragmentCard;

  @NonNull
  public final TextInputEditText loginFragmentEmail;

  @NonNull
  public final TextInputLayout loginFragmentEmailLayout;

  @NonNull
  public final MaterialTextView loginFragmentLabelTextPassword;

  @NonNull
  public final TextInputEditText loginFragmentPassword;

  @NonNull
  public final TextInputLayout loginFragmentPasswordLayout;

  @NonNull
  public final MaterialButton loginFragmentSignupButton;

  @NonNull
  public final MaterialTextView loginFragmentTextInfo;

  @NonNull
  public final MaterialTextView loginFragmentTextSignup;

  private FragmentLoginBinding(@NonNull ConstraintLayout rootView,
      @NonNull MaterialCardView loginFragmentCard, @NonNull TextInputEditText loginFragmentEmail,
      @NonNull TextInputLayout loginFragmentEmailLayout,
      @NonNull MaterialTextView loginFragmentLabelTextPassword,
      @NonNull TextInputEditText loginFragmentPassword,
      @NonNull TextInputLayout loginFragmentPasswordLayout,
      @NonNull MaterialButton loginFragmentSignupButton,
      @NonNull MaterialTextView loginFragmentTextInfo,
      @NonNull MaterialTextView loginFragmentTextSignup) {
    this.rootView = rootView;
    this.loginFragmentCard = loginFragmentCard;
    this.loginFragmentEmail = loginFragmentEmail;
    this.loginFragmentEmailLayout = loginFragmentEmailLayout;
    this.loginFragmentLabelTextPassword = loginFragmentLabelTextPassword;
    this.loginFragmentPassword = loginFragmentPassword;
    this.loginFragmentPasswordLayout = loginFragmentPasswordLayout;
    this.loginFragmentSignupButton = loginFragmentSignupButton;
    this.loginFragmentTextInfo = loginFragmentTextInfo;
    this.loginFragmentTextSignup = loginFragmentTextSignup;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentLoginBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentLoginBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_login, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentLoginBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.login_fragment_card;
      MaterialCardView loginFragmentCard = ViewBindings.findChildViewById(rootView, id);
      if (loginFragmentCard == null) {
        break missingId;
      }

      id = R.id.login_fragment_email;
      TextInputEditText loginFragmentEmail = ViewBindings.findChildViewById(rootView, id);
      if (loginFragmentEmail == null) {
        break missingId;
      }

      id = R.id.login_fragment_email_layout;
      TextInputLayout loginFragmentEmailLayout = ViewBindings.findChildViewById(rootView, id);
      if (loginFragmentEmailLayout == null) {
        break missingId;
      }

      id = R.id.login_fragment_label_text_password;
      MaterialTextView loginFragmentLabelTextPassword = ViewBindings.findChildViewById(rootView, id);
      if (loginFragmentLabelTextPassword == null) {
        break missingId;
      }

      id = R.id.login_fragment_password;
      TextInputEditText loginFragmentPassword = ViewBindings.findChildViewById(rootView, id);
      if (loginFragmentPassword == null) {
        break missingId;
      }

      id = R.id.login_fragment_password_layout;
      TextInputLayout loginFragmentPasswordLayout = ViewBindings.findChildViewById(rootView, id);
      if (loginFragmentPasswordLayout == null) {
        break missingId;
      }

      id = R.id.login_fragment_signup_button;
      MaterialButton loginFragmentSignupButton = ViewBindings.findChildViewById(rootView, id);
      if (loginFragmentSignupButton == null) {
        break missingId;
      }

      id = R.id.login_fragment_text_info;
      MaterialTextView loginFragmentTextInfo = ViewBindings.findChildViewById(rootView, id);
      if (loginFragmentTextInfo == null) {
        break missingId;
      }

      id = R.id.login_fragment_text_signup;
      MaterialTextView loginFragmentTextSignup = ViewBindings.findChildViewById(rootView, id);
      if (loginFragmentTextSignup == null) {
        break missingId;
      }

      return new FragmentLoginBinding((ConstraintLayout) rootView, loginFragmentCard,
          loginFragmentEmail, loginFragmentEmailLayout, loginFragmentLabelTextPassword,
          loginFragmentPassword, loginFragmentPasswordLayout, loginFragmentSignupButton,
          loginFragmentTextInfo, loginFragmentTextSignup);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
