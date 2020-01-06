package vebtree;


abstract class Constants {
	private Constants() {
	}	
	public static final String BACKGROUND_COLOR_CONTAINER="e6eff2";
	public static final String BACKGROUND_COLOR_SUMMARY="ffe48c";
	public static final String BACKGROUND_COLOR_ANIMATED="6BFAAE";
	public static final String EDGE_STYLE = "defaultEdge;fillColor=white;strokeColor=black;strokeWidth=2";
	public static final String BLOCK_STYLE_WITH_TEXT="defaultVertex;fontSize=9;fillColor=white;strokeColor=black;strokeWidth=2;fontColor=black";
	public static final String BLOCK_STYLE="defaultVertex;fillColor=white;strokeColor=black;strokeWidth=2;fontColor=black";
	public static final String BLOCK_STYLE_SUMMARY="defaultVertex;fillColor=yellow;strokeColor=black;strokeWidth=2;fontColor=black";
	public static final String FRAME_TITLE="Van-Emde-Boas Bäume";
	public static final int MIN_VALUE=0;
	public static final int MAX_VALUE=15;
	public static final long SLEEP_MS=2000;

	static String getBackgroundColor(boolean isSummary) {
		if(isSummary) {
			return Constants.BACKGROUND_COLOR_SUMMARY;
		}
		return Constants.BACKGROUND_COLOR_CONTAINER;
	}
		
	static String coalesce(Integer i) {
		if(i == null) {
			return "null";
		}
		return i.toString();	
	}
	
	static String coalesce(String number) {
		if(number!= null) {
			return number;
		}
		return null;
	}
			
}



