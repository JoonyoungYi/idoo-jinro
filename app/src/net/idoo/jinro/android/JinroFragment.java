package net.idoo.jinro.android;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

public class JinroFragment extends SherlockFragment {

	static final int MENU_SET_MODE = 0;

	private LinkedList<JinroDatum> mItems;
	// private PullToRefreshGridView mPullRefreshGridView;
	private GridView mGridView;
	private LinearLayout mLoading;

	/**
	 * UI Reference
	 */

	// private GridView mGv;
	private YGvAdapter mYGvAdapter;

	private ImageView mProfileIv;
	private TextView mNameTv;
	private TextView mUnivTv;
	private TextView mCollegeTv;
	private TextView mFooterDivider;
	private TextView mFooterNomore;

	// private ImageView mYGvIv;
	// private TextView mYGvTitleTv;
	// private TextView mYGvContentTv;
	// private TextView mYGvSummaryTv;
	// private FrameLayout mYGvContainer;
	// private ProgressBar mYGvIvPb;

	// private TextView mNicknameTv;
	// private TextView mTsTv;

	// private ImageView mIv;

	// private Button mLikeBtn;
	// private Button mDislikeBtn;
	// private Button mAddCommentBtn;

	/**
	 * Image Loading Que
	 **/

	private ArrayList<Integer> waiting_img_positions = new ArrayList<Integer>();
	private int updating_pos = 0;

	// private static Boolean waiting_img_isExits = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.jinro_fragment, container, false);

		/**
		 * GridView Default Setting
		 */

		mGridView = (GridView) v.findViewById(R.id.gv);
		mLoading = (LinearLayout) v.findViewById(R.id.loading);

		/**
		 * Set on pulldown to refresh // Set a listener to be invoked when the
		 * list should be refreshed.
		 */

		/**
		 * GridView Item Setting
		 */
		// mListItems = new LinkedList<String>();
		mItems = new LinkedList<JinroDatum>();
		// mAdapter = new ArrayAdapter<String>(this,
		// android.R.layout.simple_list_item_1, mListItems);

		/**
		 * GridView Setting
		 */

		mYGvAdapter = new YGvAdapter(getActivity(), R.layout.jinro_fragment_gv,
				mItems);

		mGridView.setAdapter(mYGvAdapter);

		/**
		 * Grid View Click Listener
		 */
		mGridView.setOnItemClickListener(onItemClickListener);
		// mPullRefreshGridView
		new JsonLoadingTask().execute("");

		return v;
	}

	/**
	 * GridView Apdater Setting
	 */

	private class YGvAdapter extends ArrayAdapter<JinroDatum> {

		private Context context;
		LinkedList<JinroDatum> JinroData;
		int textViewResourceId;

		public YGvAdapter(Activity context, int textViewResourceId,
				LinkedList<JinroDatum> JinroData) {
			super(context, textViewResourceId, JinroData);

			this.context = context;
			this.JinroData = JinroData;
			this.textViewResourceId = textViewResourceId;
		}

		@Override
		public int getCount() {
			return JinroData.size();
		}

		@Override
		public JinroDatum getItem(int position) {
			return JinroData.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			/**
			 * UI Initiailizing
			 */

			// if (convertView == null) {
			convertView = getActivity().getLayoutInflater().inflate(
					textViewResourceId, null);
			// mYGvIv = (ImageView) convertView.findViewById(R.id.y_gv_iv);

			mProfileIv = (ImageView) convertView.findViewById(R.id.profile_iv);
			mNameTv = (TextView) convertView.findViewById(R.id.name_tv);
			mUnivTv = (TextView) convertView.findViewById(R.id.univ_tv);
			mCollegeTv = (TextView) convertView.findViewById(R.id.college_tv);

			mFooterDivider = (TextView) convertView
					.findViewById(R.id.footer_divider);
			mFooterNomore = (TextView) convertView
					.findViewById(R.id.footer_nomore);

			if (position == JinroData.size() - 1) {
				mFooterDivider.setVisibility(View.VISIBLE);
				mFooterNomore.setVisibility(View.VISIBLE);

			} else {

				mFooterDivider.setVisibility(View.GONE);
				mFooterNomore.setVisibility(View.GONE);

			}

			// mTsTv = (TextView) convertView.findViewById(R.id.ts_tv);

			// mYGvSummaryTv = (TextView) convertView
			// .findViewById(R.id.y_gv_summary_tv);
			// mYGvContainer = (FrameLayout) convertView
			// .findViewById(R.id.y_gv_container);
			// mYGvIvPb = (ProgressBar)
			// convertView.findViewById(R.id.y_gv_iv_pb);

			// }

			// mIv = (ImageView) convertView.findViewById(R.id.iv);

			// mAddCommentBtn = (Button) convertView
			// .findViewById(R.id.add_comment_btn);
			// mAddCommentBtn.setOnClickListener(onClickListener);
			// mLikeBtn = (Button) convertView.findViewById(R.id.like_btn);
			// mDislikeBtn = (Button)
			// convertView.findViewById(R.id.dislike_btn);

			/**
			 * Data Setting
			 */

			// int dislike_num = MbSpData.get(position).getDislikeNum();
			// int like_num = MbSpData.get(position).getLikeNum();

			String name = JinroData.get(position).getName();
			String univ = JinroData.get(position).getUniv();
			String major = JinroData.get(position).getMajor();
			// int id = MbSpData.get(position).getId();
			// int ts = MbSpData.get(position).getTs();
			// SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd. HH:mm",
			// Locale.KOREA);

			// ArrayList<String> img_urls = MbSpData.get(position).getImgUrls();

			if (JinroData.get(position).getProfileImg() != null) {
				mProfileIv.setImageBitmap(JinroData.get(position)
						.getProfileImg());
			}

			mNameTv.setText(name);
			mUnivTv.setText(univ);
			mCollegeTv.setText(major);
			// mTsTv.setText(sdf.format(new Date(ts)));

			// mLikeBtn.setText("+" + Integer.toString(like_num));
			// mDislikeBtn.setText("-" + Integer.toString(dislike_num));

			// if (from_whom.getName() != "") {

			// mYGvTitleTv.setText(title);

			// }

			// mYGvSummaryTv.setText(CpFpData.get(position).getSummary());
			// mYGvIv.setImageResource(CpFpData.get(position).getImg());

			// if (img_urls.size() != 0) {

			// for (int i = 0; i < img_urls.size(); i++) {
			// if (MbSpData.get(position).getImgState(i) == false) {
			// Log.e("img_url", img_urls.get(i));
			// new
			// ImageLoadingTask().execute(img_urls.get(i).toString());
			// waiting_img_urls.add(img_urls.get(i));

			// int[] waiting_img_location = { position, i };

			// waiting_img_locations.add(waiting_img_location);

			// if (waiting_img_isExits == false) {
			// new ImageLoadingTask().execute(waiting_img_urls
			// .get(0));
			// waiting_img_isExits = true;
			// }

			// }

			// }

			// mIv.setVisibility(View.VISIBLE);

			// if (MbSpData.get(position).getImgState(0)) {
			// mIv.setImageBitmap(MbSpData.get(position).getImgBitmap(0));
			// }

			// } else {

			// mIv.setVisibility(View.GONE);

			// }

			return convertView;
		}

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
		// case R.id.profile_btn:
		// // mPager.setCurrentItem(0);
		// Toast.makeText(getApplicationContext(), "아직 준비중인 기능입니다.",
		// Toast.LENGTH_SHORT).show();
		// break;
		// case R.id.list_btn:
		// mPager.setCurrentItem(0);
		// break;
		// case R.id.add_comment_btn:
		// Intent mStTpAct = new Intent(this, JinroDetailsActivity.class);
		// startActivity(mStTpAct);
		// break;
		default:
			break;
		}
	};

	/**
	 * Mb Fp Frag Data : JSON Loading Task
	 * 
	 * @author JoonYoungYi
	 * 
	 */

	private class JsonLoadingTask extends
			AsyncTask<String, JinroDatum, Boolean> {

		protected void onPreExecute() {
			// mYGv.setVisibility(View.VISIBLE);

			((ArrayAdapter<JinroDatum>) mYGvAdapter).clear();
		}

		protected Boolean doInBackground(String... strs) {

			// Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

			MainActivity mMainActivity = (MainActivity) getActivity();
			if (mMainActivity.isOnline()) {

				Log.d("JsonLoadingTask", "Internet connection true");

				ArrayList<JinroDatum> JinroData = getJsonText();

				Log.d("JsonLoadingTask", "getJsonText Success");

				for (JinroDatum JinroDatum : JinroData) {

					publishProgress(JinroDatum);

				}

				Log.d("JsonLoadingTask",
						"Input JinroDatum to Gridview adapter Success");

				return true;

			} else {

				Log.d("JsonLoadingTask", "Internet connection false");

				return false;

			}

		}

		protected void onProgressUpdate(JinroDatum... mJinroDatum) {

			((ArrayAdapter<JinroDatum>) mYGvAdapter).add(mJinroDatum[0]);

			// ImgDatum img = mMbSpDatum[0].getImg();

			// int position = ((ArrayAdapter<MbSpDatum>) mYGvAdapter).getCount()
			// - 1;

			// img.setPosition(position);

			// new ImageLoadingTask().execute(mJinroDatum[0].get);

		}

		protected void onPostExecute(Boolean result) {
			// mMbSpFragPb.setVisibility(View.GONE);
			// mYGv.setVisibility(View.VISIBLE);

			if (result) {
				mLoading.setVisibility(View.GONE);
				mGridView.setVisibility(View.VISIBLE);
				mYGvAdapter.notifyDataSetChanged();

				for (int i = 0; i < ((ArrayAdapter<JinroDatum>) mYGvAdapter)
						.getCount(); i++) {
					waiting_img_positions.add(i);
				}

			} else {
				Toast.makeText(getActivity(), "인터넷 연결을 확인해주세요",
						Toast.LENGTH_LONG).show();
			}

			new ImageLoadingTask().execute("");

		}
	}

	public ArrayList<JinroDatum> getJsonText() {

		ArrayList<JinroDatum> JinroData = new ArrayList<JinroDatum>();

		Log.d("JsonLoadingTask", "getJsonText make jinrodata arraylist Success");

		String line = null;

		try {
			line = getStringFromUrl("https://dl.dropboxusercontent.com/u/106104942/idoo/jinro/data.json");

			Log.d("JsonLoadingTask", "getJsonText getStringFromUrl Success");
			// Log.d("JsonLoadingTask", line);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		try {

			final JSONObject object = new JSONObject(line);
			// JSONObject object = new JSONSerializer.toJSON(line);

			Log.d("JsonLoadingTask",
					"getJsonText make string json-object Success");

			// JSONArray JinroDataArray = new
			// JSONArray(object.getString("data"));

			JSONArray JinroDataArray = object.getJSONArray("data");

			Log.d("JsonLoadingTask", "getJsonText make Json Array Success");

			for (int i = 0; i < JinroDataArray.length(); i++) {

				JSONObject JinroDatumObj = JinroDataArray.getJSONObject(i);

				int id = JinroDatumObj.getInt("id");

				String name = JinroDatumObj.getString("name");
				String univ = JinroDatumObj.getString("univ");
				String major = JinroDatumObj.getString("major");

				int birth_year = JinroDatumObj.getInt("birth_year");
				String profile_img_url = JinroDatumObj
						.getString("profile_img_url");

				String answer_where = JinroDatumObj.getString("answer_where");
				String answer_how = JinroDatumObj.getString("answer_how");
				String answer_else = JinroDatumObj.getString("answer_else");
				String answer_what = JinroDatumObj.getString("answer_what");

				JinroDatum mJinroDatum = new JinroDatum(id, name, univ, major,
						birth_year, profile_img_url, answer_where, answer_how,
						answer_else, answer_what);

				if (!JinroDatumObj.isNull("email")) {
					String email = JinroDatumObj.getString("email");
					mJinroDatum.setEmail(email);
				}

				if (!JinroDatumObj.isNull("answer_open")) {
					String answer_open = JinroDatumObj.getString("answer_open");
					mJinroDatum.setAnswerOpen(answer_open);
				}

				if (!JinroDatumObj.isNull("answer_close")) {
					String answer_close = JinroDatumObj
							.getString("answer_close");
					mJinroDatum.setAnswerClose(answer_close);
				}

				/*
				 * ArrayList<String> img_urls = new ArrayList<String>();
				 * 
				 * for (int j = 0; j < MbSpDatumObj.getJSONArray("img_urls")
				 * .length(); j++) { String img_url =
				 * (MbSpDatumObj.getJSONArray("img_urls") .getString(j));
				 * 
				 * img_urls.add(img_url);
				 * 
				 * }
				 * 
				 * ArrayList<String> comments_text = new ArrayList<String>();
				 * ArrayList<String> comments_nickname = new
				 * ArrayList<String>(); ArrayList<Integer> comments_ts = new
				 * ArrayList<Integer>();
				 * 
				 * for (int j = 0; j < MbSpDatumObj.getJSONArray("comments")
				 * .length(); j++) { String comment_text = (MbSpDatumObj
				 * .getJSONArray("comments").getJSONObject(j)
				 * .getString("text")); String comment_nickname =
				 * (MbSpDatumObj.getJSONArray(
				 * "comments").getJSONObject(j).getString("nickname")); Integer
				 * comment_ts = (new Integer(MbSpDatumObj
				 * .getJSONArray("comments").getJSONObject(j) .getInt("ts")));
				 * 
				 * comments_text.add(comment_text);
				 * comments_nickname.add(comment_nickname);
				 * comments_ts.add(comment_ts);
				 * 
				 * }
				 */

				JinroData.add(mJinroDatum);

				Log.d("JsonLoadingTask",
						"getJsonText add " + Integer.toString(i)
								+ "th Json Array Success");

			}

		} catch (JSONException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return JinroData;
	}

	public String getStringFromUrl(String url)
			throws UnsupportedEncodingException {

		BufferedReader br = new BufferedReader(new InputStreamReader(
				getInputStreamFromUrl(url), "utf-8"));

		StringBuffer sb = new StringBuffer();

		Log.d("JsonLoadingTask",
				"getStringFromUrl make buffer reader and string buffer success");

		try {
			String line = null;

			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			Log.d("JsonLoadingTask",
					"getStringFromUrl input data into stringbuffer success");

		} catch (IOException e) {
			e.printStackTrace();
		}

		return sb.toString().trim();
	}

	public static InputStream getInputStreamFromUrl(String url) {
		InputStream contentStream = null;

		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response = httpclient.execute(new HttpGet(url));
			contentStream = response.getEntity().getContent();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return contentStream;
	}

	/**
	 * Load Img Bitmap in Url
	 */

	private class ImageLoadingTask extends AsyncTask<String, Bitmap, Bitmap> {
		protected Bitmap doInBackground(String... str) {

			// Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
			URL url = null;

			try {

				String url_str = ((ArrayAdapter<JinroDatum>) mYGvAdapter)
						.getItem(waiting_img_positions.get(0))
						.getProfileImgUrl();

				url = new URL(url_str);

			} catch (MalformedURLException e) {
				e.printStackTrace();
			}

			Bitmap bitmap = getRemoteImage(url);

			return bitmap;
		}

		protected void onPostExecute(Bitmap bitmap) {

			// bitmap_tmp_list.add(bitmap);

			((ArrayAdapter<JinroDatum>) mYGvAdapter).getItem(
					waiting_img_positions.get(0)).setImg(bitmap);

			mYGvAdapter.notifyDataSetChanged();

			waiting_img_positions.remove(0);

			if (waiting_img_positions.size() != 0) {
				new ImageLoadingTask().execute("");
			}

			/**
			 * ((ArrayAdapter<JinroDatum>) mYGvAdapter).getItem(
			 * waiting_img_locations.get(0)[0]).setBitmap(
			 * waiting_img_locations.get(0)[1], bitmap);
			 * mYGvAdapter.notifyDataSetChanged();
			 * 
			 * waiting_img_urls.remove(0); waiting_img_locations.remove(0);
			 * 
			 * // waiting_img_urls
			 * 
			 * // ongoing_num += 1;
			 * 
			 * if (waiting_img_urls.isEmpty() == false) { new
			 * ImageLoadingTask().execute(waiting_img_urls.get(0)); } else {
			 * waiting_img_isExits = false; }
			 * 
			 * // imageView.setImageBitmap(result); // return bitmap;
			 **/
		}
	}

	private Bitmap getRemoteImage(final URL url) {
		Bitmap bitmap = null;

		try {
			URLConnection conn = url.openConnection();
			conn.connect();

			BufferedInputStream bis = new BufferedInputStream(
					conn.getInputStream());
			bitmap = BitmapFactory.decodeStream(bis);
			bis.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return bitmap;
	}

	/**
	 * GridView Click Event Setting
	 */

	private OnItemClickListener onItemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// if (getActivity() == null) {
			// return;
			// }

			JinroDatum mJinroDatum = ((ArrayAdapter<JinroDatum>) mYGvAdapter)
					.getItem(position);

			String name = mJinroDatum.getName();
			String univ = mJinroDatum.getUniv();
			String major = mJinroDatum.getMajor();
			String email = mJinroDatum.getEmail();

			String profile_img_url = mJinroDatum.getProfileImgUrl();

			String answer_where = mJinroDatum.getAnswerWhere();
			String answer_how = mJinroDatum.getAnswerHow();
			String answer_else = mJinroDatum.getAnswerElse();
			String answer_what = mJinroDatum.getAnswerWhat();

			String answer_open = mJinroDatum.getAnswerOpen();
			String answer_close = mJinroDatum.getAnswerClose();

			// YAct mWhyMain = (YAct) getActivity();
			// mWhyMain.

			MainActivity mMainActivity = (MainActivity) getActivity();
			mMainActivity.onJinroFragmentGvItemPressed(name, univ, major,
					email, profile_img_url, answer_where, answer_how,
					answer_else, answer_what, answer_open, answer_close);

		}
	};

	/**
	 * 
	 * Save and Restore
	 */

}
