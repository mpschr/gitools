package es.imim.bg.ztools.ui.actions;

public class Actions {
	
	public static final MenuActionSet menuActionSet = 
		new MenuActionSet();
	
	public static final ToolBarActionSet toolBarActionSet = 
		new ToolBarActionSet();
	
	// Unclassified
	
	private Actions() {
	}

	/*public static void disableAll() {
		for (Field field : Actions.class.getDeclaredFields()) {
			if (BaseAction.class.equals(field.getType())) {
				try {
					BaseAction action = (BaseAction) field.get(null);
					if (action != null)
						action.setEnabled(false);
				} catch (Exception e) {
				}				
			}
		}
	}*/
}
