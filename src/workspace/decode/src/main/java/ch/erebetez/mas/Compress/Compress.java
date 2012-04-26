package ch.erebetez.mas.Compress;

public interface Compress {
		
		public String getCharset();
		
		public void setCharset(String charset);
		
		public byte[] getCompressedData();	
		
		public String compress();



}