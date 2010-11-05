package org.gitools.ui.analysis.htest.editor._DEPRECATED;

import org.gitools.ui.analysis.htest.editor.actions._DEPRECATED.NewDataHeatmapFromHtestAnalysisAction;
import org.gitools.ui.analysis.htest.editor.actions._DEPRECATED.NewResultsHeatmapFromHtestAnalysisAction;
import java.awt.BorderLayout;

import org.apache.velocity.VelocityContext;
import org.gitools.ui.platform.editor.AbstractEditor;
import org.gitools.ui.platform.panel.TemplatePanel;

import edu.upf.bg.formatter.GenericFormatter;
import java.util.HashMap;
import java.util.Map;
import org.gitools.analysis.htest.HtestAnalysis;
import org.gitools.analysis.htest.oncozet.OncodriveAnalysis;
import org.gitools.ui.platform.actions.ActionSet;
import org.gitools.ui.platform.actions.ActionSetUtils;
import org.gitools.ui.platform.actions.BaseAction;

@Deprecated // See EnrichmentAnalysisEditor and OncozEnrichmentEditor
public class HtestAnalysisEditor extends AbstractEditor {

	private static final long serialVersionUID = 8258025724628410016L;

	public static final ActionSet toolBar = new ActionSet(new BaseAction[] {
		new NewDataHeatmapFromHtestAnalysisAction(),
		new NewResultsHeatmapFromHtestAnalysisAction()
	});

	private static Map<Class<? extends HtestAnalysis>, String> templateMap = new HashMap<Class<? extends HtestAnalysis>, String>();
	static {
		templateMap.put(OncodriveAnalysis.class, "/vm/analysis/oncodrive/analysis_details.vm");
	}

	private HtestAnalysis analysis;

	private TemplatePanel templatePane;

	public HtestAnalysisEditor(HtestAnalysis analysis) {
		this.analysis = analysis;

		createComponents();
	}

	private void createComponents() {
		templatePane = new TemplatePanel();
		try {
			templatePane.setTemplateFromResource(
					templateMap.get(analysis.getClass()));

			VelocityContext context = new VelocityContext();
			context.put("fmt", new GenericFormatter());
			context.put("analysis", analysis);

			templatePane.setContext(context);
			templatePane.render();
		} catch (Exception e) {
			e.printStackTrace();
		}

		setLayout(new BorderLayout());
		add(ActionSetUtils.createToolBar(toolBar), BorderLayout.NORTH);
		add(templatePane, BorderLayout.CENTER);
	}

	@Override
	public Object getModel() {
		return analysis;
	}
}