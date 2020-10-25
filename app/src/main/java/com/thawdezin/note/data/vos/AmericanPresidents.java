package com.thawdezin.note.data.vos;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class AmericanPresidents implements Serializable {

	@SerializedName("number")
	private int number;

	@SerializedName("photoUrl")
	private String photoUrl ;
	@SerializedName("death_year")
	private int deathYear;

	@SerializedName("left_office")
	private String leftOffice;

	@SerializedName("took_office")
	private String tookOffice;

	@SerializedName("party")
	private String party;

	@SerializedName("president")
	private String president;

	@SerializedName("birth_year")
	private int birthYear;

	public void setNumber(int number){
		this.number = number;
	}

	public int getNumber(){
		return number;
	}

	public void setPhotoUrl(String photoUrl){
		this.photoUrl = photoUrl;
	}

	public String getPhotoUrl(){
		return photoUrl;
	}

	public void setDeathYear(int deathYear){
		this.deathYear = deathYear;
	}

	public int getDeathYear(){
		return deathYear;
	}

	public void setLeftOffice(String leftOffice){
		this.leftOffice = leftOffice;
	}

	public String getLeftOffice(){
		return leftOffice;
	}

	public void setTookOffice(String tookOffice){
		this.tookOffice = tookOffice;
	}

	public String getTookOffice(){
		return tookOffice;
	}

	public void setParty(String party){
		this.party = party;
	}

	public String getParty(){
		return party;
	}

	public void setPresident(String president){
		this.president = president;
	}

	public String getPresident(){
		return president;
	}

	public void setBirthYear(int birthYear){
		this.birthYear = birthYear;
	}

	public int getBirthYear(){
		return birthYear;
	}

	@Override
 	public String toString(){
		return 
			"AmericanPresidents{" + 
			"number = '" + number + '\'' + 
			",photoUrl = '" + photoUrl + '\'' + 
			",death_year = '" + deathYear + '\'' + 
			",left_office = '" + leftOffice + '\'' + 
			",took_office = '" + tookOffice + '\'' + 
			",party = '" + party + '\'' + 
			",president = '" + president + '\'' + 
			",birth_year = '" + birthYear + '\'' + 
			"}";
		}
}