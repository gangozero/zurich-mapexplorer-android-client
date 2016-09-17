package io.gangozero.mapexplorer.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.gangozero.mapexplorer.R;
import io.gangozero.mapexplorer.models.UserModel;

import java.util.List;

/**
 * Created by eleven on 18/09/2016.
 */
public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardAdapter.VH> {

	private List<UserModel> list;

	public LeaderBoardAdapter(List<UserModel> list) {
		this.list = list;
	}

	@Override public VH onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_leaderboard, parent, false);
		return new VH(view);
	}

	@Override public void onBindViewHolder(VH holder, int position) {
		UserModel userModel = list.get(position);
		holder.textName.setText(userModel.name);
		holder.textXp.setText("XP:" + String.valueOf(userModel.xp));
	}

	@Override public int getItemCount() {
		return list.size();
	}

	static class VH extends RecyclerView.ViewHolder {

		@BindView(R.id.text_name) TextView textName;
		@BindView(R.id.text_xp) TextView textXp;

		public VH(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}
}
