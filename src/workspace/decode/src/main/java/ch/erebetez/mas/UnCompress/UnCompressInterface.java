package ch.erebetez.mas.UnCompress;

public interface UnCompressInterface {
	
	public String getCharset();
	
	public void setCharset(String charset);
	
	public byte[] getCompressedData();	
	
	public String uncompress();



}
