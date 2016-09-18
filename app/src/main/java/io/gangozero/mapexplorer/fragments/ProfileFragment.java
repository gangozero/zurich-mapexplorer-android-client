package io.gangozero.mapexplorer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.model.OAuthAsyncRequestCallback;
import com.github.scribejava.core.model.OAuthRequestAsync;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuthService;
import io.gangozero.mapexplorer.R;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by eleven on 18/09/2016.
 */
public class ProfileFragment extends Fragment {

	private OAuthService service;

	public static ProfileFragment create() {
		return new ProfileFragment();
	}

	@Override public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		service = new ServiceBuilder()
				.apiKey("227Z5M")
				.apiSecret("a3a4daee7f7059291c359d4405d42b02")
				.build(new FitBitAuth());

	}

	@Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View result = inflater.inflate(R.layout.fragment_profile, container, false);
		ButterKnife.bind(this, result);
		return result;
	}

	@OnClick(R.id.btn_fitbit_auth) public void authFitBit() {
		auth();
	}

	private void authOk() {

	}

	private void auth() {
		service.executeAsync(
				new HashMap<>(),
				Verb.GET,
				"",
				"",
				new OAuthAsyncRequestCallback<Object>() {
					@Override public void onCompleted(Object response) {
						authOk();
					}

					@Override public void onThrowable(Throwable t) {
						t.printStackTrace();
					}
				},
				new OAuthRequestAsync.ResponseConverter<Object>() {
					@Override public Object convert(Response response) throws IOException {
						return null;
					}
				}
		);
	}

	private static class FitBitAuth extends DefaultApi20 {

		@Override public String getAccessTokenEndpoint() {
			return "https://api.fitbit.com/oauth2/token";
		}

		@Override protected String getAuthorizationBaseUrl() {
			return "https://www.fitbit.com/oauth2/authorize";
		}


	}
}
