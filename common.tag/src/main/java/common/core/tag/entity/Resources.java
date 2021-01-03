package common.core.tag.entity;

import java.io.Serializable;
import java.util.Date;

public class Resources implements Serializable{
    private static final long serialVersionUID = -2077764883574453661L;
    /** 主键 */
    private String id;

    /** 资源名称 */
    private String resourceName;
    
    /** 资源名称 */
    private String resourceCode;

    /** 资源路径 */
    private String resourceUrl;

    /** 资源类型 */
    private Integer resourceType;

    /** 排序字段 */
    private Integer sort;

    /** 资源描述 */
    private String description;

    /** 父id */
    private String parentId;
    
    /** 创建人*/
    private String creator;
    
    /** 修改人*/
    private String updator;
    
    /** 创建时间*/
    private Date createTime;
    
    /** 修改时间*/
    private Date updateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getResourceCode() {
		return resourceCode;
	}

	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}

	public String getResourceUrl() {
		return resourceUrl;
	}

	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}

	public Integer getResourceType() {
		return resourceType;
	}

	public void setResourceType(Integer resourceType) {
		this.resourceType = resourceType;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getUpdator() {
		return updator;
	}

	public void setUpdator(String updator) {
		this.updator = updator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

    


}
