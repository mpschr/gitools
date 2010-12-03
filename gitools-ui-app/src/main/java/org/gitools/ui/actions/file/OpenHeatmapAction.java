package org.gitools.ui.actions.file;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

import org.gitools.ui.IconNames;
import org.gitools.ui.platform.actions.BaseAction;
import org.gitools.ui.platform.AppFrame;
import org.gitools.ui.settings.Settings;

import edu.upf.bg.progressmonitor.IProgressMonitor;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;
import org.gitools.heatmap.model.Heatmap;
import org.gitools.heatmap.util.HeatmapUtil;
import org.gitools.matrix.model.IMatrix;
import org.gitools.matrix.model.IMatrixView;
import org.gitools.matrix.model.MatrixView;
import org.gitools.persistence.FileFormat;
import org.gitools.persistence.FileFormats;
import org.gitools.persistence.FileSuffixes;
import org.gitools.persistence.MimeTypes;
import org.gitools.persistence.PersistenceManager;
import org.gitools.persistence.PersistenceUtils;
import org.gitools.ui.platform.progress.JobRunnable;
import org.gitools.ui.platform.progress.JobThread;
import org.gitools.ui.heatmap.editor.HeatmapEditor;
import org.gitools.ui.utils.FileChooserUtils;
import org.gitools.ui.utils.FileFormatFilter;

public class OpenHeatmapAction extends BaseAction {

	private static final long serialVersionUID = -6528634034161710370L;

	public OpenHeatmapAction() {
		super("Heatmap ...");
		setDesc("Open a heatmap from a file");
		setSmallIconFromResource(IconNames.openMatrix16);
		setLargeIconFromResource(IconNames.openMatrix24);
		setMnemonic(KeyEvent.VK_M);
		setDefaultEnabled(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		FileFilter[] filters = new FileFilter[] {
			// TODO new FileFormatFilter(FileFormats.HEATMAP),
			new FileFormatFilter("Known formats", null, new FileFormat[] {
				FileFormats.RESULTS_MATRIX,
				FileFormats.DOUBLE_MATRIX,
				FileFormats.DOUBLE_BINARY_MATRIX,
				FileFormats.GENE_MATRIX,
				FileFormats.GENE_MATRIX_TRANSPOSED
			}),
			new FileFormatFilter(FileFormats.RESULTS_MATRIX),
			new FileFormatFilter(FileFormats.RESULTS_MATRIX.getTitle() + " (*.*)", MimeTypes.OBJECT_MATRIX),
			new FileFormatFilter(FileFormats.DOUBLE_MATRIX),
			new FileFormatFilter(FileFormats.DOUBLE_MATRIX.getTitle() + " (*.*)", MimeTypes.DOUBLE_MATRIX),
			new FileFormatFilter(FileFormats.DOUBLE_BINARY_MATRIX),
			new FileFormatFilter(FileFormats.GENE_MATRIX),
			new FileFormatFilter(FileFormats.GENE_MATRIX_TRANSPOSED)
		};

		final FileChooserUtils.FileAndFilter ret = FileChooserUtils.selectFile(
				"Select file", FileChooserUtils.MODE_OPEN, filters);

		if (ret == null)
			return;
		
		final File file = ret.getFile();
		
		if (file == null)
			return;

		final FileFormatFilter ff = (FileFormatFilter) ret.getFilter();

		Settings.getDefault().setLastPath(file.getParent());
		Settings.getDefault().save();

		JobThread.execute(AppFrame.instance(), new JobRunnable() {
			@Override
			public void run(IProgressMonitor monitor) {
				try {
					monitor.begin("Loading ...", 1);
					monitor.info("File: " + file.getName());

					String mime = ff.getMime();
					if (mime == null)
						mime = PersistenceManager.getDefault().getMimeFromFile(file.getName());

					final IMatrix matrix = (IMatrix) PersistenceManager.getDefault()
							.load(file, mime, monitor);

					final IMatrixView matrixView = new MatrixView(matrix);

					Heatmap figure = HeatmapUtil.createFromMatrixView(matrixView);

					final HeatmapEditor editor = new HeatmapEditor(figure);

					editor.setName(PersistenceUtils.getFileName(file.getName())
							+ "." + FileSuffixes.HEATMAP);

					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							AppFrame.instance().getEditorsPanel().addEditor(editor);
							AppFrame.instance().refresh();
						}
					});

					monitor.end();
				}
				catch (Exception ex) {
					monitor.exception(ex);
				}
			}
		});

		AppFrame.instance().setStatusText("Done.");
	}
}
