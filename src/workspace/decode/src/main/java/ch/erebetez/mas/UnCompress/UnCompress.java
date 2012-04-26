package ch.erebetez.mas.UnCompress;

public interface UnCompress {
	
	public String getCharset();
	
	public void setCharset(String charset);
	
	public byte[] getCompressedData();	
	
	public String uncompress();



}
