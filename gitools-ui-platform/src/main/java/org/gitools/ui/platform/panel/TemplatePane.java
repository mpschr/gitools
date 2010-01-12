package org.gitools.ui.platform.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Point;
import java.awt.Window;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.util.Properties;
import java.util.Map.Entry;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.gitools.ui.platform.dialog.ExceptionDialog;

public class TemplatePane extends JPanel {

	private static final long serialVersionUID = 1939265225161205798L;
	
	private VelocityEngine velocityEngine;
	private String templateName;
	private Template template;
	private VelocityContext context;

	private JScrollPane infoScrollPane;
	private JTextPane infoPane;

	private Window parent;

	public TemplatePane(Properties props) {
		this(null, props);
	}

	public TemplatePane(Window parent, Properties props) {

		this.parent = parent;

		velocityEngine = new VelocityEngine();
		
		velocityEngine.setProperty(VelocityEngine.RESOURCE_LOADER, "class");
		velocityEngine.setProperty(
				"class." + VelocityEngine.RESOURCE_LOADER + ".class", 
				ClasspathResourceLoader.class.getName());
		
		velocityEngine.setProperty(VelocityEngine.COUNTER_NAME, "forIndex");
		velocityEngine.setProperty(VelocityEngine.COUNTER_INITIAL_VALUE, "0");
		
		//FIXME: external parameter
		// velocityEngine.setProperty(VelocityEngine.VM_LIBRARY, "/vm/details/common.vm");

		for (Entry<Object, Object> prop : props.entrySet())
			velocityEngine.setProperty(
					(String) prop.getKey(), prop.getValue());
		
		try {
			velocityEngine.init();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
			
		createComponents();
	}
	
	public TemplatePane() {
		this(new Properties());
	}

	private void createComponents() {
		infoPane = new JTextPane();
		infoPane.setEditable(false);
		infoPane.setBackground(Color.WHITE);
		infoPane.setContentType("text/html");
		//infoPane.setAutoscrolls(false);
		infoScrollPane = new JScrollPane(infoPane);
		/*infoScrollPane.setBorder(
				BorderFactory.createEmptyBorder(8, 8, 8, 8));*/

		infoPane.addHyperlinkListener(new HyperlinkListener() {
			@Override
			public void hyperlinkUpdate(HyperlinkEvent e) {
				if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
					final String desc = e.getDescription();
					if (desc.startsWith("action:"))
						performUrlAction(desc.substring("action:".length()));
					else {
						try {
							Desktop.getDesktop().browse(
									new URL(e.getDescription()).toURI());
						} catch (Exception e1) {
							ExceptionDialog dlg =
									new ExceptionDialog(parent, e1);
							dlg.setVisible(true);
						}
					}
				}
			}

			private void performUrlAction(String substring) {
				throw new UnsupportedOperationException("Not yet implemented");
			}
		});

		setLayout(new BorderLayout());
		add(infoScrollPane, BorderLayout.CENTER);
	}

	public Template getTemplate() {
		return template;
	}
	
	public void setTemplate(String name) 
			throws ResourceNotFoundException, ParseErrorException, Exception {
		
		if (template == null || !this.templateName.equals(name)) {
			template = velocityEngine.getTemplate(name);
			this.templateName = name;
		}
	}
	
	public VelocityContext getContext() {
		return context;
	}
	
	public void setContext(VelocityContext context) {
		this.context = context;
	}
	
	public void render() throws ResourceNotFoundException, ParseErrorException, MethodInvocationException, IOException {
		final StringWriter sw = new StringWriter();
		template.merge(context, sw);
		infoPane.setText(sw.toString());
		SwingUtilities.invokeLater(new Runnable() {
			@Override public void run() {
				infoScrollPane.getViewport().setViewPosition(new Point());
			}
		});
	}
}