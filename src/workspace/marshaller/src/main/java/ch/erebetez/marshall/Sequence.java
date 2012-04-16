package ch.erebetez.marshall;

import java.util.*;

public class Sequence {
	String name;
	int lenght;
	boolean flag;
	String method;

	List<String> rawdata;

	Map<String, String> dictionary;

	List<Assay> assayList;

	public List<Assay> getAssayList() {
		return assayList;
	}

	public void setAssayList(List<Assay> assayList) {
		this.assayList = assayList;
	}

	public Map<String, String> getDictionary() {
		return dictionary;
	}

	public void setDictionary(Map<String, String> dictionary) {
		this.dictionary = dictionary;
	}

	public List<String> getRawdata() {
		return rawdata;
	}

	public void setRawdata(List<String> rawdata) {
		this.rawdata = rawdata;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLenght() {
		return lenght;
	}

	public void setLenght(int lenght) {
		this.lenght = lenght;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	@Override
	public String toString() {
		return "Sequence [name=" + name + ", lenght=" + lenght + ", flag="
				+ flag + ", method=" + method + ", rawdata=" + rawdata
				+ ", dictionary=" + dictionary + ", assayList=" + assayList
				+ "]";
	}

}
