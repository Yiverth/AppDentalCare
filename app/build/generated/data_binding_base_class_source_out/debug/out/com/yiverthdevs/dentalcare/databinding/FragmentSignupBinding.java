// Generated by view binder compiler. Do not edit!
package com.yiverthdevs.dentalcare.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public final class FragmentSignupBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final MaterialCardView signupFragmentCard;

  @NonNull
  public final TextInputEditText signupFragmentEmail;

  @NonNull
  public final TextInputLayout signupFragmentEmailLayout;

  @NonNull
  public final RadioButton signupFragmentFemale;

  @NonNull
  public final MaterialTextView signupFragmentLavelTextGender;

  @NonNull
  public final RadioButton signupFragmentMale;

  @NonNull
  public final TextInputEditText signupFragmentName;

  @NonNull
  public final TextInputLayout signupFragmentNameLayout;

  @NonNull
  public final TextInputEditText signupFragmentNumber;

  @NonNull
  public final TextInputLayout signupFragmentNumberLayout;

  @NonNull
  public final TextInputEditText signupFragmentPassword;

  @NonNull
  public final TextInputLayout signupFragmentPasswordLayout;

  @NonNull
  public final RadioGroup signupFragmentRadioGroup;

  @NonNull
  public final MaterialButton signupFragmentSignupButton;

  @NonNull
  public final MaterialTextView signupFragmentTextInfo;

  @NonNull
  public final MaterialTextView signupFragmentTextLogin;

  private FragmentSignupBinding(@NonNull ConstraintLayout rootView,
      @NonNull MaterialCardView signupFragmentCard, @NonNull TextInputEditText signupFragmentEmail,
      @NonNull TextInputLayout signupFragmentEmailLayout, @NonNull RadioButton signupFragmentFemale,
      @NonNull MaterialTextView signupFragmentLavelTextGender,
      @NonNull RadioButton signupFragmentMale, @NonNull TextInputEditText signupFragmentName,
      @NonNull TextInputLayout signupFragmentNameLayout,
      @NonNull TextInputEditText signupFragmentNumber,
      @NonNull TextInputLayout signupFragmentNumberLayout,
      @NonNull TextInputEditText signupFragmentPassword,
      @NonNull TextInputLayout signupFragmentPasswordLayout,
      @NonNull RadioGroup signupFragmentRadioGroup,
      @NonNull MaterialButton signupFragmentSignupButton,
      @NonNull MaterialTextView signupFragmentTextInfo,
      @NonNull MaterialTextView signupFragmentTextLogin) {
    this.rootView = rootView;
    this.signupFragmentCard = signupFragmentCard;
    this.signupFragmentEmail = signupFragmentEmail;
    this.signupFragmentEmailLayout = signupFragmentEmailLayout;
    this.signupFragmentFemale = signupFragmentFemale;
    this.signupFragmentLavelTextGender = signupFragmentLavelTextGender;
    this.signupFragmentMale = signupFragmentMale;
    this.signupFragmentName = signupFragmentName;
    this.signupFragmentNameLayout = signupFragmentNameLayout;
    this.signupFragmentNumber = signupFragmentNumber;
    this.signupFragmentNumberLayout = signupFragmentNumberLayout;
    this.signupFragmentPassword = signupFragmentPassword;
    this.signupFragmentPasswordLayout = signupFragmentPasswordLayout;
    this.signupFragmentRadioGroup = signupFragmentRadioGroup;
    this.signupFragmentSignupButton = signupFragmentSignupButton;
    this.signupFragmentTextInfo = signupFragmentTextInfo;
    this.signupFragmentTextLogin = signupFragmentTextLogin;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentSignupBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentSignupBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_signup, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentSignupBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.signup_fragment_card;
      MaterialCardView signupFragmentCard = ViewBindings.findChildViewById(rootView, id);
      if (signupFragmentCard == null) {
        break missingId;
      }

      id = R.id.signup_fragment_email;
      TextInputEditText signupFragmentEmail = ViewBindings.findChildViewById(rootView, id);
      if (signupFragmentEmail == null) {
        break missingId;
      }

      id = R.id.signup_fragment_email_layout;
      TextInputLayout signupFragmentEmailLayout = ViewBindings.findChildViewById(rootView, id);
      if (signupFragmentEmailLayout == null) {
        break missingId;
      }

      id = R.id.signup_fragment_female;
      RadioButton signupFragmentFemale = ViewBindings.findChildViewById(rootView, id);
      if (signupFragmentFemale == null) {
        break missingId;
      }

      id = R.id.signup_fragment_lavel_text_gender;
      MaterialTextView signupFragmentLavelTextGender = ViewBindings.findChildViewById(rootView, id);
      if (signupFragmentLavelTextGender == null) {
        break missingId;
      }

      id = R.id.signup_fragment_male;
      RadioButton signupFragmentMale = ViewBindings.findChildViewById(rootView, id);
      if (signupFragmentMale == null) {
        break missingId;
      }

      id = R.id.signup_fragment_name;
      TextInputEditText signupFragmentName = ViewBindings.findChildViewById(rootView, id);
      if (signupFragmentName == null) {
        break missingId;
      }

      id = R.id.signup_fragment_name_layout;
      TextInputLayout signupFragmentNameLayout = ViewBindings.findChildViewById(rootView, id);
      if (signupFragmentNameLayout == null) {
        break missingId;
      }

      id = R.id.signup_fragment_number;
      TextInputEditText signupFragmentNumber = ViewBindings.findChildViewById(rootView, id);
      if (signupFragmentNumber == null) {
        break missingId;
      }

      id = R.id.signup_fragment_number_layout;
      TextInputLayout signupFragmentNumberLayout = ViewBindings.findChildViewById(rootView, id);
      if (signupFragmentNumberLayout == null) {
        break missingId;
      }

      id = R.id.signup_fragment_password;
      TextInputEditText signupFragmentPassword = ViewBindings.findChildViewById(rootView, id);
      if (signupFragmentPassword == null) {
        break missingId;
      }

      id = R.id.signup_fragment_password_layout;
      TextInputLayout signupFragmentPasswordLayout = ViewBindings.findChildViewById(rootView, id);
      if (signupFragmentPasswordLayout == null) {
        break missingId;
      }

      id = R.id.signup_fragment_radio_group;
      RadioGroup signupFragmentRadioGroup = ViewBindings.findChildViewById(rootView, id);
      if (signupFragmentRadioGroup == null) {
        break missingId;
      }

      id = R.id.signup_fragment_signup_button;
      MaterialButton signupFragmentSignupButton = ViewBindings.findChildViewById(rootView, id);
      if (signupFragmentSignupButton == null) {
        break missingId;
      }

      id = R.id.signup_fragment_text_info;
      MaterialTextView signupFragmentTextInfo = ViewBindings.findChildViewById(rootView, id);
      if (signupFragmentTextInfo == null) {
        break missingId;
      }

      id = R.id.signup_fragment_text_login;
      MaterialTextView signupFragmentTextLogin = ViewBindings.findChildViewById(rootView, id);
      if (signupFragmentTextLogin == null) {
        break missingId;
      }

      return new FragmentSignupBinding((ConstraintLayout) rootView, signupFragmentCard,
          signupFragmentEmail, signupFragmentEmailLayout, signupFragmentFemale,
          signupFragmentLavelTextGender, signupFragmentMale, signupFragmentName,
          signupFragmentNameLayout, signupFragmentNumber, signupFragmentNumberLayout,
          signupFragmentPassword, signupFragmentPasswordLayout, signupFragmentRadioGroup,
          signupFragmentSignupButton, signupFragmentTextInfo, signupFragmentTextLogin);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
