package com.stanfy.enroscar.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stanfy.enroscar.content.loader.ResponseData;
import com.stanfy.enroscar.sample.model.Profile;
import com.stanfy.enroscar.rest.request.SimpleRequestBuilder;


public class LoadProfileFragment extends Fragment implements LoaderManager.LoaderCallbacks<ResponseData<Profile>> {

  private static final int LOADER_ID = 1;

  private TextView text1, text2;

  @Override
  public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
    final View mainView = inflater.inflate(R.layout.profile_fragment, container, false);
    return mainView;
  }

  @Override
  public void onViewCreated(final View view, final Bundle savedInstanceState) {
    text1 = (TextView)view.findViewById(R.id.profile_text1);
    text2 = (TextView)view.findViewById(R.id.profile_text2);
  }

  @Override
  public void onActivityCreated(final Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    getLoaderManager().initLoader(LOADER_ID, null, this);
  }

  @Override
  public Loader<ResponseData<Profile>> onCreateLoader(final int arg0, final Bundle arg1) {
    return new SimpleRequestBuilder<Profile>(getActivity()) { }
      .setUrl("https://api.twitter.com/1/users/show.json")
      .addParam("screen_name", "TwitterAPI")
      //.setModelClass(Profile.class)
      .getLoader();
  }

  @Override
  public void onLoadFinished(final Loader<ResponseData<Profile>> arg0, final ResponseData<Profile> arg1) {
    final Profile profile = arg1.getModel();
    if (profile != null) {
      text1.setText(profile.getName());
      text2.setText(profile.getDescription());
    } else {

    }
  }

  @Override
  public void onLoaderReset(final Loader<ResponseData<Profile>> arg0) {
    // TODO Auto-generated method stub

  }

}
