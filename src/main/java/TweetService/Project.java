package TweetService;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // This tells Hibernate to make a table out of this class
public class Project {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long Id;
    private Long ProjectId;
    private Integer TypeId;

    public Long getId() {
		return Id;
	}

	public void setId(Long Id) {
		this.Id =Id;
	}

	public Integer getTypeId() {
		return TypeId;
	}

	public void setTypeId(Integer TypeId) {
		this.TypeId = TypeId;
	}

	public Long getProjectId() {
		return ProjectId;
	}

	public void setProjectId(Long ProjectId) {
		this.ProjectId = ProjectId;
	}

	


}