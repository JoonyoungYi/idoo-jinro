package net.idoo.jinro.android;

import android.graphics.Bitmap;

public class JinroDatum {

	private int id;
	private String name;
	private String univ;
	private String major;
	private int birth_year;
	private String email;

	private String profile_img_url;
	private Bitmap profile_img;

	private String answer_where;
	private String answer_how;
	private String answer_else;
	private String answer_what;

	private String answer_open;
	private String answer_close;

	/**
	 * SetData
	 */

	public JinroDatum(int id, String name, String univ, String major,
			int birth_year, String profile_img_url, String answer_where,
			String answer_how, String answer_else, String answer_what) {
		this.id = id;
		this.name = name;
		this.univ = univ;
		this.major = major;
		this.birth_year = birth_year;
		this.email = null;

		this.profile_img_url = profile_img_url;
		this.profile_img = null;

		this.answer_where = answer_where;
		this.answer_how = answer_how;
		this.answer_else = answer_else;
		this.answer_what = answer_what;

		this.answer_open = null;
		this.answer_close = null;
		// mb_sp_datum = this;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setAnswerOpen(String answer_open) {
		this.answer_open = answer_open;
	}

	public void setAnswerClose(String answer_close) {
		this.answer_close = answer_close;
	}

	public void setImg(Bitmap profile_img) {
		this.profile_img = profile_img;
	}

	/**
	 * Get Data
	 */

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getUniv() {
		return this.univ;
	}

	public String getMajor() {
		return this.major;
	}

	public int getBirthYear() {
		return this.birth_year;
	}
	
	public String getEmail(){
		return this.email;
	}

	public String getProfileImgUrl() {
		return this.profile_img_url;
	}

	public Bitmap getProfileImg() {
		return this.profile_img;
	}

	public String getAnswerWhere() {
		return this.answer_where;
	}

	public String getAnswerHow() {
		return this.answer_how;
	}

	public String getAnswerElse() {
		return this.answer_else;
	}

	public String getAnswerWhat() {
		return this.answer_what;
	}

	public String getAnswerOpen() {
		return this.answer_open;
	}

	public String getAnswerClose() {
		return this.answer_close;
	}

	// public ArrayList<Bitmap> getBitmap() {
	// return img_bitmaps;
	// }

}
