package Model.TileRelated.Resource;

public class Resource {
    private ResourceType resourceType;

    public Resource(ResourceType resourceType) {
        this.setResourceType(resourceType);
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }
   
}
