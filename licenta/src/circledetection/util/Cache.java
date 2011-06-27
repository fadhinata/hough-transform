package circledetection.util;

import java.util.ArrayList;
import java.util.List;

import javax.media.jai.PlanarImage;

import circledetection.PIOperations.Operation;
import circledetection.gui.AppToolBar;




public class Cache {
	private static Cache INSTANCE = null;
	private List<Operation> operations ;
	private int lastOperation;
	private Cache(){
		operations = new ArrayList<Operation>();
		lastOperation = -1;
	}
	public static Cache getInstance() {
		if(INSTANCE==null)
		{
			INSTANCE = new Cache();
		}
		return INSTANCE;
	}
	public void add(Operation o)
	{
		if(lastOperation!= operations.size()-1)
		{
			for(int i = lastOperation+1; i<operations.size(); i++)
			{
				operations.remove(i);
			}
		}
		operations.add(o);
		lastOperation=operations.size()-1;
		if(lastOperation==1)
		{
			AppToolBar.getInstance().enableUndo();
		}
	}
	public PlanarImage getNextOpInitialState()
	{
		if(lastOperation == operations.size()-1)
			return null;
		lastOperation++;
		return ((Operation)operations.get(lastOperation)).getInitialState();
	}
	public void undo() {
		((Operation)operations.get(lastOperation)).undo();
		lastOperation--;
		if(lastOperation==0)
			AppToolBar.getInstance().disableUndo();
		if(lastOperation==operations.size()-2)
			AppToolBar.getInstance().enableRedo();
		
	}
	public void redo() {
		((Operation)operations.get(lastOperation)).redo();
		lastOperation++;
		if(lastOperation == operations.size()-1)
			AppToolBar.getInstance().disableRedo();
	}

	public void clear()
	{
		operations.clear();
		lastOperation = -1;
		AppToolBar.getInstance().disableUndo();
		AppToolBar.getInstance().disableRedo();
	}
}
