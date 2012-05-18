package ch.erebetez.activititestapp4.dataadapter;

import java.util.*;

public class DummyDataAdapter implements DataAdapter{
	
	private final List<String> lotIDs = Arrays.asList( "123456", "123455", "125476", "122234", "123457" );
	private final List<String> rooms = Arrays.asList( "B01R111", "B01R111", "B01R111", "B02R005", "B02R005" );
	
	@Override
	public List<Map<String, String>> getData() {
		List<Map<String, String>> dataList = new Vector<Map<String, String>>();

		for(int i = 0; i < lotIDs.size(); ++i){
			
			Map<String, String> data = new HashMap<String, String>();
			
			// FIXME Put the names as constants somewhere else.
			data.put("Lot", lotIDs.get(i));
			data.put("Location", rooms.get(i));			
			
			dataList.add(data);
		}
		
		return dataList;
	}

	@Override
	public int size() {		
		return lotIDs.size();
	}

}
