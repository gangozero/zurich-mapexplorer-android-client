package io.gangozero.mapexplorer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.gangozero.mapexplorer.R;
import io.gangozero.mapexplorer.adapters.LeaderBoardAdapter;
import io.gangozero.mapexplorer.di.DIHelper;
import io.gangozero.mapexplorer.models.UserModel;
import io.gangozero.mapexplorer.presenters.LeaderBoardPresenter;
import io.gangozero.mapexplorer.views.LeaderBoardView;

import java.util.List;

/**
 * Created by eleven on 17/09/2016.
 */
public class LeaderBoardFragment extends Fragment implements LeaderBoardView {

	@BindView(R.id.recycler_view) RecyclerView recyclerView;
	@BindView(R.id.text_status) TextView textView;
	private LeaderBoardPresenter presenter;

	public static LeaderBoardFragment create() {
		return new LeaderBoardFragment();
	}

	@Override public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		presenter = new LeaderBoardPresenter();
		DIHelper.coreComponent().inject(presenter);
	}

	@Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View result = inflater.inflate(R.layout.fragment_leaderboard, container, false);
		ButterKnife.bind(this, result);
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		return result;
	}

	@Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		presenter.onViewCreated(this);
	}

	@Override public void showUsers(List<UserModel> users) {
		textView.setVisibility(View.GONE);
		recyclerView.setAdapter(new LeaderBoardAdapter(users));
	}

	@Override public void showError(Throwable throwable) {
		textView.setText(throwable.getMessage());
	}
}
