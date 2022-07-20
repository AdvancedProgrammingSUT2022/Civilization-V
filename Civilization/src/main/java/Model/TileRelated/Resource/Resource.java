package Model.TileRelated.Resource;

import com.google.gson.annotations.Expose;

public class Resource {
    @Expose
    private ResourceType resourceType;
    private boolean isAvailable = false;

    public Resource(ResourceType resourceType) {
        this.setResourceType(resourceType);
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
