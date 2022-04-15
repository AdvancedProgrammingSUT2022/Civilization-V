package Model.TileAndFeatures.Resource;

import java.util.ArrayList;

import Model.Technology.TechnologyType;
import Model.TileAndFeatures.Feature.FeatureType;
import Model.TileAndFeatures.Improvement.Improvement;
import Model.TileAndFeatures.Resource.ResourceType;

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
