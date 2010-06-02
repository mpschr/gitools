/*
 * DataSourcePanel.java
 *
 * Created on September 4, 2009, 1:58 PM
 */

package org.gitools.ui.analysis.wizard;

import edu.upf.bg.cutoffcmp.CutoffCmp;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComponent;
import javax.swing.event.DocumentEvent;
import org.gitools.persistence.FileFormat;
import org.gitools.persistence.FileFormats;
import org.gitools.ui.IconNames;
import org.gitools.ui.platform.IconUtils;
import org.gitools.ui.platform.dialog.MessageStatus;
import org.gitools.ui.platform.wizard.AbstractWizardPage;
import org.gitools.ui.settings.Settings;
import org.gitools.ui.utils.DocumentChangeListener;
import org.gitools.ui.utils.FileChooserUtils;

public class DataPage extends AbstractWizardPage {

	private static final long serialVersionUID = 3840797252370672587L;

	private static final FileFormat[] formats = new FileFormat[] {
			FileFormats.GENE_MATRIX,
			FileFormats.GENE_MATRIX_TRANSPOSED,
			FileFormats.DOUBLE_MATRIX,
			FileFormats.DOUBLE_BINARY_MATRIX,
			FileFormats.MODULES_2C_MAP,
			FileFormats.MODULES_INDEXED_MAP
	};

	private boolean discardNonMappedRowsVisible;
	private boolean populationFileVisible;

	/** Creates new form DataSourcePanel */
    public DataPage() {
		setTitle("Select data source");

		setLogo(IconUtils.getImageIconResourceScaledByHeight(IconNames.LOGO_DATA, 96));
		
        initComponents();

		discardNonMappedRowsVisible = true;
		populationFileVisible = true;

		dataContentsCb.setModel(new DefaultComboBoxModel(formats));
		dataContentsCb.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				updateState(); }
		});

		DocumentChangeListener docCompleteListener = new DocumentChangeListener() {
			@Override protected void update(DocumentEvent e) {
				updateState(); }
		};

		ItemListener itemListener = new ItemListener() {
			@Override public void itemStateChanged(ItemEvent e) {
				updateState(); }
		};

		discardNonMappedRowsCheck.addItemListener(itemListener);

		dataFilePath.getDocument().addDocumentListener(docCompleteListener);
		populationFilePath.getDocument().addDocumentListener(docCompleteListener);

		// TODO
		rowFilterEnabledCheck.setVisible(false);
		rowFilterFilePath.setVisible(false);
		rowFilterFileBrowseBtn.setVisible(false);
		rowFilterIncludeRb.setVisible(false);
		rowFilterExcludeRb.setVisible(false);

		rowFilterEnabledCheck.addItemListener(itemListener);
		rowFilterFilePath.getDocument().addDocumentListener(docCompleteListener);
		rowFilterIncludeRb.addItemListener(itemListener);
		rowFilterExcludeRb.addItemListener(itemListener);

		cutoffEnabledCheck.addItemListener(itemListener);
		String[] cmpNames = new String[CutoffCmp.comparators.length];
		for (int i = 0; i < cmpNames.length; i++)
			cmpNames[i] = CutoffCmp.comparators[i].getLongName();
		cutoffCmpCb.setModel(new DefaultComboBoxModel(cmpNames));
		cutoffCmpCb.setSelectedItem(CutoffCmp.GE.getLongName());

		cutoffValue.getDocument().addDocumentListener(docCompleteListener);
		cutoffValue.setText("1.5");

		discardNonMappedRowsCheck.setVisible(discardNonMappedRowsVisible);
    }

	private void updateState() {
		FileFormat ff = (FileFormat) dataContentsCb.getSelectedItem();

		boolean binaryFilterEnabled = ff == FileFormats.DOUBLE_MATRIX;

		cutoffEnabledCheck.setEnabled(binaryFilterEnabled);
		cutoffCmpCb.setEnabled(binaryFilterEnabled && cutoffEnabledCheck.isSelected());
		cutoffValue.setEnabled(binaryFilterEnabled && cutoffEnabledCheck.isSelected());

		//TODO rowFilterEnabledCheck.setEnabled(filteringControlsEnabled);
		rowFilterFilePath.setEnabled(rowFilterEnabledCheck.isSelected());
		rowFilterFileBrowseBtn.setEnabled(rowFilterEnabledCheck.isSelected());

		setMessage(MessageStatus.INFO, "");

		String path = dataFilePath.getText().trim().toLowerCase();
		if (!path.isEmpty()) {
			if (!getFileFormat().checkExtension(path))
				setMessage(MessageStatus.WARN, "The file extension doesn't match the selected format");

			/*String ext = getFileFormat().getExtension().toLowerCase();
			if (!path.endsWith(ext) &&
					!path.endsWith(ext + ".gz"))
				setMessage(MessageStatus.WARN, "The extension of the data file doesn't match the selected format");*/
		}

		boolean c = !dataFilePath.getText().isEmpty();
		
		if (cutoffEnabledCheck.isSelected()) {
			boolean fail = false;
			try {
				Double.valueOf(cutoffValue.getText());}
			catch (NumberFormatException e) {
				fail = true;
				setStatus(MessageStatus.ERROR);
				setMessage("Cutoff value should be a real number.");
			}

			c &= !fail && !cutoffValue.getText().isEmpty();
		}

		setComplete(c);
	}

	@Override
	public JComponent createControls() {
		return this;
	}

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        dataContentsCb = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        dataFilePath = new javax.swing.JTextField();
        dataFileBrowseBtn = new javax.swing.JButton();
        rowFilterFilePath = new javax.swing.JTextField();
        rowFilterFileBrowseBtn = new javax.swing.JButton();
        cutoffEnabledCheck = new javax.swing.JCheckBox();
        cutoffCmpCb = new javax.swing.JComboBox();
        cutoffValue = new javax.swing.JTextField();
        rowFilterEnabledCheck = new javax.swing.JCheckBox();
        rowFilterIncludeRb = new javax.swing.JRadioButton();
        rowFilterExcludeRb = new javax.swing.JRadioButton();
        populationFilePath = new javax.swing.JTextField();
        populationFileLabel = new javax.swing.JLabel();
        populationFileBrowserBtn = new javax.swing.JButton();
        discardNonMappedRowsCheck = new javax.swing.JCheckBox();

        jLabel1.setText("Data contents");

        dataContentsCb.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Binary data matrix", "Continuous data matrix" }));

        jLabel2.setText("Data file");

        dataFileBrowseBtn.setText("Browse...");
        dataFileBrowseBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dataFileBrowseBtnActionPerformed(evt);
            }
        });

        rowFilterFilePath.setEnabled(false);

        rowFilterFileBrowseBtn.setText("Browse...");
        rowFilterFileBrowseBtn.setEnabled(false);
        rowFilterFileBrowseBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rowFilterFileBrowseBtnActionPerformed(evt);
            }
        });

        cutoffEnabledCheck.setText("Transform to 1 (0 otherwise) cells with value");
        cutoffEnabledCheck.setEnabled(false);

        cutoffCmpCb.setEnabled(false);

        cutoffValue.setColumns(6);
        cutoffValue.setEnabled(false);

        rowFilterEnabledCheck.setText("Filter rows by label from the file");
        rowFilterEnabledCheck.setEnabled(false);

        buttonGroup2.add(rowFilterIncludeRb);
        rowFilterIncludeRb.setSelected(true);
        rowFilterIncludeRb.setText("Include only rows which labels are in the file");
        rowFilterIncludeRb.setEnabled(false);

        buttonGroup2.add(rowFilterExcludeRb);
        rowFilterExcludeRb.setText("Exclude rows which labels are in the file");
        rowFilterExcludeRb.setEnabled(false);

        populationFileLabel.setText("Population file");

        populationFileBrowserBtn.setText("Browse...");
        populationFileBrowserBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                populationFileBrowserBtnActionPerformed(evt);
            }
        });

        discardNonMappedRowsCheck.setText("Filter out rows for which no information appears in the module");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dataContentsCb, 0, 525, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(populationFileLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(populationFilePath, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
                            .addComponent(dataFilePath, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dataFileBrowseBtn, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(populationFileBrowserBtn, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(rowFilterEnabledCheck)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rowFilterFilePath, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rowFilterFileBrowseBtn))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rowFilterExcludeRb)
                            .addComponent(rowFilterIncludeRb)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cutoffEnabledCheck)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cutoffCmpCb, 0, 224, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cutoffValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(discardNonMappedRowsCheck))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dataContentsCb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dataFileBrowseBtn)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(dataFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(populationFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(populationFileLabel))
                    .addComponent(populationFileBrowserBtn))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rowFilterEnabledCheck)
                    .addComponent(rowFilterFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rowFilterFileBrowseBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rowFilterIncludeRb)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rowFilterExcludeRb)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cutoffEnabledCheck)
                    .addComponent(cutoffCmpCb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cutoffValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(discardNonMappedRowsCheck)
                .addContainerGap(238, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

	private void dataFileBrowseBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dataFileBrowseBtnActionPerformed
		File selPath = FileChooserUtils.selectFile(
				"Select file",
				Settings.getDefault().getLastDataPath(),
				FileChooserUtils.MODE_OPEN);

		if (selPath != null) {
			String fileName = selPath.getName().toLowerCase();
			for (FileFormat ff : formats) {
				if (ff.checkExtension(fileName)) {
					dataContentsCb.setSelectedItem(ff);
					break;
				}
			}
			
			dataFilePath.setText(selPath.getAbsolutePath());
			Settings.getDefault().setLastDataPath(selPath.getAbsolutePath());
		}
	}//GEN-LAST:event_dataFileBrowseBtnActionPerformed

	private void rowFilterFileBrowseBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rowFilterFileBrowseBtnActionPerformed
		File selPath = FileChooserUtils.selectFile(
				"Select file",
				Settings.getDefault().getLastPath(),
				FileChooserUtils.MODE_OPEN);

		if (selPath != null) {
			rowFilterFilePath.setText(selPath.getAbsolutePath());
			Settings.getDefault().setLastPath(selPath.getAbsolutePath());
		}
	}//GEN-LAST:event_rowFilterFileBrowseBtnActionPerformed

	private void populationFileBrowserBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_populationFileBrowserBtnActionPerformed
		File selPath = FileChooserUtils.selectFile(
				"Select file",
				Settings.getDefault().getLastDataPath(),
				FileChooserUtils.MODE_OPEN);

		if (selPath != null) {
			populationFilePath.setText(selPath.getAbsolutePath());
			Settings.getDefault().setLastDataPath(selPath.getAbsolutePath());
		}
	}//GEN-LAST:event_populationFileBrowserBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox cutoffCmpCb;
    private javax.swing.JCheckBox cutoffEnabledCheck;
    private javax.swing.JTextField cutoffValue;
    private javax.swing.JComboBox dataContentsCb;
    private javax.swing.JButton dataFileBrowseBtn;
    private javax.swing.JTextField dataFilePath;
    private javax.swing.JCheckBox discardNonMappedRowsCheck;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton populationFileBrowserBtn;
    private javax.swing.JLabel populationFileLabel;
    private javax.swing.JTextField populationFilePath;
    private javax.swing.JCheckBox rowFilterEnabledCheck;
    private javax.swing.JRadioButton rowFilterExcludeRb;
    private javax.swing.JButton rowFilterFileBrowseBtn;
    private javax.swing.JTextField rowFilterFilePath;
    private javax.swing.JRadioButton rowFilterIncludeRb;
    // End of variables declaration//GEN-END:variables

	public FileFormat getFileFormat() {
		return (FileFormat) dataContentsCb.getSelectedItem();
	}

	public File getDataFile() {
		String path = dataFilePath.getText();
		return path.isEmpty() ? null : new File(path);
	}

	public File getPopulationFile() {
		String text = populationFilePath.getText();
		return !text.isEmpty() ? new File(text) : null;
	}

	public boolean isBinaryCutoffEnabled() {
		return cutoffEnabledCheck.isSelected();
	}

	public CutoffCmp getBinaryCutoffCmp() {
		return CutoffCmp.getFromName((String) cutoffCmpCb.getSelectedItem());
	}

	public double getBinaryCutoffValue() {
		return Double.parseDouble(cutoffValue.getText());
	}

	public boolean isDiscardNonMappedRows() {
		return discardNonMappedRowsCheck.isSelected();
	}

	/*public boolean isDiscardNonMappedRowsVisible() {
		return discardNonMappedRowsVisible;
	}*/

	public void setDiscardNonMappedRowsVisible(boolean visible) {
		discardNonMappedRowsVisible = visible;
		discardNonMappedRowsCheck.setVisible(discardNonMappedRowsVisible);
	}

	public void setPopulationFileVisible(boolean visible) {
		populationFileLabel.setVisible(visible);
		populationFilePath.setVisible(visible);
		populationFileBrowserBtn.setVisible(visible);
	}
}
