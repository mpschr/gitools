package es.imim.bg.ztools.ui.wizards.panels;

import es.imim.bg.ztools.ui.wizards.AbstractWizard;
import es.imim.bg.ztools.ui.wizards.AnalysisWizardPanelDescriptor;
import es.imim.bg.ztools.ui.wizards.WizardDataModel;


public class ZCalcAnalysisStatsHelpDescriptor extends AnalysisWizardPanelDescriptor {
    
    public static final String IDENTIFIER = "STATSHELP_PANEL";
    final ZCalcAnalysisStatsHelpPanel statsHelpPanel;
    WizardDataModel dataModel;
    
    public ZCalcAnalysisStatsHelpDescriptor(AbstractWizard aw, Object BackPanelDescriptor, Object NextPanelDescriptor) {    	
        super(IDENTIFIER, new ZCalcAnalysisStatsHelpPanel(), BackPanelDescriptor, NextPanelDescriptor);
        this.statsHelpPanel = (ZCalcAnalysisStatsHelpPanel) getPanelComponent();
        this.dataModel = aw.getWizardDataModel();
        setNextButtonAccordingToInputs();
    }
       
    private void setNextButtonAccordingToInputs() {
    	//TODO: for some reason, setWizard returns null
        //getWizard().setNextFinishButtonEnabled(false);       
   }

    
}
