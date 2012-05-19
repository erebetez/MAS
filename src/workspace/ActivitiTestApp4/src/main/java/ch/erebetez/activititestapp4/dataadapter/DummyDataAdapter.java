package ch.erebetez.activititestapp4.dataadapter;

import java.util.*;

import ch.erebetez.activititestapp4.dataobjects.InventoryItem;

public class DummyDataAdapter implements DataAdapter{
	
	private final List<String> lotIDs = Arrays.asList( "123456", "123455", "125476", "122234", "123457" );
	private final List<String> rooms = Arrays.asList( "B01R111", "B01R111", "B01R111", "B02R005", "B02R005" );
	
	@Override
	public List<Map<String, String>> getData() {
		List<Map<String, String>> dataList = new Vector<Map<String, String>>();

		for(int i = 0; i < lotIDs.size(); ++i){
			
			Map<String, String> data = new HashMap<String, String>();
			
			data.put(InventoryItem.LOT, lotIDs.get(i));
			data.put(InventoryItem.LOCATION, rooms.get(i));			
			
			dataList.add(data);
		}
		
		return dataList;
	}

	@Override
	public int size() {		
		return lotIDs.size();
	}

}
