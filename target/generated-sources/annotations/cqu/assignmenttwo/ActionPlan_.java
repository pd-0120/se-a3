package cqu.assignmenttwo;

import cqu.assignmenttwo.ResponderAuthority;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-10-01T16:36:14", comments="EclipseLink-4.0.3.v20240522-rb5bf922d44efed420f3a09bc7fa4b015c369ea2a")
@StaticMetamodel(ActionPlan.class)
@SuppressWarnings({"rawtypes", "deprecation"})
public class ActionPlan_ { 

    public static volatile SingularAttribute<ActionPlan, String> planReview;
    public static volatile SingularAttribute<ActionPlan, ResponderAuthority> authorityRequired;
    public static volatile SingularAttribute<ActionPlan, String> levelOfPriority;
    public static volatile SingularAttribute<ActionPlan, String> planChanges;
    public static volatile SingularAttribute<ActionPlan, String> disasterId;
    public static volatile SingularAttribute<ActionPlan, String> actionsRequired;
    public static volatile SingularAttribute<ActionPlan, Long> id;

}