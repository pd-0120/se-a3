package cqu.assignmenttwo;

import cqu.assignmenttwo.Role;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-10-01T13:49:44", comments="EclipseLink-4.0.3.v20240522-rb5bf922d44efed420f3a09bc7fa4b015c369ea2a")
@StaticMetamodel(Staff.class)
@SuppressWarnings({"rawtypes", "deprecation"})
public class Staff_ { 

    public static volatile SingularAttribute<Staff, String> emailAddress;
    public static volatile SingularAttribute<Staff, String> password;
    public static volatile SingularAttribute<Staff, Role> role;
    public static volatile SingularAttribute<Staff, String> name;
    public static volatile SingularAttribute<Staff, Long> id;
    public static volatile SingularAttribute<Staff, String> idStaff;

}