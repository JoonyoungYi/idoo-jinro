package net.idoo.jinro.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

public final class JinroDetailsPostingFragment extends SherlockFragment {

	static final int MENU_SET_MODE = 0;

	/**
	 * UI Reference
	 */

	/** Called when the activity is first created. */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.jinro_details_posting_fragment,
				container, false);

		Intent mIntent = getActivity().getIntent();

		String answer_where = mIntent.getExtras().getString("answer_where");
		String answer_how = mIntent.getExtras().getString("answer_how");
		String answer_else = mIntent.getExtras().getString("answer_else");
		String answer_what = mIntent.getExtras().getString("answer_what");

		String answer_open = mIntent.getExtras().getString("answer_open");
		String answer_close = mIntent.getExtras().getString("answer_close");

		/**
		 * Data Input to TextView
		 */

		TextView mAnswerWhereTv = (TextView) v
				.findViewById(R.id.answer_where_tv);
		TextView mAnswerHowTv = (TextView) v.findViewById(R.id.answer_how_tv);
		TextView mAnswerElseTv = (TextView) v.findViewById(R.id.answer_else_tv);
		TextView mAnswerWhatTv = (TextView) v.findViewById(R.id.answer_what_tv);

		mAnswerWhereTv.setText(answer_where);
		mAnswerHowTv.setText(answer_how);
		mAnswerElseTv.setText(answer_else);
		mAnswerWhatTv.setText(answer_what);

		if (answer_open != null) {
			TextView mAnswerOpenTv = (TextView) v
					.findViewById(R.id.answer_open_tv);
			TextView mAnswerOpenLabelTv = (TextView) v
					.findViewById(R.id.answer_open_label_tv);

			mAnswerOpenTv.setText(answer_open);
			mAnswerOpenTv.setVisibility(View.VISIBLE);
			mAnswerOpenLabelTv.setVisibility(View.VISIBLE);
		}

		if (answer_close != null) {
			TextView mAnswerCloseTv = (TextView) v
					.findViewById(R.id.answer_close_tv);
			TextView mAnswerCloseLabelTv = (TextView) v
					.findViewById(R.id.answer_close_label_tv);

			mAnswerCloseTv.setText(answer_close);
			mAnswerCloseTv.setVisibility(View.VISIBLE);
			mAnswerCloseLabelTv.setVisibility(View.VISIBLE);
		}

		TextView mAnswerCloseTv = (TextView) v
				.findViewById(R.id.answer_close_tv);

		return v;
	}

}
