package net.idoo.jinro.android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockFragment;

public class SettingsFragment extends SherlockFragment {

	private Button mSendEmailBtn;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.settings_fragment, container, false);

		/**
		 * actionbar setting
		 * 
		 * getActivity().getActionBar().setDisplayOptions(
		 * ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP |
		 * ActionBar.DISPLAY_SHOW_TITLE);
		 * 
		 * getActivity().getActionBar().setTitle(R.string.menu_event);
		 */

		mSendEmailBtn = (Button) v.findViewById(R.id.send_email_btn);
		mSendEmailBtn.setOnClickListener(onClickListener);

		return v;
	}

	/**
	 * Btn OnClickListener
	 */

	OnClickListener onClickListener = new OnClickListener() {
		public void onClick(View v) {
			clickListenerHandler(v.getId());
		}
	};

	private void clickListenerHandler(int id) {

		switch (id) {
		case R.id.send_email_btn:

			MainActivity mMainActivity = (MainActivity) getActivity();
			mMainActivity
					.onSettingsFragmentSendEmailBtnClicked("idoo.jinro.developer@gmail.com");

			break;

		default:
			break;
		}
	};

}
