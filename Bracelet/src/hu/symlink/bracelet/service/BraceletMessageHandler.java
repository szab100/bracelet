package hu.symlink.bracelet.service;

import hu.symlink.bracelet.Measure;


public class BraceletMessageHandler {
	
	private enum State {
		NONE,
		IN_MEASURE,
		IN_MEASURE_DATA
	}
	
	private enum DataType {
		NONE,
		PULSE,
		BLOX,
		TEMP,
		ACCEL,
		POWER,
		EMERGENCY
	}
	
	// Store current states
	private State currentState = State.NONE;
	private DataType currentDataType = DataType.NONE;
	
	private StringBuilder currentData = null ;
	private Measure measure = null;
	
	private BraceletService service = null;
	
	public BraceletMessageHandler()
	{
	}
	
	public void processMessage(String msg, BraceletService p_service)
	{
		
		// Initialize service ref
		service = p_service;
		
		// Lets process message char-by-char
		for(int i=0;i<msg.length();i++)
		{
			processChar(msg.charAt(i));
		}
				
	}
	
	private void processChar(char ch)
	{
		// Look for special characters
		switch(ch)
		{
			case '[':
				if(currentState == State.NONE) currentState = State.IN_MEASURE;
				else currentState = State.NONE;
				
				// (Re-)Initialize measure object
				measure = null;
				break;
			
			case '<':
				if(currentState == State.IN_MEASURE) currentState = State.IN_MEASURE_DATA;
				else currentState = State.NONE;
				
				// Reset data before new one
				currentData = new StringBuilder();
				break;
				
			case '>':
				if(currentState == State.IN_MEASURE_DATA) currentState = State.IN_MEASURE;
				else currentState = State.NONE;
				break;
				
			case ']':
				// Send collected measure to service
				if(currentState == State.IN_MEASURE && measure!=null) setMeasure(measure); 
				
				// Reset state
				currentState = State.NONE;
				break;
				
			default:
				if (currentState == State.IN_MEASURE_DATA) processInnerChar(ch);
				break;
		}
	}
	
	private void processInnerChar(char ch) {
		
		// Switch by type
		switch (ch)
		{
			case 'P':
				currentDataType = DataType.PULSE;
				break;
			
			case 'B':
				currentDataType = DataType.BLOX;
				break;
				
			case 'T':
				currentDataType = DataType.TEMP;
				break;
				
			case 'A':
				currentDataType = DataType.ACCEL;
				break;
				
			case 'L':
				currentDataType = DataType.POWER;
				break;
				
			case 'E':
				currentDataType = DataType.EMERGENCY;
				break;
			
			default:
				processDataChar(ch);
		}
		
	}
	
	private void processDataChar(char ch) {
		
		if(measure == null) measure = new Measure(); 
		
		// Switch by DataType
		switch(currentDataType) {
			
			case ACCEL:
				if(currentData.length()<3) currentData.append(ch);
				
				// Set acceeleration
				if (currentData.length() == 3) {
					// Try to parse data as Int
					float val = (float) ( (float) Integer.parseInt(currentData.toString()) / 10.0);
					measure.setAcceleration(val);
				}
				break;
				
			case BLOX:
				if(currentData.length()<3) currentData.append(ch);
				
				// Set acceeleration
				if (currentData.length() == 3) {
					// Try to parse data as Int
					int val = Integer.parseInt(currentData.toString());
					measure.setBloodoxygen(val);
				}
				break;

			case PULSE:
				if(currentData.length()<3) currentData.append(ch);
				
				// Set acceeleration
				if (currentData.length() == 3) {
					// Try to parse data as Int
					int val = Integer.parseInt(currentData.toString());
					measure.setPulse(val);
				}
				break;

			case TEMP:
				if(currentData.length()<3) currentData.append(ch);
				
				// Set acceeleration
				if (currentData.length() == 3) {
					// Try to parse data as Int
					float val = (float) ( (float) Integer.parseInt(currentData.toString()) / 10.0);
					measure.setTemperature(val);
				}
				break;

			case POWER:
				if(currentData.length()<3) currentData.append(ch);
				
				// Set acceeleration
				if (currentData.length() == 3) {
					// Try to parse data as Int
					int val = Integer.parseInt(currentData.toString());
					measure.setPower(val);
				}
				break;

			case EMERGENCY:
				if(currentData.length()<1) currentData.append(ch);
				
				// Set acceeleration
				if (currentData.length() == 1) {
					// Try to parse data as Int
					int val = Integer.parseInt(currentData.toString());
					if(val == 1) measure.setPanic(true);
					else measure.setPanic(false);
				}
				break;
				
			default:
		}
		
	}
	
	private void setMeasure(Measure p_measure) {
		
        // Set measure on Service
        service.setMeasure(measure);
	}
	
}
