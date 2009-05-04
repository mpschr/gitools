package es.imim.bg.ztools.model;

import java.io.Serializable;

public class Artifact implements Serializable, IArtifact{

    /** id unique **/
    private String artifactId;
    
    /** type of the artifact **/
    private String artifactType;
    
    /** short description **/
    private String title;

    /** long description **/
    private String description;
    
    public Artifact(){

    }
    
    public Artifact(String id, String artifactType) {
	this(id, artifactType, null, null);
    }

    public Artifact(String id, String artifactType, String title, String description) {
	this.artifactId = id;
	this.artifactType = artifactType;
	this.setTitle(title);
	this.setDescription(description);
    }

    public String getArtifactId() {
	return artifactId;
    }

    public void setArtifactId(String artifactId) {
	this.artifactId = artifactId;
    }

    public String getArtifactType() {
	return artifactType;
    }

    public void setArtifactType(String artifactType) {
	this.artifactType = artifactType;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public String getDescription() {
	return description;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public String getTitle() {
	return title;
    }

}
